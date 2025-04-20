package com.example.restaurantreviewapp.vms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantreviewapp.dao.User
import com.example.restaurantreviewapp.dto.LoginState
import com.example.restaurantreviewapp.repos.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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

    fun registerUser(repeatPassword: String): Boolean {
        var output = false
        if (!validateSignUp(repeatPassword)) return output

        viewModelScope.launch {
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
                        output = true
                        clearLoginInfo()
                    } else {
                        addLoginError("User not created. Something went wrong.")
                    }
                }
            } catch (e: Exception) {
                println(e.toString())
                addLoginError("Something went wrong")
            } finally {
                _state.update {
                    it.copy(
                        loading = false
                    )
                }
            }
        }
        return output
    }

    fun login(): Boolean {
        var output = false
        if(!validateSignIn()) return output

        viewModelScope.launch {
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
                output = true
            }
            catch (e: IllegalStateException) {
                println(e.toString())
                addLoginError("User was not found")
            }
            catch (e: Exception) {
                println(e.toString())
                addLoginError("Something went wrong")
            } finally {
                _state.update {
                    it.copy(
                        loading = false
                    )
                }
            }
        }
        return output
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
        viewModelScope.launch {
            try {
                _state.update {
                 it.copy(
                    signUpVisible = visible
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
        _state.value.apply {
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

    private fun validateSignIn(): Boolean {
        var output = true
        _state.value.apply {
            errors.clear()
            if (username == "") addLoginError("Please provide a username")
            if (password == "") addLoginError("Please provide a password")

            if (errors.size > 0) output = false
        }
        return output
    }

    private fun clearLoginInfo() {
        setPassword("")
        setUsername("")
        setLastName("")
        setFirstName("")
        clearLoginErrors()
    }

    private fun addLoginError(error: String?) {
        if (error == null) _state.value.errors.clear()
        else _state.value.errors.add(error)
        _state.update {
            it.copy(
                errorString = it.errors.joinToString("\n")
            )
        }
    }

    private fun clearLoginErrors() {
        _state.value.errors.clear()
        _state.update {
            it.copy(
                errorString = ""
            )
        }
    }
}