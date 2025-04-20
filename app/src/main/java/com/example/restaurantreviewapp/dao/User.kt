package com.example.restaurantreviewapp.dao

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Index
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity(tableName = "users", indices = [Index(value = [ "username" ], unique = true)])
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo("first_name")
    val firstName: String,
    @ColumnInfo("last_name")
    val lastName: String,
    @ColumnInfo("username")
    var username: String,
    @ColumnInfo("password")
    var password: String,
    @ColumnInfo("logged_in")
    var loggedIn: Boolean
)


@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(user: User)

    @Query("SELECT * FROM users WHERE username=:username")
    suspend fun getUserInfo(username: String): User

    @Query("SELECT password FROM users WHERE username=:username")
    suspend fun getPasswordHash(username: String): String

    @Query("UPDATE users SET logged_in = 1 WHERE username = :username")
    suspend fun logUserIn(username: String)

    @Query("SELECT * FROM users WHERE logged_in=1")
    suspend fun getLoggedInUser(): User

    @Query("UPDATE users SET logged_in = 0 WHERE username <> :username")
    suspend fun logOthersOut(username: String)

    @Query("UPDATE users SET logged_in = 0 WHERE username = :username")
    suspend fun logOut(username: String)
}