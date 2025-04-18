package com.example.restaurantreviewapp.services

import android.content.ComponentName
import com.example.restaurantreviewapp.dto.RatingDto
import com.example.restaurantreviewapp.dto.RestaurantDto
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

const val baseUrl = "http://10.0.2.2:8000/api/restaurants/"
val retroFit: Retrofit = Retrofit.Builder()
    .baseUrl(baseUrl)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

interface RestaurantsApi {
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

val restaurantService: RestaurantsApi = retroFit.create(RestaurantsApi::class.java)

