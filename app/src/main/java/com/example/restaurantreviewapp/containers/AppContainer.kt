package com.example.restaurantreviewapp.containers

import android.content.Context
import com.example.restaurantreviewapp.dao.AppDatabase
import com.example.restaurantreviewapp.repos.AppRepository

class AppContainer(private val context: Context) {
    val appRepository: AppRepository by lazy {
        AppRepository(
            AppDatabase.getAppDatabase(context).userDao(),
            AppDatabase.getAppDatabase(context).reviewDao()
        )
    }
}