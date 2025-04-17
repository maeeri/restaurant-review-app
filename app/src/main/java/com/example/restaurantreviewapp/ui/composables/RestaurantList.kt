package com.example.restaurantreviewapp.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest.Builder
import coil3.request.crossfade
import com.example.restaurantreviewapp.dto.RestaurantDto
import com.example.restaurantreviewapp.dto.RestaurantsViewModel
import com.example.restaurantreviewapp.ui.theme.Grey

@Composable
fun RestaurantList(modifier: Modifier = Modifier, model: RestaurantsViewModel) {
    if (model.state.collectAsState().value.loading) return
    val restaurants: List<RestaurantDto> = model.state.collectAsState().value.restaurantList
    LazyColumn(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.Start) {
        items(restaurants.size) {
                i -> ListItem(modifier = Modifier.padding(8.dp), child = { RestaurantItem(restaurant = restaurants[i]) })
        }
    }
}

@Composable
fun RestaurantItem(modifier: Modifier = Modifier, restaurant: RestaurantDto) {
    Row(modifier = Modifier
        .background(color = Grey)
        .height(155.dp)
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
            .padding(3.dp)) {
            Row()
            {
                Text(
                    restaurant.name,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium)
            }
            Row {
                StarRating(modifier, restaurant.rating, restaurant.review_count)
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
                Text(restaurant.open_status)
            }
        }
    }
}



