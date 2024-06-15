package com.example.querizz_app.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.querizz_app.data.SumPagingSource
import com.example.querizz_app.data.api.config.ApiConfig
import com.example.querizz_app.data.api.service.AuthApiService
import com.example.querizz_app.data.response.RegisterResponse
import com.example.querizz_app.data.model.UserModel
import com.example.querizz_app.data.pref.UserPreference
import com.example.querizz_app.data.response.DataItem
import com.example.querizz_app.data.response.LoginResponse
import kotlinx.coroutines.flow.firstOrNull

class AuthRepository(
    private val authApiService: AuthApiService,
    private val userPreference: UserPreference
) {
    suspend fun register(name: String, email: String, password: String): RegisterResponse {
        return authApiService.register(name, email, password)
    }

    suspend fun login(email: String, password: String): LoginResponse {
        return authApiService.login(email, password)
    }

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession() = userPreference.getSession()

    fun getSummary(): LiveData<PagingData<DataItem>> = liveData {
        val userSession = userPreference.getSession().firstOrNull()
        if (userSession != null && userSession.token.isNotEmpty()) {
            val token = userSession.token
            val apiService = ApiConfig.getApiService(token)
            val pager = Pager(
                config = PagingConfig(
                    pageSize = 5
                ),
                pagingSourceFactory = {
                    SumPagingSource(apiService)
                }
            ).liveData
            emitSource(pager)
        } else {
            emit(PagingData.empty())
        }
    }

    companion object {
        fun getInstance(apiServiceAuth: AuthApiService, userPreference: UserPreference) =
            AuthRepository(apiServiceAuth, userPreference)
    }
}