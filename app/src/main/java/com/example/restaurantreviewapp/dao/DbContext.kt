package com.example.restaurantreviewapp.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class, Review::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun reviewDao(): ReviewDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getAppDatabase(context: Context): AppDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context = context,
                    klass = AppDatabase::class.java,
                    name = "db"
                )
                    .build()
                    .also { Instance = it }
            }
        }
    }
}

//https://developer.android.com/training/data-storage/room