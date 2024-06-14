package com.example.querizz_app.data.model

data class UserModel (
    val email: String,
    val token: String,
    val isLogin: Boolean = false
)