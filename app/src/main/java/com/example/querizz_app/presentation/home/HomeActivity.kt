package com.example.querizz_app.presentation.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.querizz_app.adapter.LoadingStateAdapter
import com.example.querizz_app.adapter.SumAdapter
import com.example.querizz_app.data.api.config.ApiConfig
import com.example.querizz_app.data.api.config.AuthApiConfig
import com.example.querizz_app.data.pref.UserPreference
import com.example.querizz_app.data.repository.AuthRepository
import com.example.querizz_app.data.repository.Repository
import com.example.querizz_app.databinding.ActivityHomeBinding
import com.example.querizz_app.presentation.add.AddSumActivity
import com.example.querizz_app.presentation.add.AddSumViewModel
import com.example.querizz_app.presentation.view.AuthViewModelFactory
import com.example.querizz_app.presentation.view.ViewModelFactory
import com.example.querizz_app.presentation.welcome.WelcomeActivity
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHomeBinding

    private lateinit var sumAdapter: SumAdapter

    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory(Repository(ApiConfig.getApiService(getTokenUser()), UserPreference.getInstance(this)))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sumAdapter = SumAdapter()
        binding.rvSummary.adapter = sumAdapter
        checkAuthority()
        binding.logout.setOnClickListener {
            viewModel.logout()
            val intent = Intent(this@HomeActivity, WelcomeActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.fabUpload.setOnClickListener {
            navigateToUpload()
        }
    }

    private fun checkAuthority() {
        viewModel.getSession().observe(this) { user ->
            if (user.token.isNullOrEmpty()) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            } else {
                setSummaryData()
                binding.tvHome.text = "Hello ${user.email}"
            }
        }
    }

    private fun navigateToUpload() {
        val intent = Intent(this@HomeActivity, AddSumActivity::class.java)
        startActivity(intent)
    }

    private fun setSummaryData() {
        sumAdapter = SumAdapter()
        binding.rvSummary.adapter = sumAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                sumAdapter.retry()
            }
        )
        viewModel.summaries.observe(this) {
            lifecycleScope.launch {
                sumAdapter.submitData(it)
            }
        }
        binding.rvSummary.layoutManager = LinearLayoutManager(this)

        // Pantau status load dari adapter
        sumAdapter.addLoadStateListener { loadState ->
            // Jika sedang memuat data, tampilkan ProgressBar
            val isLoading = loadState.source.refresh is LoadState.Loading
            showLoading(isLoading)
        }
    }

    private fun getTokenUser(): String {
        val sharedPreferences = getSharedPreferences(USER_PREF, Context.MODE_PRIVATE)
        return sharedPreferences.getString(USER_TOKEN, "") ?: ""
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        private const val EXTRA_TITLE = "extra_title"
        private const val EXTRA_DESCRIPTION = "extra_description"
        private const val USER_PREF = "user_preferences"
        private const val USER_TOKEN = "user_token"
    }
}