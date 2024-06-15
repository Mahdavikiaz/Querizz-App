package com.example.querizz_app.presentation.add

import android.app.ProgressDialog.show
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.querizz_app.R
import com.example.querizz_app.data.api.config.ApiConfig
import com.example.querizz_app.data.pref.UserPreference
import com.example.querizz_app.data.response.UploadResponse
import com.example.querizz_app.databinding.ActivityAddSumBinding
import com.example.querizz_app.presentation.home.HomeActivity
import com.example.querizz_app.presentation.result.ResultActivity
import com.example.querizz_app.presentation.view.ViewModelFactory
import com.google.gson.Gson
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException

class AddSumActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddSumBinding
    private var currentFileUri: Uri? = null

    private val viewModel by viewModels<AddSumViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddSumBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.fileUpload.setOnClickListener {

        }

        binding.summarize.setOnClickListener {
            uploadFile()
//            startActivity(Intent(this, HomeActivity::class.java))
//            finish()
        }
    }

    private fun uploadFile() {
        currentFileUri?.let {
            val title = binding.etTitle.text.toString()
            val subtitle = binding.etSubtitle.text.toString()

            val titleRequestBody = title.toRequestBody("text/plain".toMediaType())
            val subtitleRequestBody = subtitle.toRequestBody("text/plain".toMediaType())

            lifecycleScope.launch {
                try {
                    showLoading(true)  // Show loading before starting upload
                    val userPreference = UserPreference.getInstance(this@AddSumActivity)
                    val token = userPreference.getSession().first().token

                    val response = viewModel.uploadFile(token, titleRequestBody, subtitleRequestBody)
                    Log.e("Success", "Success upload file")
                    showToast(response.message!!)
                } catch (e: HttpException) {
                    val errorBody = e.response()?.errorBody()?.string()
                    val errorResponse = Gson().fromJson(errorBody, UploadResponse::class.java)
                    showToast(errorResponse.message!!)
                    Log.e("Error", "Error upload file: ${e.message()}")
                } finally {
                    showLoading(false)  // Hide loading after upload
                }
            }
        }
    }


//    private fun uploadFile() {
//        currentFileUri?.let {
//            val title = binding.etTitle.text.toString()
//            val subtitle = binding.etSubtitle.text.toString()
//
//            val titleRequestBody = title.toRequestBody("text/plain".toMediaType())
//            val subtitleRequestBody = subtitle.toRequestBody("text/plain".toMediaType())
//
//            lifecycleScope.launch {
//                try {
//                    val userPreference = UserPreference.getInstance(this@AddSumActivity)
//                    val token = userPreference.getSession().first().token
//
//                    viewModel.uploadFile(token, titleRequestBody, subtitleRequestBody)
//                    Log.e("Success", "Success upload file")
//                } catch (e: HttpException) {
//                    val errorBody = e.response()?.errorBody()?.string()
//                    val errorResponse = Gson().fromJson(errorBody, UploadResponse::class.java)
//                    showToast(errorResponse.message!!)
//                    showLoading(false)
//                }
//            }
//        }
//    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}