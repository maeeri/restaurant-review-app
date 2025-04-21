package com.example.restaurantreviewapp.mock

import com.example.restaurantreviewapp.dao.User
import com.example.restaurantreviewapp.dao.UserDao
import org.mindrot.jbcrypt.BCrypt

class MockUserDao: UserDao {
    override suspend fun insert(user: User) {
        return
    }

    override suspend fun getUserInfo(username: String): User {
        if (username == "testuser") {
            return User(
                id = 1,
                firstName = "Test",
                lastName = "Testington",
                username = "testuser",
                password = BCrypt.hashpw("test", BCrypt.gensalt()),
                loggedIn = true
            )
        } else {
            throw Exception()
        }
    }

    override suspend fun getPasswordHash(username: String): String {
        return BCrypt.hashpw("test", BCrypt.gensalt())
    }

    override suspend fun logUserIn(username: String) {
        return
    }

    override suspend fun getLoggedInUser(): String {
        return "testuser"
    }

    override suspend fun logOthersOut(username: String) {
        return
    }

    override suspend fun logOut(username: String) {
        return
    }

}