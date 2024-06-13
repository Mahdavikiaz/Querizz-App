package com.example.querizz_app.presentation.repository

import com.example.querizz_app.presentation.api.AuthApiService
import com.example.querizz_app.presentation.api.RegisterResponse
import com.example.querizz_app.presentation.pref.UserModel
import com.example.querizz_app.presentation.pref.UserPreference

class AuthRepository(
    private val authApiService: AuthApiService,
    private val userPreference: UserPreference
) {
    suspend fun register(name: String, email: String, password: String): RegisterResponse {
        return authApiService.register(name, email, password)
    }

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    companion object {
        fun getInstance(apiServiceAuth: AuthApiService, userPreference: UserPreference) =
            AuthRepository(apiServiceAuth, userPreference)
    }
}