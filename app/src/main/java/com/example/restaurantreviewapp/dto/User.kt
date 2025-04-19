package com.example.restaurantreviewapp.dto

data class UserState (
    val loading: Boolean = false,
    val username: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val error: String? = null
)
