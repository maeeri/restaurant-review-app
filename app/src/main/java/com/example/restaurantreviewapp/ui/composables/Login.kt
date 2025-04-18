package com.example.restaurantreviewapp.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.restaurantreviewapp.ui.theme.CardBackground
import com.example.restaurantreviewapp.ui.theme.RestaurantReviewAppTheme

@Composable
fun LoginPage(modifier: Modifier = Modifier, navController: NavController) {
    var userName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    RestaurantReviewAppTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
            Column(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
                Row {
                    AppBar(
                        text = "Login",
                        navController = navController
                    )
                }

                Card(modifier
                    .background(CardBackground)
                    .padding(50.dp)
                    .align(Alignment.CenterHorizontally)) {
                    Row {
                        TextField(
                            modifier = modifier.background(Color.White),
                            value = userName,
                            onValueChange = { userName = it },
                            label = { Text("username") }
                        )
                    }
                    Row {
                        TextField(
                            modifier = modifier.background(Color.White),
                            value = password,
                            onValueChange = { password = it },
                            label = { Text("password") }
                        )
                    }
                    Spacer(modifier.padding(8.dp))
                    Button(onClick = {/* add the magic*/}, modifier
                        .align(Alignment.CenterHorizontally)) {
                        Text("Login")
                    }
                    Spacer(modifier.padding(8.dp))
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun ReviewPreview() {
    RestaurantReviewAppTheme {
        LoginPage(navController = rememberNavController())
    }
}