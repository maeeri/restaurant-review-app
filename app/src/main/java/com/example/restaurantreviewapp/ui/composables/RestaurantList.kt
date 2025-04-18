package com.example.restaurantreviewapp.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest.Builder
import coil3.request.crossfade
import com.example.restaurantreviewapp.dto.RestaurantDto
import com.example.restaurantreviewapp.dto.AppViewModel
import com.example.restaurantreviewapp.ui.theme.CardBackground
import com.example.restaurantreviewapp.ui.theme.DarkGreen
import com.example.restaurantreviewapp.ui.theme.Orange
import com.example.restaurantreviewapp.ui.theme.RestaurantReviewAppTheme

@Composable
fun RestaurantList(modifier: Modifier = Modifier, model: AppViewModel, navController: NavController) {
    if (model.state.collectAsState().value.restaurantListState.loading) return
    val restaurants: List<RestaurantDto> = model.state.collectAsState().value.restaurantListState.restaurantList
    LazyColumn(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.Start) {
        items(restaurants.size) {
            i -> ListItem(modifier = Modifier
                .padding(8.dp)
                .clickable {
                    onRestaurantClick(restaurants[i], model, navController)
                },
                child = {
                    RestaurantItem(restaurant = restaurants[i])
                    }
            )
        }
    }
}

@Composable
fun RestaurantItem(modifier: Modifier = Modifier, restaurant: RestaurantDto?) {
    if (restaurant == null) return

    val statusColor: Color = when(restaurant.open_status.lowercase()) {
        "open" -> DarkGreen
        "closed" -> Color.Red
        "closing soon" -> Orange
        else -> Color.Black
    }

    Row(modifier = Modifier
        .background(color = CardBackground)
        .height(200.dp)
        .padding(3.dp),
        verticalAlignment = Alignment.CenterVertically) {
        Column(modifier = Modifier
            .weight(0.3f)
            .padding(2.dp)) {
            AsyncImage(
                model = Builder(LocalContext.current)
                    .data("https://cdn.pixabay.com/photo/2014/04/02/10/48/food-304597_1280.png")
                    .crossfade(true)
                    .build(),
                contentDescription = "image"
            )
        }
        Column(modifier = Modifier
            .weight(0.7f)
            .padding(5.dp)) {
            Row()
            {
                Text(
                    restaurant.name,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleLarge)
            }
            Row {
                Column {
                    StarRating(modifier, restaurant.rating)
                }
                Spacer(modifier.padding(3.dp))
                Column {
                    Row {
                        Text("(".plus(restaurant.review_count).plus(")"),
                            modifier.align(Alignment.CenterVertically))
                    }
                }
            }
            Row {
                Text(restaurant.cuisine.plus(" ").plus(restaurant.price_range))
            }
            Row {
                Text(restaurant.address.substringBefore(","))
            }
            Row {
                Text(restaurant.address.substringAfter(", "))
            }
            Row {
                Text(restaurant.open_status, fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = statusColor
                )
            }
        }
    }
}


@Composable
fun RestaurantListPage(modifier: Modifier = Modifier, model: AppViewModel, navController: NavController) {
    RestaurantReviewAppTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                Row {
                    AppBar(
                        text = "Restaurants",
                        navController = navController
                    )
                }
                Row {
                    RestaurantList(modifier = Modifier.padding(3.dp), model, navController)
                }
            }
        }
    }
}

fun onRestaurantClick(restaurant: RestaurantDto, model: AppViewModel, navController: NavController) {
    model.loadRestaurant(restaurant.id)
    navController.navigate("RestaurantPage")
}
