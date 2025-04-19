package com.example.restaurantreviewapp.dto

data class LoginState(
    val loading: Boolean = false,
    val errors: MutableList<String> = mutableListOf(),
    val errorString: String = "",
    val signUpVisible: Boolean = false,
    val username: String = "",
    val password: String ="",
    val firstName: String = "",
    val lastName: String = ""
)