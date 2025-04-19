package com.example.restaurantreviewapp.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.restaurantreviewapp.dto.AppViewModel
import com.example.restaurantreviewapp.ui.theme.RestaurantReviewAppTheme
import com.example.restaurantreviewapp.ui.theme.Turquoise

@Composable
fun LoginPage(modifier: Modifier = Modifier, navController: NavController, model: AppViewModel) {
    val state = model.state.collectAsState().value.loginState
    RestaurantReviewAppTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Row {
                    AppBar(
                        text = "Login",
                        navController = navController
                    )
                }

                CustomCard(modifier) {
                    SignUpForm(modifier, state.signUpVisible,
                        { model.setSignUpVisibility(!state.signUpVisible) }, { println("...") })
                }
            }
        }
    }
}

@Composable
fun SignUpForm(modifier: Modifier = Modifier, visible: Boolean, setVisibility: () -> Unit, onClickAction: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }

    val actionText: String = if (visible) "SIGN UP" else "SIGN IN"
    val showButtonText: String = if(visible) "BACK TO SIGN IN" else "REGISTER"

    Column(modifier.padding(20.dp)) {
        Row(modifier.align(Alignment.CenterHorizontally)) {
            Text(actionText, modifier.padding(10.dp), fontWeight = FontWeight.Bold, fontSize = 20.sp)
        }
        Row {
            TextField(
                modifier = modifier,
                value = username,
                onValueChange = { username = it },
                label = { Text("username") }
            )
        }
        Row {
            TextField(
                modifier = modifier,
                value = password,
                onValueChange = { password = it },
                visualTransformation = PasswordVisualTransformation(),
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
                    label = { Text("repeat password") }
                )
            }
            Row {
                TextField(
                    modifier = modifier,
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = { Text("first name") }
                )
            }
            Row {
                TextField(
                    modifier = modifier,
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = { Text("last name") }
                )
            }
        }
        Spacer(modifier.padding(8.dp))
        Button(onClick = {onClickAction()}, modifier
            .align(Alignment.CenterHorizontally),
            colors = ButtonColors(Turquoise, Color.White, Color.Gray, Color.Black)
        ) {
            Text(actionText)
        }
        Button(onClick = {setVisibility()}, modifier
            .align(Alignment.CenterHorizontally)
            ,colors = ButtonColors(Color.White, Turquoise, Color.Gray, Color.Black)) {
            Text(showButtonText)
        }
        Spacer(modifier.padding(8.dp))
    }
}


//@Preview(showBackground = true)
//@Composable
//fun ReviewPreview() {
//    RestaurantReviewAppTheme {
//        LoginPage(navController = rememberNavController())
//    }
//}