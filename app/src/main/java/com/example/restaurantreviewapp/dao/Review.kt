package com.example.restaurantreviewapp.dao

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity(tableName = "reviews",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("user_id"),
            onDelete = ForeignKey.NO_ACTION,
            onUpdate = ForeignKey.NO_ACTION
        )
    ])
data class Review(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "review_id")
    val reviewId: Int,
    @ColumnInfo(name = "user_id")
    val userId: Int
)

@Dao
interface ReviewDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(review: Review)

    @Query("SELECT * FROM reviews WHERE user_id=:userId")
    fun getUserReviews(userId: Int): List<Review>
}