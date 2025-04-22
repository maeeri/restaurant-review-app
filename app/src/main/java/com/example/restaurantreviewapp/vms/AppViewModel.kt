package com.example.restaurantreviewapp.vms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantreviewapp.dao.Review
import com.example.restaurantreviewapp.dto.AppState
import com.example.restaurantreviewapp.dto.RatingDto
import com.example.restaurantreviewapp.dto.RestaurantDto
import com.example.restaurantreviewapp.dto.RestaurantState
import com.example.restaurantreviewapp.dto.UserDto
import com.example.restaurantreviewapp.dto.UserState
import com.example.restaurantreviewapp.repos.AppRepository
import com.example.restaurantreviewapp.services.RestaurantsDataService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AppViewModel @Inject constructor(private val restaurantService: RestaurantsDataService,
                                       private val appRepository: AppRepository) : ViewModel() {
    private val _state = MutableStateFlow(
        AppState(
            RestaurantState(),
            UserState()
        )
    )
    val state = _state.asStateFlow()
    init {
        initializeFunctionCall()
        getRestaurants()
        getLoggedInUser()
        cleanUpFunctionCall()
    }
    private fun getLoggedInUser() {
        viewModelScope.launch(Dispatchers.Default) {
            try {
                val username = appRepository.getLoggedInUser()
                val user = appRepository.getUser(username)
                setUser(UserDto(user.username, user.firstName, user.lastName, user.id))
                val reviewIds = appRepository.getUserReviews(user.id)
                setUserReviews(reviewIds)
            } catch (e: Exception) {
                setError(e.toString())
            }
        }
    }
    private fun getRestaurants() {
        viewModelScope.launch(Dispatchers.Default) {
            try {
                val restaurants = restaurantService.getRestaurants()
                setRestaurantList(restaurants)
            }
            catch (e: Exception) {
                setError(e.toString())
            }
        }
    }
    private fun getRestaurantReviews(id: Int) {
        viewModelScope.launch(Dispatchers.Default) {
            try {
                initializeFunctionCall()
                val restaurant = restaurantService.getRestaurant(id)
                val ratings = restaurantService.getRestaurantRatings(id)
                setRestaurant(restaurant, ratings)
            }
            catch (e: Exception) {
                setError(e.toString())
            }
            finally {
                cleanUpFunctionCall()
            }
        }
    }
    private fun initializeFunctionCall() {
        _state.update {
            it.copy(
                loading = true,
                error = null
            )
        }
    }
    private fun cleanUpFunctionCall() {
        _state.update {
            it.copy(
                loading = false
            )
        }
    }
    private fun setUserReviews(reviews: List<Int>) {
        _state.update {
            it.copy(
                userState = it.userState.copy(
                    reviews = reviews
                )
            )
        }
    }
    private fun setError(error: String?) {
        _state.update {
            it.copy(
                error = error
            )
        }
    }
    private fun setUser(user: UserDto?) {
        _state.update {
            it.copy(
                userState = it.userState.copy(
                    user = user
                )
            )
        }
    }
    private fun setRestaurantList(restaurants: List<RestaurantDto>) {
        _state.update {
            it.copy(
                restaurantList = restaurants
            )
        }
    }
    private fun setRestaurant(restaurant: RestaurantDto, ratings: List<RatingDto>) {
        _state.update {
            it.copy(
                restaurantState = it.restaurantState.copy(
                    restaurant = restaurant,
                    ratings = ratings
                )
            )
        }
    }
    fun loadRestaurant(id: Int) {
        getRestaurantReviews(id)
    }
    fun addReview(restaurantId: Int, userId: Int, rating: Float, comment: String) {
        viewModelScope.launch(Dispatchers.Default) {
            try {
                initializeFunctionCall()
                val review = restaurantService.postRestaurantRating(restaurantId, rating, comment)
                appRepository.insertReview(Review(reviewId = review.id, userId = userId))
                val reviewIds = appRepository.getUserReviews(userId)
                setUserReviews(reviewIds)
                loadRestaurant(restaurantId)
            } catch (e: Exception) {
                setError(e.toString())
            } finally {
                cleanUpFunctionCall()
            }

        }
    }
    fun logout() {
        viewModelScope.launch(Dispatchers.Default) {
            try {
                initializeFunctionCall()
                _state.value.userState.user?.apply {
                    if (username != null) {
                        appRepository.logOut(username)
                    }
                }
                setUser(null)
            } catch (e: Exception) {
                setError(e.toString())
            } finally {
                cleanUpFunctionCall()
            }
        }
    }
    fun deleteReview(restaurantId: Int, reviewId: Int, userId: Int) {
        viewModelScope.launch(Dispatchers.Default) {
            try {
                initializeFunctionCall()
                restaurantService.deleteRating(restaurantId, reviewId)
                val review = appRepository.getReview(reviewId)
                appRepository.deleteReview(review)
                val restaurant = restaurantService.getRestaurant(restaurantId)
                val ratings = restaurantService.getRestaurantRatings(restaurantId)
                setRestaurant(restaurant, ratings)
                val reviewIds = appRepository.getUserReviews(userId)
                setUserReviews(reviewIds)
            } catch (e: Exception) {
                setError(e.toString())
            }
            finally {
                cleanUpFunctionCall()
            }

        }
    }
}