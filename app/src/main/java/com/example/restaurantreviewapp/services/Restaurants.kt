package com.example.restaurantreviewapp.services

import com.example.restaurantreviewapp.dto.RatingDto
import com.example.restaurantreviewapp.dto.RestaurantDto
import com.google.gson.JsonObject
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
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

    @POST("{id}/ratings")
    suspend fun postRestaurantRating(
        @Path("id") id: Int,
        @Body body: JsonObject
    ) : RatingDto
}

interface RestaurantsDataService {
    suspend fun getRestaurants(): List<RestaurantDto>
    suspend fun getRestaurant(id: Int): RestaurantDto
    suspend fun getRestaurantRatings(id: Int): List<RatingDto>
    suspend fun postRestaurantRating(id: Int, rating: Float, comment: String): RatingDto
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

    override suspend fun postRestaurantRating(id: Int, rating: Float, comment: String): RatingDto {
        val requestBody = JsonObject()
        requestBody.addProperty("rating", rating)
        requestBody.addProperty("comment", comment)
        val res = api.postRestaurantRating(id, requestBody)
        return res
    }
}