package com.example.restaurantreviewapp.dto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantreviewapp.dao.AppDatabase
import com.example.restaurantreviewapp.dao.User
import com.example.restaurantreviewapp.services.RestaurantsDataService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.mindrot.jbcrypt.BCrypt
import javax.inject.Inject

data class AppState (
    val restaurantListState: RestaurantListState,
    val restaurantState: RestaurantState,
    val userState: UserState,
    val loginState: LoginState
)

@HiltViewModel
class AppViewModel @Inject constructor(private val restaurantService: RestaurantsDataService,
                                       private val appDatabase: AppDatabase) : ViewModel() {
    private val _state = MutableStateFlow(
        AppState(
            RestaurantListState(),
            RestaurantState(),
            UserState(),
            LoginState()
        )
    )
    val state = _state.asStateFlow()

    init {
        getRestaurants()
    }

    fun loadRestaurant(id: Int) {
        getRestaurantReviews(id)
    }

    fun registerUser(user: User) {
        user.password = BCrypt.hashpw(user.password, BCrypt.gensalt())

    }

    fun setSignUpVisibility(visible: Boolean) {
        viewModelScope.launch {
            try {
                _state.update {
                    it.copy(
                        loginState = it.loginState.copy(
                            signUpVisible = visible
                        )
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        loginState = it.loginState.copy(
                            error = "Something went wrong"
                        )
                    )
                }
            }
        }
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