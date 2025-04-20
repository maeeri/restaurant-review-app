package com.example.restaurantreviewapp.dto

data class RestaurantDto (
    val id: Int,
    val name: String,
    val cuisine: String,
    val price_range: String,
    val address: String,
    val open_status: String,
    val rating: Float,
    val review_count: Int
)

data class RatingDto (
    val id: Int,
    val user_id: Int?,
    val value: Float,
    val description: String?,
    val date_rated: String?
)

data class AppState (
    val restaurantListState: RestaurantListState,
    val restaurantState: RestaurantState,
    val userState: UserState
)

data class RestaurantListState (
    val loading: Boolean = false,
    val restaurantList: List<RestaurantDto> = listOf(),
    val error: String? = null
)

data class RestaurantState (
    val loading: Boolean = false,
    val restaurant: RestaurantDto? = null,
    val ratings: List<RatingDto> = listOf(),
    val error: String? = null
)
