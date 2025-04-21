package com.example.restaurantreviewapp

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
import com.example.restaurantreviewapp.ui.composables.AppBar
import com.example.restaurantreviewapp.vms.LoginViewModel
import com.example.restaurantreviewapp.vms.AppViewModel
import com.example.restaurantreviewapp.ui.composables.LoginPage
import com.example.restaurantreviewapp.ui.composables.LogoutPage
import com.example.restaurantreviewapp.ui.composables.RestaurantListPage
import com.example.restaurantreviewapp.ui.composables.RestaurantPage
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
                        Image(
                            painter = painterResource(R.drawable.logo),
                            modifier = Modifier
                                .padding(20.dp)
                                .height(325.dp)
                                .clickable {
                                    navController.navigate("restaurantFeature")
                                    scope.launch {
                                        drawerState.close()
                                    }
                                },
                            contentScale = ContentScale.FillHeight,
                            contentDescription = "logo"
                        )
                        HorizontalDivider(modifier = Modifier.padding(vertical = 20.dp))
                        NavigationDrawerItem(
                            label = {
                                Text("Home")
                            },
                            icon = {
                                Icon(Icons.Default.Home, contentDescription = "Restaurant list")
                            },
                            onClick = {
                                navController.navigate("restaurantFeature")
                                scope.launch {
                                    drawerState.close()
                                }
                            },
                            selected = true
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        NavigationDrawerItem(
                            label = {
                                Text("Sign in / switch user")
                            },
                            icon = {
                                Icon(Icons.Default.Lock, contentDescription = "Sign in")
                            },
                            onClick = {
                                navController.navigate("authFeature")
                                scope.launch {
                                    drawerState.close()
                                }
                            },
                            selected = true
                        )
                        HorizontalDivider(modifier = Modifier.padding(vertical = 20.dp))
                        NavigationDrawerItem(
                            label = {
                                Text("Sign out", modifier = Modifier.align(Alignment.End))
                            },
                            icon = {
                                Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Sign out")
                            },
                            onClick = {
                                navController.navigate("LogoutPage")
                                scope.launch {
                                    drawerState.close()
                                }
                            },
                            selected = true,
                            colors = NavigationDrawerItemDefaults.colors(
                                selectedContainerColor = Color.White,
                                unselectedIconColor = Color.Gray
                            )
                        )
                    }
                },
                drawerState = drawerState
            ) {
                AppNavHost(navController = navController,
                    startDestination = "restaurantFeature",
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
        navigation(startDestination = "RestaurantListPage", route = "restaurantFeature") {
            composable("RestaurantListPage") {
                val model = it.SharedViewModel<AppViewModel>(navController)
                RestaurantListPage(
                    model = model,
                    navController = navController,
                    topBar = {
                        AppBar(modifier,
                            "Home",
                            navigationButton = {
                                MenuIcon { onMenuClick() }
                            }
                        )
                    }
                )
            }
            composable("RestaurantPage") {
                val model = it.SharedViewModel<AppViewModel>(navController)
                val restaurantName = if (model.state.collectAsState().value.restaurantState.restaurant != null) {
                    model.state.collectAsState().value.restaurantState.restaurant!!.name
                }
                    else {
                    ""
                }
                RestaurantPage(
                    model = model,
                    topBar = {
                        AppBar(modifier,
                            restaurantName,
                            navigationButton = {
                                BackArrowIcon { navigateToHome(navController) }
                            }
                        )
                    }
                )
            }
        }
        navigation("LoginPage", route = "authFeature") {
            composable("LoginPage") {
                val model = it.SharedViewModel<LoginViewModel>(navController)
                LoginPage(
                    model = model,
                    topBar = {
                        AppBar(modifier,
                            "Sign in",
                            navigationButton = {
                                MenuIcon { onMenuClick() }
                            }
                        )
                    },
                    navController = navController
                )
            }
            composable("LogoutPage") {
                val model = it.SharedViewModel<AppViewModel>(navController)
                LogoutPage(
                    model = model,
                    topBar = {
                        AppBar(modifier,
                            "Signed out",
                            navigationButton = {
                                BackArrowIcon { navigateToHome(navController) }
                            }
                        )
                    }
                )
            }
        }
    }
}

@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.SharedViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}

@HiltAndroidApp
class ReviewApplication : Application() {
}

fun navigateToHome(navController: NavController) {
    navController.navigate("restaurantFeature")
}

@Composable
fun MenuIcon(onMenuClick: () -> Unit) {
    IconButton(onClick = { onMenuClick() }) {
        Icon(
            imageVector = Icons.Filled.Menu,
            contentDescription = "menu",
            Modifier.defaultMinSize(30.dp, 30.dp)
        )
    }
}

@Composable
fun BackArrowIcon(onBackArrowClick: () -> Unit) {
    IconButton(onClick = { onBackArrowClick() }) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "back",
            Modifier.defaultMinSize(30.dp, 30.dp)
        )
    }
}