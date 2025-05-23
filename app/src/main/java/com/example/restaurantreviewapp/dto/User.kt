package com.example.restaurantreviewapp.dto

data class UserDto(
    val username: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val id: Int? = null
)

data class UserState (
    val user: UserDto? = null,
    val reviews: List<Int> = listOf()
)
