package com.example.restaurantreviewapp.ui.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.restaurantreviewapp.vms.LoginViewModel
import com.example.restaurantreviewapp.ui.theme.RestaurantReviewAppTheme
import com.example.restaurantreviewapp.ui.theme.Turquoise

@Composable
fun LoginPage(modifier: Modifier = Modifier,
              model: LoginViewModel,
              topBar: @Composable (modifier: Modifier) -> Unit,
              navController: NavController
) {
    RestaurantReviewAppTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Row {
                    topBar(modifier)
                }

                CustomCard(modifier) {
                    SignUpForm(modifier, model, navController)
                }
            }
        }
    }
}

@Composable
fun SignUpForm(modifier: Modifier = Modifier, model: LoginViewModel, navController: NavController) {
    var repeatPassword by remember { mutableStateOf("") }
    val loginState = model.state.collectAsState().value

    if (loginState.success) {
        model.setSuccess(false)
        LocalSoftwareKeyboardController.current?.hide()
        navController.navigate("restaurantfeature") {
            popUpTo("authfeature")
        }
    }
    val visible = loginState.signUpVisible

    val actionText: String = if (visible) "SIGN UP" else "SIGN IN"
    val showButtonText: String = if(visible) "BACK TO SIGN IN" else "REGISTER"

    Column(modifier.padding(20.dp)) {
        Row(modifier.align(Alignment.CenterHorizontally)) {
            Text(actionText, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        }
        Row(modifier
            .align(Alignment.CenterHorizontally)
            .padding(3.dp)) {
            loginState.error?.let { ErrorTextBox(modifier, it) }
        }
        Row {
            TextField(
                modifier = modifier,
                value = loginState.username,
                onValueChange = { model.setUsername(it)},
                label = { Text("username") }
            )
        }
        Row {
            TextField(
                modifier = modifier,
                value = loginState.password,
                onValueChange = { model.setPassword(it) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    autoCorrectEnabled = false,
                    keyboardType = KeyboardType.Password
                ),
                label = { Text("password") }
            )
        }
        if (visible){
            Row {
                TextField(
                    modifier = modifier,
                    value = repeatPassword,
                    onValueChange = { repeatPassword = it },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(
                        autoCorrectEnabled = false,
                        keyboardType = KeyboardType.Password
                    ),
                    label = { Text("repeat password") }
                )
            }
            Row {
                TextField(
                    modifier = modifier,
                    value = loginState.firstName,
                    onValueChange = { model.setFirstName(it) },
                    label = { Text("first name") }
                )
            }
            Row {
                TextField(
                    modifier = modifier,
                    value = loginState.lastName,
                    onValueChange = { model.setLastName(it) },
                    label = { Text("last name") }
                )
            }
        }
        Spacer(modifier.padding(8.dp))
        Button(onClick = {
            if(visible) {
                model.registerUser(repeatPassword)
                repeatPassword = ""
            } else {
                model.login()
            }
        }, modifier
            .align(Alignment.CenterHorizontally),
            colors = ButtonColors(Turquoise, Color.White, Color.Gray, Color.Black)
        ) {
            Text(actionText)
        }
        Button(onClick = { model.setSignUpVisibility(!visible) }, modifier
            .align(Alignment.CenterHorizontally)
            ,colors = ButtonColors(Color.White, Turquoise, Color.Gray, Color.Black)) {
            Text(showButtonText)
        }
        Spacer(modifier.padding(8.dp))
    }
}

@Composable
fun ErrorTextBox(modifier: Modifier = Modifier, text: String) {
    if(text != "") {
        Box(modifier
            .border(1.dp, Color.Red)) {
            Text(text,
                modifier
                    .padding(5.dp),
                color = Color.Red
            )
        }
    }
}

