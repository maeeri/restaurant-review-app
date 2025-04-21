package com.example.restaurantreviewapp.mock

import com.example.restaurantreviewapp.dto.RatingDto
import com.example.restaurantreviewapp.dto.RestaurantDto
import com.example.restaurantreviewapp.services.RestaurantsDataService

class MockRestaurantsDataService: RestaurantsDataService {
    override suspend fun getRestaurants(): List<RestaurantDto> {
        return listOf(
            RestaurantDto(
                1,
                "TESTIPAIKKA",
                "Experimental",
                "$$",
                "Test Street, 12345 TESTTOWN",
                "Closed",
                2.5f,
                1
            )
        )
    }

    override suspend fun getRestaurant(id: Int): RestaurantDto {
        return RestaurantDto(
            1,
            "TESTIPAIKKA",
            "Experimental",
            "$$",
            "Test Street, 12345 TESTTOWN",
            "Closed",
            2.5f,
            1
        )
    }

    override suspend fun getRestaurantRatings(id: Int): List<RatingDto> {
        return listOf(
            RatingDto(
                id = 1,
                user_id = null,
                value = 2.5f,
                description = null,
                date_rated = null,
            )
        )
    }

    override suspend fun postRestaurantRating(id: Int, rating: Float, comment: String): RatingDto {
        return RatingDto(
            id = 2,
            user_id = null,
            value = 3.5f,
            description = null,
            date_rated = null,
        )
    }

    override suspend fun deleteRating(restaurantId: Int, ratingId: Int) {
        return
    }

}