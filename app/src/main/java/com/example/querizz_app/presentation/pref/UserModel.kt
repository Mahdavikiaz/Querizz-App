package com.example.querizz_app.presentation.pref

data class UserModel (
    val email: String,
    val token: String,
    val isLogin: Boolean = false
)