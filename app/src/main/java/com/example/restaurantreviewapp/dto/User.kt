package com.example.restaurantreviewapp.dto

data class UserDto(
    val username: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val id: Int? = null
)

data class UserState (
//    val loading: Boolean = false,
    val user: UserDto? = null,
//    val error: String? = null,
    val reviews: List<Int> = listOf()
)
