package com.example.querizz_app.presentation.add

import android.app.ProgressDialog.show
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import com.example.querizz_app.util.uriToFile
import com.google.gson.Gson
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
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
            startGallery()
        }

        binding.summarize.setOnClickListener {
            uploadFile()
//            startActivity(Intent(this, HomeActivity::class.java))
//            finish()
        }
    }

    private fun uploadFile() {
        currentFileUri?.let { uri ->
            val imageFile = uriToFile(uri, this)
            Log.d("Image File", "showImage: ${imageFile.path}")
            showLoading(true)

            val title = binding.etTitle.text.toString()
            val subtitle = binding.etSubtitle.text.toString()

            val titleRequestBody = title.toRequestBody("text/plain".toMediaType())
            val subtitleRequestBody = subtitle.toRequestBody("text/plain".toMediaType())
            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "photo",
                imageFile.name,
                requestImageFile
            )

            val intent = Intent(this, HomeActivity::class.java)

            lifecycleScope.launch {
                try {
                    val userPref = UserPreference.getInstance(this@AddSumActivity)
                    val token = userPref.getSession().first().token
                    val apiService = ApiConfig.getApiService(token)
                    val successResponse = apiService.uploadFile(token, multipartBody, titleRequestBody, subtitleRequestBody)
                    showToast(successResponse.message!!)
                    startActivity(intent)
                    finish()
                    showLoading(false)
                } catch (e: HttpException) {
                    val errorBody = e.response()?.errorBody()?.string()
                    val errorResponse = Gson().fromJson(errorBody, UploadResponse::class.java)
                    showToast(errorResponse.message!!)
                    showLoading(false)
                }
            }

        } ?: showToast("Image kosong")
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentFileUri = uri
        } else {
            Log.d("Photo Picker", "No media selected")
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
//                    showLoading(true)  // Show loading before starting upload
//                    val userPreference = UserPreference.getInstance(this@AddSumActivity)
//                    val token = userPreference.getSession().first().token
//
//                    val response = viewModel.uploadFile(token, titleRequestBody, subtitleRequestBody)
//                    Log.e("Success", "Success upload file")
//                    showToast(response.message!!)
//                } catch (e: HttpException) {
//                    val errorBody = e.response()?.errorBody()?.string()
//                    val errorResponse = Gson().fromJson(errorBody, UploadResponse::class.java)
//                    showToast(errorResponse.message!!)
//                    Log.e("Error", "Error upload file: ${e.message()}")
//                } finally {
//                    showLoading(false)  // Hide loading after upload
//                }
//            }
//        }
//    }


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