package com.example.restaurantreviewapp.mock

import com.example.restaurantreviewapp.dao.Review
import com.example.restaurantreviewapp.dao.ReviewDao

class MockReviewDao: ReviewDao {
    override suspend fun insert(review: Review) {
        return
    }

    override suspend fun getUserReviews(userId: Int): List<Int> {
        return listOf(1)
    }

    override suspend fun deleteByIdAndUserId(reviewId: Int, userId: Int) {
        return
    }
}