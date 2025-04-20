package com.example.restaurantreviewapp.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.restaurantreviewapp.dto.RatingDto
import com.example.restaurantreviewapp.vms.RestaurantsViewModel
import com.example.restaurantreviewapp.ui.theme.CardBackground
import com.example.restaurantreviewapp.ui.theme.RestaurantReviewAppTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun ReviewList(modifier: Modifier = Modifier, model: RestaurantsViewModel) {
    if (model.state.collectAsState().value.restaurantState.loading) return
    val reviews = model.state.collectAsState().value.restaurantState.ratings
    Row(modifier) {
        LazyColumn(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.Start) {
            items(reviews.size) {
                i -> CustomCard(modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
                        child = { ReviewItem(review = reviews[i]) })
            }
        }
    }
}

@Composable
fun ReviewItem(modifier: Modifier = Modifier, review: RatingDto) {
    var reviewTimeString = ""
    if (review.date_rated != null) {
        try {
            reviewTimeString = LocalDateTime
                .parse(review.date_rated.substring(0,19), DateTimeFormatter
                    .ofPattern("yyyy-MM-dd HH:mm:ss"))
                .format(DateTimeFormatter
                    .ofPattern("dd.MM.yyyy HH:mm:ss"))
                .toString()
        } catch (e: Exception)
        {
            reviewTimeString = ""
        }
    }
    Column(modifier.fillMaxWidth().background(CardBackground).padding(8.dp)) {
        Row {
            Column {
                StarRating(rating = review.value)
            }
        }
        Spacer(modifier.padding(8.dp))
        Row { review.description?.let { Text(it) } }
        Spacer(modifier.padding(8.dp))
        Row { Text(reviewTimeString, fontSize = 10.sp) }
    }
}

@Composable
fun RestaurantReviews(modifier: Modifier = Modifier, model: RestaurantsViewModel) {
    Column {
        Row {
            CustomCard(child = { model.state.collectAsState().value.restaurantState.restaurant?.let { RestaurantItem(restaurant = it) } })
        }
        Row {
            ReviewList(modifier, model)
        }
    }
}


@Composable
fun RestaurantPage(modifier: Modifier = Modifier,
                   model: RestaurantsViewModel,
                   topBar: @Composable() (modifier: Modifier) -> Unit) {
    RestaurantReviewAppTheme {
        Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
            Column(modifier = modifier.fillMaxSize().padding(innerPadding)) {
                Row {
                    topBar(modifier)
                }
                Row {
                    RestaurantReviews(model = model)
                }
            }
        }
    }
}
