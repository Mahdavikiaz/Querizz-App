package com.example.querizz_app.presentation.add

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.querizz_app.data.api.config.ApiConfig
import com.example.querizz_app.data.repository.Repository
import com.example.querizz_app.data.response.UploadResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import kotlin.math.log

class AddSumViewModel(private val repository: Repository): ViewModel() {
    private val _response = MutableLiveData<UploadResponse>()
    val response: LiveData<UploadResponse> = _response

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

//    fun uploadFile(token: String, file: MultipartBody.Part, title: RequestBody, subtitle: RequestBody) {
//        _loading.value = true
//        _error.value = null
//
//        viewModelScope.launch {
//            try {
//                val uploadResponse = repository.uploadFile(token, file, title, subtitle)
//                _response.value = uploadResponse
//                _loading.value = false
//            } catch (e: Exception) {
//                _error.value = e.message
//                _loading.value = false
//            }
//        }
//    }
//    private val _isLoading = MutableLiveData<Boolean>()
//    val isLoading: LiveData<Boolean> = _isLoading
//
//    private val _error = MutableLiveData<String>()
//    val error: LiveData<String> = _error
//
//    private val _response = MutableLiveData<UploadResponse>()
//    val response: LiveData<UploadResponse> = _response
//
//    suspend fun uploadFile(token: String, multipartBody: MultipartBody.Part, title: RequestBody, subtitle: RequestBody): UploadResponse {
//        return withContext(Dispatchers.IO) {
//            try {
//                val apiService = ApiConfig.getApiService(token)
//                val response = apiService.uploadFile(multipartBody, title, subtitle)
//                _response.postValue(response)
//                response
//            } catch (e: Exception) {
//                throw e
//            }
//        }
//    }

//    suspend fun uploadFile(token: String, title: RequestBody, subtitle: RequestBody): UploadResponse {
//        // Implement your API call here
//        return repository.uploadFile(token, title, subtitle)
//    }

//    suspend fun uploadFile(token: String, title: RequestBody, subtitle: RequestBody): UploadResponse {
//        return withContext(Dispatchers.IO) {
//            try {
//                val apiService = ApiConfig.getApiService(token)
//                val response = apiService.uploadFile(title, subtitle)
//                _response.postValue(response)
//                response
//            } catch (e: Exception) {
//                throw e
//            }
//        }
//    }
}