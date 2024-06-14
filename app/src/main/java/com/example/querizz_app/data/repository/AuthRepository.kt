package com.example.querizz_app.data.repository

import com.example.querizz_app.data.api.service.AuthApiService
import com.example.querizz_app.data.response.RegisterResponse
import com.example.querizz_app.data.model.UserModel
import com.example.querizz_app.data.pref.UserPreference
import com.example.querizz_app.data.response.LoginResponse

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

    companion object {
        fun getInstance(apiServiceAuth: AuthApiService, userPreference: UserPreference) =
            AuthRepository(apiServiceAuth, userPreference)
    }
}