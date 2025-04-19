package com.example.restaurantreviewapp.dto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantreviewapp.dao.User
import com.example.restaurantreviewapp.repos.AppRepository
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
                                       private val appRepository: AppRepository) : ViewModel() {
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

    fun registerUser(repeatPassword: String) {
        if (!validateSignUp(repeatPassword)) return

        viewModelScope.launch {
            _state.value.loginState.apply {
                try {
                    val passwordHash = BCrypt.hashpw(password, BCrypt.gensalt())
                    val user = User(
                        username = username,
                        firstName = firstName,
                        lastName = lastName,
                        password = passwordHash,
                        loggedIn = true
                    )
                    appRepository.insertUser(user)
                    var userFromDb = appRepository.getUser(username)
                    println(userFromDb)
                    clearLoginInfo()
                } catch (e: Exception) {
                    addLoginError("Something went wrong")
                } finally {

                }
            }
        }
    }

    fun login() {
        println(state.value.loginState.username)
        println(state.value.loginState.password)
        clearLoginInfo()
    }

    fun setUsername(username: String) {
        _state.update {
            it.copy(
                loginState = it.loginState.copy(
                    username = username
                )
            )
        }
    }

    fun setPassword(password: String) {
        _state.update {
            it.copy(
                loginState = it.loginState.copy(
                    password = password
                )
            )
        }
    }

    fun setFirstName(firstName: String) {
        _state.update {
            it.copy(
                loginState = it.loginState.copy(
                    firstName = firstName
                )
            )
        }
    }

    fun setLastName(lastName: String) {
        _state.update {
            it.copy(
                loginState = it.loginState.copy(
                    lastName = lastName
                )
            )
        }
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
                println(e.toString())
                addLoginError("Something went wrong")
            }
        }
        clearLoginInfo()
    }

    private fun validateSignUp(repeatPassword: String): Boolean {
        var output = true
        _state.value.loginState.apply {
            errors.clear()
            if (password != repeatPassword) addLoginError("Passwords did not match")
            if (username == "") addLoginError("Please provide a username")
            if (password == "") addLoginError("Please provide a password")
            if (firstName == "") addLoginError("Please provide a first name")
            if (lastName == "") addLoginError("Please provide a last name")

            if (errors.size > 0) output = false
        }
        return output
    }

    private fun clearLoginInfo() {
        setPassword("")
        setUsername("")
        setLastName("")
        setFirstName("")
        _state.value.loginState.errors.clear()
    }

    private fun addLoginError(error: String?) {
        if (error == null) _state.value.loginState.errors.clear()
        else _state.value.loginState.errors.add(error)
        _state.update {
            it.copy(
                loginState = it.loginState.copy(
                    errorString = it.loginState.errors.joinToString("\n")
                )
            )
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