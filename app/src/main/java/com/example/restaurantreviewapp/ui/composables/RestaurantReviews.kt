package com.example.restaurantreviewapp.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.restaurantreviewapp.dto.RatingDto
import com.example.restaurantreviewapp.vms.AppViewModel
import com.example.restaurantreviewapp.ui.theme.CardBackground
import com.example.restaurantreviewapp.ui.theme.RestaurantReviewAppTheme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun ReviewList(modifier: Modifier = Modifier, model: AppViewModel) {
    val state = model.state.collectAsState().value
    if (state.loading) return
    val reviews = state.restaurantState.ratings
    val ownReviews = state.userState.reviews
    val userId = state.userState.user?.id
    val restaurantId = state.restaurantState.restaurant?.id

    Row(modifier) {
        LazyColumn(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.Start) {
            items(reviews.size) {
                i -> CustomCard(modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
                child = {
                        ReviewItem(review = reviews[i],
                            isOwnReview = ownReviews.contains(reviews[i].id),
                            onDelete = {
                                if (restaurantId != null && userId != null) {
                                    model.deleteReview(
                                        restaurantId, reviewId = reviews[i].id, userId
                                    )
                                }
                            }
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun ReviewItem(modifier: Modifier = Modifier, review: RatingDto, isOwnReview: Boolean, onDelete: () -> Unit) {
    var reviewTimeString = ""
    if (review.date_rated != null) {
        reviewTimeString = try {
            LocalDateTime
                .parse(review.date_rated.substring(0,19), DateTimeFormatter
                    .ofPattern("yyyy-MM-dd HH:mm:ss"))
                .format(DateTimeFormatter
                    .ofPattern("dd.MM.yyyy HH:mm:ss"))
                .toString()
        } catch (e: Exception) {
            ""
        }
    }
    Column(modifier
        .fillMaxWidth()
        .background(CardBackground)
        .padding(8.dp)) {
        Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column {
                StarRating(rating = review.value)
            }
            Column {
                if (isOwnReview) {
                    CustomIcon(
                        modifier.clickable {
                            onDelete()
                        },
                        Icons.Default.Delete,
                        iconTintColor = Color.Red
                    )
                }
            }
        }
        Spacer(modifier.padding(8.dp))
        Row { review.description?.let { Text(it) } }
        Spacer(modifier.padding(8.dp))
        Row { Text(reviewTimeString, fontSize = 10.sp) }
    }
}

@Composable
fun RestaurantReviews(modifier: Modifier = Modifier, model: AppViewModel) {
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
                   model: AppViewModel,
                   topBar: @Composable (modifier: Modifier) -> Unit) {
    var showAddReview by remember { mutableStateOf(false) }
    val state = model.state.collectAsState().value
    if (state.loading) return

    val userIsNotNull = state.userState.user != null
    val restaurantId = state.restaurantState.restaurant?.id
    val userId = state.userState.user?.id

    RestaurantReviewAppTheme {
        Scaffold(modifier = modifier.fillMaxSize(),
            floatingActionButton = {
                if (userIsNotNull) {
                    ExtendedFloatingActionButton(
                        text = { Text("Add review") },
                        icon = { Icon(Icons.Filled.Add, contentDescription = "") },
                        onClick = {
                            showAddReview = true
                        }
                    )
                }
            }) { innerPadding ->
            Column(modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)) {
                Row {
                    topBar(modifier)
                }
                Row {
                    RestaurantReviews(model = model)
                }
            }
            if(restaurantId != null && userId != null) {
                AddReviewModal(
                    modifier,
                    { showAddReview = false },
                    showAddReview,
                    restaurantId,
                    userId,
                    model
                )
            }
        }
    }
}

