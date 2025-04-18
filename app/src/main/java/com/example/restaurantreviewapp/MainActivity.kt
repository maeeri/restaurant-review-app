package com.example.restaurantreviewapp

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.restaurantreviewapp.dto.AppViewModel
import com.example.restaurantreviewapp.ui.composables.RestaurantListPage
import com.example.restaurantreviewapp.ui.composables.RestaurantPage


class MainActivity : ComponentActivity(), ViewModelStoreOwner {
    private lateinit var model: AppViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = ViewModelProvider(this)[AppViewModel::class.java]
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "RestaurantListPage") {
                composable("RestaurantListPage") {
                    RestaurantListPage(
                        model = model,
                        navController = navController,
                    )
                }

                composable("RestaurantPage") {
                    RestaurantPage(model = model)
                }
            }
        }
    }
}

class ReviewApplication : Application()





//@Preview(showBackground = true)
//@Composable
//fun AppPreview() {
//    RestaurantReviewAppTheme {
//        App()
//    }
//}