package com.example.restaurantreviewapp.vms

import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantreviewapp.dao.User
import com.example.restaurantreviewapp.dto.LoginState
import com.example.restaurantreviewapp.repos.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.mindrot.jbcrypt.BCrypt
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val appRepository: AppRepository) : ViewModel() {
    private val _state  = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()
    private fun validateSignUp(repeatPassword: String): Boolean {
        resetError()
        var output = true
        _state.value.apply {
            if (password != repeatPassword) addLoginError("Passwords did not match")
            if (username == "") addLoginError("Please provide a username")
            if (password == "") addLoginError("Please provide a password")
            if (firstName == "") addLoginError("Please provide a first name")
            if (lastName == "") addLoginError("Please provide a last name")

            if (error != null) output = false
        }
        return output
    }
    private fun validateSignIn(): Boolean {
        var output = true
        resetError()
        _state.value.apply {
            if (username == "") addLoginError("Please provide a username")
            if (password == "") addLoginError("Please provide a password")
            if (error != null) output = false
        }
        return output
    }
    private fun clearLoginInfo() {
        setPassword("")
        setUsername("")
        setLastName("")
        setFirstName("")
        resetError()
    }
    private fun addLoginError(error: String?) {
        _state.update {
            it.copy(
                error = if (it.error == null) error else it.error.plus("\n").plus(error)
            )
        }
    }
    private fun resetError () {
        _state.update {
            it.copy(
                error = null
            )
        }
    }
    fun registerUser(repeatPassword: String) {
        _state.update {
            it.copy(
                success = false
            )
        }
        if (!validateSignUp(repeatPassword)) return

        viewModelScope.launch(Dispatchers.Default) {
            try {
                _state.update {
                    it.copy(
                        loading = true
                    )
                }
                _state.value.apply {
                    val passwordHash = BCrypt.hashpw(password, BCrypt.gensalt())
                    val user = User(
                        username = username,
                        firstName = firstName,
                        lastName = lastName,
                        password = passwordHash,
                        loggedIn = true
                    )
                    appRepository.insertUser(user)
                    val userFromDb = appRepository.getUser(username)
                    if (userFromDb.username == username) {
                        _state.update {
                            it.copy(
                                success = true
                            )
                        }
                        clearLoginInfo()
                    } else {
                        addLoginError("User not created. Something went wrong.")
                    }
                }
            }
            catch (e: SQLiteConstraintException) {
                addLoginError("Username is already in use")
            }
            catch (e: Exception) {
                addLoginError("Something went wrong")
            } finally {
                _state.update {
                    it.copy(
                        loading = false
                    )
                }
            }
        }
    }
    fun login() {
        _state.update {
            it.copy(
                success = false
            )
        }
        if(!validateSignIn()) return

        viewModelScope.launch(Dispatchers.Default) {
            try {
                _state.update {
                    it.copy(
                        loading = true
                    )
                }
                _state.value.apply {
                    if (!BCrypt.checkpw(password, appRepository.getPasswordHash(username))) {
                        addLoginError("Wrong password")
                        setPassword("")
                        return@launch
                    } else {
                        appRepository.logOthersOut(username)
                        appRepository.logUserIn(username)
                    }
                }
                clearLoginInfo()
                _state.update {
                    it.copy(
                        success = true
                    )
                }
            }
            catch (e: IllegalStateException) {
                addLoginError("User was not found")
            }
            catch (e: Exception) {
                addLoginError("Something went wrong")
            } finally {
                _state.update {
                    it.copy(
                        loading = false
                    )
                }
            }
        }
    }
    fun setUsername(username: String) {
        _state.update {
            it.copy(
                username = username
            )
        }
    }
    fun setPassword(password: String) {
        _state.update {
            it.copy(
                password = password
            )
        }
    }
    fun setFirstName(firstName: String) {
        _state.update {
            it.copy(
                firstName = firstName
            )
        }
    }
    fun setLastName(lastName: String) {
        _state.update {
            it.copy(
                lastName = lastName
            )
        }
    }
    fun setSignUpVisibility(visible: Boolean) {
        _state.update {
         it.copy(
            signUpVisible = visible
            )
        }
        clearLoginInfo()
    }
}