package com.example.querizz_app.presentation.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.querizz_app.adapter.LoadingStateAdapter
import com.example.querizz_app.adapter.SumAdapter
import com.example.querizz_app.data.api.config.AuthApiConfig
import com.example.querizz_app.data.pref.UserPreference
import com.example.querizz_app.data.repository.AuthRepository
import com.example.querizz_app.databinding.ActivityHomeBinding
import com.example.querizz_app.presentation.add.AddSumActivity
import com.example.querizz_app.presentation.view.AuthViewModelFactory
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHomeBinding

    private lateinit var sumAdapter: SumAdapter

    private val viewModel by viewModels<HomeViewModel> {
        AuthViewModelFactory(AuthRepository(AuthApiConfig.getApiService(getTokenUser()), UserPreference.getInstance(this)))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sumAdapter = SumAdapter()
        binding.rvSummary.adapter = sumAdapter
        setSummaryData()
        binding.fabUpload.setOnClickListener {
            navigateToUpload()
        }
    }

    private fun navigateToUpload() {
        val intent = Intent(this@HomeActivity, AddSumActivity::class.java)
        startActivity(intent)
        finish()
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
    }

    private fun getTokenUser(): String {
        val sharedPreferences = getSharedPreferences(USER_PREF, Context.MODE_PRIVATE)
        return sharedPreferences.getString(USER_TOKEN, "") ?: ""
    }

    companion object {
        private const val EXTRA_TITLE = "extra_title"
        private const val EXTRA_DESCRIPTION = "extra_description"
        private const val USER_PREF = "user_preferences"
        private const val USER_TOKEN = "user_token"
    }
}