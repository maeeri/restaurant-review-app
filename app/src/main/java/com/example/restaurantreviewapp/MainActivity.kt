package com.example.restaurantreviewapp

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.restaurantreviewapp.containers.AppContainer
import com.example.restaurantreviewapp.dto.AppViewModel
import com.example.restaurantreviewapp.ui.composables.LoginPage
import com.example.restaurantreviewapp.ui.composables.RestaurantListPage
import com.example.restaurantreviewapp.ui.composables.RestaurantPage
import dagger.BindsInstance
import dagger.Component
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val model: AppViewModel = ViewModelProvider(this)[AppViewModel::class.java]
        enableEdgeToEdge()
        setContent {
            // AppNavHost(model = model, startDestination = "RestaurantListPage")
            AppNavHost(model = model, startDestination = "Login")
        }
    }
}

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String,
    model: AppViewModel
) {
    NavHost(modifier = modifier, navController = navController, startDestination = startDestination) {
        composable("RestaurantListPage") {
            RestaurantListPage(
                model = model,
                navController = navController
            )
        }

        composable("RestaurantPage") {
            RestaurantPage(
                model = model,
                navController = navController
            )
        }

        composable("Login") {
            LoginPage(
                model = model,
                navController = navController
            )
        }
    }
}

@HiltAndroidApp
class ReviewApplication : Application() {
    private lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainer(this)
    }
}

@Component
internal interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application?): Builder? // Application extends Context
        fun build(): AppComponent?
    }

    fun inject(app: ReviewApplication?)
}



//@Preview(showBackground = true)
//@Composable
//fun AppPreview() {
//    RestaurantReviewAppTheme {
//        App()
//    }
//}