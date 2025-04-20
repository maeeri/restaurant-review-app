package com.example.restaurantreviewapp.modules

import android.content.Context
import com.example.restaurantreviewapp.dao.AppDatabase
import com.example.restaurantreviewapp.repos.AppRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object AppDbModule {
    @Provides
    fun provideAppDatabase(context: Context): AppDatabase {
        return AppDatabase.getAppDatabase(context)
    }

    @Provides
    fun provideAppRepository(appDatabase: AppDatabase): AppRepository {
        return AppRepository(appDatabase.userDao(), appDatabase.reviewDao())
    }
}
