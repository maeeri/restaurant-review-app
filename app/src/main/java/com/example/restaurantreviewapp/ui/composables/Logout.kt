package com.example.restaurantreviewapp.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.restaurantreviewapp.ui.theme.RestaurantReviewAppTheme
import com.example.restaurantreviewapp.vms.AppViewModel

@Composable
fun LogoutPage(modifier: Modifier = Modifier, model: AppViewModel, topBar: @Composable () -> Unit) {
    val userState = model.state.collectAsState().value.userState
    if (userState.loading) return

    val user = userState.user
    if (user != null) {
        println(user.username)
        println(user.firstName)
        println(user.lastName)
        model.logout()
    }
    RestaurantReviewAppTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Row {
                    topBar()
                }
                if (user == null) {
                    Text("You have successfully signed out",
                        modifier.padding(PaddingValues(top = 20.dp)),
                        fontSize = 24.sp)
                }
            }
        }
    }
}