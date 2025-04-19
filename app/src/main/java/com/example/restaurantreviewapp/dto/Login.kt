package com.example.restaurantreviewapp.dto

data class LoginState(
    val loading: Boolean = false,
    val error: String? = null,
    val signInVisible: Boolean = true,
    val signUpVisible: Boolean = false
)