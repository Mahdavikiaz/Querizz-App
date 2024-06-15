package com.example.querizz_app.presentation.add

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.querizz_app.data.api.config.ApiConfig
import com.example.querizz_app.data.repository.Repository
import com.example.querizz_app.data.response.UploadResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.RequestBody
import java.io.File
import kotlin.math.log

class AddSumViewModel(private val repository: Repository): ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _response = MutableLiveData<UploadResponse>()
    val response: LiveData<UploadResponse> = _response

    suspend fun uploadFile(token: String, title: RequestBody, subtitle: RequestBody): UploadResponse {
        return withContext(Dispatchers.IO) {
            try {
                val apiService = ApiConfig.getApiService(token)
                val response = apiService.uploadFile(title, subtitle)
                _response.postValue(response)
                response
            } catch (e: Exception) {
                throw e
            }
        }
    }
}