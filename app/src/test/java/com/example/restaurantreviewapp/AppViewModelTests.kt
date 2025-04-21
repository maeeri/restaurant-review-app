package com.example.restaurantreviewapp

import com.example.restaurantreviewapp.dto.RestaurantDto
import com.example.restaurantreviewapp.dto.UserDto
import com.example.restaurantreviewapp.mock.MockRestaurantsDataService
import com.example.restaurantreviewapp.mock.MockReviewDao
import com.example.restaurantreviewapp.mock.MockUserDao
import com.example.restaurantreviewapp.repos.AppRepository
import com.example.restaurantreviewapp.services.RestaurantsDataService
import com.example.restaurantreviewapp.vms.AppViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test


class AppViewModelTests {
    @Test
    fun `getRestaurants should update state`(): Unit = runTest {
        val expected = listOf(
            RestaurantDto(
                1,
                "TESTIPAIKKA",
                "Experimental",
                "$$",
                "Test Street, 12345 TESTTOWN",
                "Closed",
                2.5f,
                1
            )
        )
        assertEquals(expected, model.state.value.restaurantList)
    }
    @Test
    fun `getLoggedInUser should update state`(): Unit = runTest {
        val expected = UserDto(
            id = 1,
            firstName = "Test",
            lastName = "Testington",
            username = "testuser"
        )
        assertEquals(expected, model.state.value.userState.user)
    }
    companion object {
        private lateinit var model: AppViewModel
        private lateinit var service: RestaurantsDataService

        @JvmStatic
        @OptIn(ExperimentalCoroutinesApi::class)
        @BeforeClass
        fun setup() {
            Dispatchers.setMain(Dispatchers.Unconfined)
            service = MockRestaurantsDataService()
            model = AppViewModel(service, AppRepository(MockUserDao(), MockReviewDao()))
        }

        @JvmStatic
        @OptIn(ExperimentalCoroutinesApi::class)
        @AfterClass
        fun tearDown() {
            Dispatchers.resetMain()
        }
    }
}