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

    fun getSession() {
        viewModelScope.launch {
            repository.getSession()
        }
    }

    fun uploadStory(file: File, title: String, subtitle: String) = repository.uploadStory(file, title, subtitle)

}