package com.example.restaurantreviewapp.repos

import com.example.restaurantreviewapp.dao.Review
import com.example.restaurantreviewapp.dao.ReviewDao
import com.example.restaurantreviewapp.dao.User
import com.example.restaurantreviewapp.dao.UserDao

class AppRepository(private val userDao: UserDao, private val reviewDao: ReviewDao) {
    suspend fun getUser(username: String) = userDao.getUserInfo(username)
    suspend fun getPasswordHash(username: String) = userDao.getPasswordHash(username)
    suspend fun getLoggedInUser() = userDao.getLoggedInUser()
    suspend fun getUserReviews(userId: Int): List<Int> = reviewDao.getUserReviews(userId)
    suspend fun getReview(reviewId: Int): Review = reviewDao.getReview(reviewId)

    suspend fun insertUser(user: User) = userDao.insert(user)
    suspend fun insertReview(review: Review) = reviewDao.insert(review)

    suspend fun logUserIn(username: String) = userDao.logUserIn(username)
    suspend fun logOthersOut(username: String) = userDao.logOthersOut(username)
    suspend fun logOut(username: String) = userDao.logOut(username)

    suspend fun deleteReview(review: Review) = reviewDao.deleteReview(review)
}