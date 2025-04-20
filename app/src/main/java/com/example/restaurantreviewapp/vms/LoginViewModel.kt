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

    fun registerUser(repeatPassword: String) {
        if (!validateSignUp(repeatPassword)) return

        viewModelScope.launch {
            _state.value.apply {
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
                    val userFromDb = appRepository.getUser(username)
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
        println(state.value.username)
        println(state.value.password)
        clearLoginInfo()
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