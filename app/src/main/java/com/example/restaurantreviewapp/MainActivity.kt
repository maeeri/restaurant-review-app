package com.example.restaurantreviewapp

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.restaurantreviewapp.dto.AppViewModel
import com.example.restaurantreviewapp.ui.composables.RestaurantList
import com.example.restaurantreviewapp.ui.theme.RestaurantReviewAppTheme
import com.example.restaurantreviewapp.ui.theme.Turquoise


class MainActivity : ComponentActivity(), ViewModelStoreOwner {
    private lateinit var model: AppViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = ViewModelProvider(this)[AppViewModel::class.java]
        enableEdgeToEdge()
        setContent {
            RestaurantReviewAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    App(modifier = Modifier.padding(innerPadding), model)
                }
            }
        }
    }
}

class ReviewApplication : Application()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(modifier: Modifier = Modifier) {
    TopAppBar({ Text("Restaurants")}, modifier, navigationIcon = {
        IconButton(onClick = { /* do something */ }) {
            Icon(
                imageVector = Icons.Filled.Menu,
                contentDescription = "menu"
            )
        }
    },
        colors = TopAppBarColors(Turquoise, Color.Yellow, Color.White, Color.White, Color.White))
}

@Composable
fun App(modifier: Modifier = Modifier, model: AppViewModel) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row {
            AppBar()
        }
        Row {
            RestaurantList(modifier = Modifier.padding(3.dp), model)
        }
    }
}



//@Preview(showBackground = true)
//@Composable
//fun AppPreview() {
//    RestaurantReviewAppTheme {
//        App()
//    }
//}