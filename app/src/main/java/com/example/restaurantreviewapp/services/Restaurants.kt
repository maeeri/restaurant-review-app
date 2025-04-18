package com.example.restaurantreviewapp.services

import android.content.ComponentName
import com.example.restaurantreviewapp.dto.RatingDto
import com.example.restaurantreviewapp.dto.RestaurantDto
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface RestaurantsDataApi {
    @GET("ratings")
    suspend fun getRestaurants() : List<RestaurantDto>

    @GET("{id}")
    suspend fun getRestaurant(
        @Path("id") id: Int
    ) : RestaurantDto

    @GET("{id}/ratings")
    suspend fun getRestaurantRatings(
        @Path("id") id: Int
    ) : List<RatingDto>
}

interface RestaurantsDataService {
    suspend fun getRestaurants(): List<RestaurantDto>
    suspend fun getRestaurant(id: Int): RestaurantDto
    suspend fun getRestaurantRatings(id: Int): List<RatingDto>
}

class RestaurantsDataServiceImplementation(private val api: RestaurantsDataApi) : RestaurantsDataService {
    override suspend fun getRestaurants(): List<RestaurantDto> {
        val restaurants = api.getRestaurants()
        return restaurants
    }

    override suspend fun getRestaurant(id: Int): RestaurantDto {
        val restaurant = api.getRestaurant(id)
        return restaurant
    }

    override suspend fun getRestaurantRatings(id: Int): List<RatingDto> {
        val ratings = api.getRestaurantRatings(id)
        return ratings
    }
}