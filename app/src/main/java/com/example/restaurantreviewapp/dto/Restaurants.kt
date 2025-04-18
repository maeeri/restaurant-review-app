package com.example.restaurantreviewapp.dto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantreviewapp.services.restaurantService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.sql.Timestamp

data class RestaurantDto (
    val id: Int,
    val name: String,
    val cuisine: String,
    val price_range: String,
    val address: String,
    val open_status: String,
    val rating: Float,
    val review_count: Int
)

data class RestaurantListState (
    val loading: Boolean = false,
    val restaurantList: List<RestaurantDto> = listOf(),
    val error: String? = null
)

data class RestaurantState (
    val loading: Boolean = false,
    val restaurant: RestaurantDto? = null,
    val ratings: List<RatingDto> = listOf(),
    val error: String? = null
)

data class AppState (
    val restaurantListState: RestaurantListState,
    val restaurantState: RestaurantState
)

data class RatingDto (
    val id: Int,
    val user_id: Int?,
    val value: Float,
    val description: String?,
    val date_rated: String?
)

class AppViewModel : ViewModel() {
    private val _state = MutableStateFlow(AppState(RestaurantListState(), RestaurantState()))
    val state = _state.asStateFlow()

    init {
        getRestaurants()
        loadRestaurant(1)
    }

    fun loadRestaurant(id: Int) {
        getRestaurantReviews(id)
    }

    private fun getRestaurants() {
        viewModelScope.launch {
            try {
                _state.update {
                    it.copy(
                        restaurantListState = it.restaurantListState.copy(
                            error = null, loading = true
                        )
                    )
                }
                val restaurants = restaurantService.getRestaurants()
                _state.update {
                    it.copy(
                        restaurantListState = it.restaurantListState.copy(
                            restaurantList = restaurants
                        )
                    )
                }
            }
            catch (e: Exception) {
                _state.update {
                    it.copy(
                        restaurantListState = it.restaurantListState.copy(
                            error = e.toString()
                        )
                    )
                }
            }
            finally {
                _state.update {
                    it.copy(
                        restaurantListState = it.restaurantListState.copy(
                            loading = false
                        )
                    )
                }
            }
        }
    }

    private fun getRestaurantReviews(id: Int) {
        viewModelScope.launch {
            try {
                _state.update {
                    it.copy(
                        restaurantState = it.restaurantState.copy(
                            error = null, loading = true
                        )
                    )
                }
                val restaurant = restaurantService.getRestaurant(id)
                val ratings = restaurantService.getRestaurantRatings(id)
                _state.update {
                    it.copy(
                        restaurantState = it.restaurantState.copy(
                            restaurant = restaurant,
                            ratings = ratings
                        )
                    )
                }
            }
            catch (e: Exception) {
                _state.update {
                    it.copy(
                        restaurantState = it.restaurantState.copy(
                            error = e.toString()
                        )
                    )
                }
            }
            finally {
                _state.update {
                    it.copy(
                        restaurantState = it.restaurantState.copy(
                            loading = false
                        )
                    )
                }
            }
        }
    }
}
