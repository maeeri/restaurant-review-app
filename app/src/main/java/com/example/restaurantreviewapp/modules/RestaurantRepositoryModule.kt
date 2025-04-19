package com.example.restaurantreviewapp.modules

import com.example.restaurantreviewapp.services.RestaurantsDataApi
import com.example.restaurantreviewapp.services.RestaurantsDataService
import com.example.restaurantreviewapp.services.RestaurantsDataServiceImplementation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
object RestaurantRepositoryModule {
    @Provides
    fun provideRestaurantsDataService(api: RestaurantsDataApi): RestaurantsDataService {
        return RestaurantsDataServiceImplementation(api)
    }
}