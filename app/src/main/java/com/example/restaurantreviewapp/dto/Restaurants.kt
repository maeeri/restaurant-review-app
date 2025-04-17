package com.example.restaurantreviewapp.dto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantreviewapp.services.restaurantService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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

class RestaurantsViewModel : ViewModel() {
    private val _state = MutableStateFlow(RestaurantListState())
    val state = _state.asStateFlow()

    init {
        getRestaurants()
    }

    private fun getRestaurants() {
        viewModelScope.launch {
            try {
                _state.update {
                    it.copy(error = null, loading = true)
                }
                val restaurants = restaurantService.getRestaurants()
                _state.update {
                    it.copy(restaurantList = restaurants)
                }
            }
            catch (e: Exception) {
                _state.update {
                    it.copy(error = e.toString())
                }
            }
            finally {
                _state.update {
                    it.copy(loading = false)
                }
            }
        }
    }
}
