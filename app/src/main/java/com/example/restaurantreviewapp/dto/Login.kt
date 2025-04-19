package com.example.restaurantreviewapp.dto

data class LoginState(
    val loading: Boolean = false,
    val error: String? = null,
    val signUpVisible: Boolean = false
)