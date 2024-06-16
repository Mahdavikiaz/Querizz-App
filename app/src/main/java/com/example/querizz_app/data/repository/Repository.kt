package com.example.querizz_app.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.querizz_app.data.api.config.ApiConfig
import com.example.querizz_app.data.api.service.ApiService
import com.example.querizz_app.data.api.service.AuthApiService
import com.example.querizz_app.data.model.UserModel
import com.example.querizz_app.data.pref.UserPreference
import com.example.querizz_app.data.response.ApiResponse
import com.example.querizz_app.data.response.DataItem
import com.example.querizz_app.data.response.LoginResponse
import com.example.querizz_app.data.response.SumResponse
import com.example.querizz_app.data.response.UploadResponse
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException

class   Repository(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    suspend fun logout() = userPreference.logout()

//    suspend fun uploadFile(token: String, file: MultipartBody.Part, title: RequestBody, subtitle: RequestBody): UploadResponse {
//        return apiService.uploadFile("Bearer $token", file, title, subtitle)
//    }

    fun getSummary(): LiveData<ApiResponse<SumResponse>> = liveData {
        val userSession = userPreference.getSession().firstOrNull()
        if (userSession != null && !userSession.token.isNullOrEmpty()) {
            val token = userSession.token
            val apiService = ApiConfig.getApiService(token)
            emit(ApiResponse.Loading)
            try {
                val response = apiService.getSummaries()
                emit(ApiResponse.Success(response))
            } catch (e: HttpException) {
                val jsonInString = e.response()?.errorBody()?.string()
                val errorBody = Gson().fromJson(jsonInString, LoginResponse::class.java)
                val errorMessage = errorBody.message
                emit(ApiResponse.Error(errorMessage))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
            }
        } else {
            emit(ApiResponse.Error("Error"))
        }
    }

    companion object {
        fun getInstance(apiService: ApiService, userPreference: UserPreference) =
            Repository(apiService, userPreference)
    }
}