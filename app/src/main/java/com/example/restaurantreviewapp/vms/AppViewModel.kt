package com.example.restaurantreviewapp.vms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantreviewapp.dto.AppState
import com.example.restaurantreviewapp.dto.RestaurantListState
import com.example.restaurantreviewapp.dto.RestaurantState
import com.example.restaurantreviewapp.dto.UserDto
import com.example.restaurantreviewapp.dto.UserState
import com.example.restaurantreviewapp.repos.AppRepository
import com.example.restaurantreviewapp.services.RestaurantsDataService
import dagger.hilt.android.lifecycle.HiltViewModel
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
            RestaurantListState(),
            RestaurantState(),
            UserState()
        )
    )
    val state = _state.asStateFlow()

    init {
        getRestaurants()
        getLoggedInUser()
    }

    fun loadRestaurant(id: Int) {
        getRestaurantReviews(id)
    }

    fun logout() {
        viewModelScope.launch {
            try {
                _state.update {
                    it.copy(
                        userState = it.userState.copy(
                            loading = true
                        )
                    )
                }
                _state.value.userState.user?.apply {
                    if (username != null) {
                        appRepository.logOut(username)
                    }
                }
                _state.update {
                    it.copy(
                        userState = it.userState.copy(
                            user = null
                        )
                    )
                }
            } catch (e: Exception) {
                println(e.toString())
            } finally {
                _state.update {
                    it.copy(
                        userState = it.userState.copy(
                            loading = false
                        )
                    )
                }
            }
        }
    }

    fun loadUser(username: String) {
        viewModelScope.launch {
            try {
                _state.update {
                    it.copy(
                        userState = it.userState.copy(
                            loading = true
                        )
                    )
                }
                val user = appRepository.getUser(username)
                _state.update {
                    it.copy(
                        userState = it.userState.copy(
                            user = UserDto(
                                user.username, user.firstName, user.lastName
                            )
                        )
                    )
                }
            } catch (e: Exception) {
                println(e.toString())
            } finally {
                _state.update {
                    it.copy(
                        userState = it.userState.copy(
                            loading = false
                        )
                    )
                }
            }
        }
    }

    private fun getLoggedInUser() {
        viewModelScope.launch {
            try {
                _state.update {
                    it.copy(
                        userState = it.userState.copy(
                            loading = true
                        )
                    )
                }
                val username = appRepository.getLoggedInUser()
                loadUser(username)
            } catch (e: Exception) {
                println(e.toString())
            } finally {
                _state.update {
                    it.copy(
                        userState = it.userState.copy(
                            loading = false
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