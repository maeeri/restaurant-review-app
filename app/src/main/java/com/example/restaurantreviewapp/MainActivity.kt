package com.example.restaurantreviewapp

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.restaurantreviewapp.containers.AppContainer
import com.example.restaurantreviewapp.ui.composables.AppBar
import com.example.restaurantreviewapp.vms.LoginViewModel
import com.example.restaurantreviewapp.vms.RestaurantsViewModel
import com.example.restaurantreviewapp.ui.composables.LoginPage
import com.example.restaurantreviewapp.ui.composables.RestaurantListPage
import com.example.restaurantreviewapp.ui.composables.RestaurantPage
import dagger.BindsInstance
import dagger.Component
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val drawerState = rememberDrawerState(DrawerValue.Closed)
            val scope = rememberCoroutineScope()
            val navController = rememberNavController()
            ModalNavigationDrawer(
                drawerContent = {
                    ModalDrawerSheet {
                        Spacer(modifier = Modifier.height(16.dp))
                        NavigationDrawerItem(
                            label = {
                                Text("Restaurant list")
                            },
                            icon = {
                                Icon(Icons.Default.Home, contentDescription = "Restaurant list")
                            },
                            onClick = {
                                navController.navigate("restaurantfeature")
                                scope.launch {
                                    drawerState.close()
                                }
                            },
                            selected = true
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        NavigationDrawerItem(
                            label = {
                                Text("Sign in")
                            },
                            icon = {
                                Icon(Icons.Default.Lock, contentDescription = "Sign in")
                            },
                            onClick = {
                                navController.navigate("authfeature")
                                scope.launch {
                                    drawerState.close()
                                }
                            },
                            selected = true
                        )
                    }
                },
                drawerState = drawerState
            ) {
                AppNavHost(navController = navController,
                    startDestination = "restaurantfeature",
                    onMenuClick = {
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String,
    onMenuClick: () -> Unit
) {
    NavHost(modifier = modifier,
        navController = navController,
        startDestination = startDestination) {
        navigation(startDestination = "RestaurantListPage", route = "restaurantfeature") {
            composable("RestaurantListPage") {
                val model = it.SharedViewModel<RestaurantsViewModel>(navController)
                RestaurantListPage(
                    model = model,
                    navController = navController,
                    topBar = { AppBar(modifier,
                        "Restaurant list",
                        navController = navController,
                        onMenuClick) }
                )
            }

            composable("RestaurantPage") {
                val model = it.SharedViewModel<RestaurantsViewModel>(navController)
                RestaurantPage(
                    model = model,
                    topBar = { AppBar(modifier,
                        "Restaurant reviews",
                        navController = navController,
                        onMenuClick) }
                )
            }
        }
        navigation("LoginPage", route = "authfeature") {
            composable("LoginPage") {
                val model = it.SharedViewModel<LoginViewModel>(navController)
                LoginPage(
                    model = model,
                    topBar = { AppBar(modifier,
                        "Sign in",
                        navController = navController,
                        onMenuClick) }
                )
            }
        }
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.SharedViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }

    return hiltViewModel(parentEntry)

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
        fun application(application: Application?): Builder?
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