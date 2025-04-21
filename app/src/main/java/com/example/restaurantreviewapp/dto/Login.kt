package com.example.restaurantreviewapp.dto

data class LoginState(
    val loading: Boolean = false,
    val error: String? = null,
    val signUpVisible: Boolean = false,
    val username: String = "",
    val password: String ="",
    val firstName: String = "",
    val lastName: String = "",
    val success: Boolean = false
)