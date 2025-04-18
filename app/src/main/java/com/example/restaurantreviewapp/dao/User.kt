package com.example.restaurantreviewapp.dao

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo("first_name")
    val firstName: String,
    @ColumnInfo("last_name")
    val lastName: String,
    @ColumnInfo("username")
    val username: String,
    @ColumnInfo("password")
    var password: String,
    @ColumnInfo("logged_in")
    val loggedIn: Boolean
)


@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(user: User)

    @Query("SELECT * FROM users WHERE username=:username")
    fun getUserInfo(username: String): User

    @Query("SELECT * FROM users WHERE logged_in=1")
    fun getLoggedInUser(): User
}