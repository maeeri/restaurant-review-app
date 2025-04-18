package com.example.restaurantreviewapp.modules

import android.app.Application
import android.content.Context
import com.example.restaurantreviewapp.ReviewApplication
import com.example.restaurantreviewapp.dao.AppDatabase
import com.example.restaurantreviewapp.services.RestaurantsDataApi
import com.example.restaurantreviewapp.services.RestaurantsDataService
import com.example.restaurantreviewapp.services.RestaurantsDataServiceImplementation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun restaurantsDataApi(): RestaurantsDataApi {
        val baseUrl = "http://10.0.2.2:8000/api/restaurants/"
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RestaurantsDataApi::class.java)
    }

    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext context: Context): Context {
        return context
    }
}

@Module
@InstallIn(ViewModelComponent::class)
object RestaurantRepositoryModule {
    @Provides
    fun provideRestaurantsDataService(api: RestaurantsDataApi): RestaurantsDataService {
        return RestaurantsDataServiceImplementation(api)
    }
}

@Module
@InstallIn(SingletonComponent::class)
object AppDbModule {
    @Provides
    fun provideAppDatabase(context: Context): AppDatabase {
        return AppDatabase.getAppDatabase(context)
    }
}


