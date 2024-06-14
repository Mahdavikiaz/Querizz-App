package com.example.querizz_app.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.querizz_app.data.api.config.ApiConfig
import com.example.querizz_app.data.api.service.ApiService
import com.example.querizz_app.data.api.service.AuthApiService
import com.example.querizz_app.data.pref.UserPreference
import com.example.querizz_app.data.response.UploadResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody

class Repository(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {

    fun getSession() = userPreference.getSession()

    suspend fun logout() = userPreference.logout()

    companion object {
        fun getInstance(apiService: ApiService, userPreference: UserPreference) =
            Repository(apiService, userPreference)
    }
}