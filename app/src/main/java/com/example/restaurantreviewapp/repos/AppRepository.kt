package com.example.restaurantreviewapp.repos

import com.example.restaurantreviewapp.dao.Review
import com.example.restaurantreviewapp.dao.ReviewDao
import com.example.restaurantreviewapp.dao.User
import com.example.restaurantreviewapp.dao.UserDao

class AppRepository(private val userDao: UserDao, private val reviewDao: ReviewDao) {
    fun getUser(username: String) = userDao.getUserInfo(username)
    fun getLoggedInUser(username: String) = userDao.getLoggedInUser()
    fun getUserReviews(userId: Int) = reviewDao.getUserReviews(userId)

    suspend fun insertUser(user: User) = userDao.insert(user)
    suspend fun insertReview(review: Review) = reviewDao.insert(review)
}