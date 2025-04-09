package com.example.restaurantreviewapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest.Builder
import coil3.request.crossfade
import com.example.restaurantreviewapp.ui.theme.RestaurantReviewAppTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RestaurantReviewAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    RestaurantList(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun ListItem(modifier: Modifier = Modifier) {

    Card(modifier = Modifier
        .padding(3.dp)
        .shadow(2.dp, shape = RoundedCornerShape(10.dp), ambientColor = Color.LightGray),
        shape = RoundedCornerShape(10.dp)
    ) {
        Row(modifier = Modifier
            .background(color = Color.LightGray)
            .height(150.dp),
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
            Column(modifier = Modifier.weight(0.7f).padding(2.dp)) {
                Row()
                {
                    Text(
                        "NAME",
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleMedium)
                }
                Row {
                    Text("RATING")
                }
                Row {
                    Text("STYLE".plus(" ").plus("PRICE"))
                }
                Row {
                    Text("STREET ADDRESS")
                }
                Row {
                    Text("POSTAL INFO")
                }
                Row {
                    Text("OPEN/CLOSED")
                }
            }
        }
    }
}

@Composable
fun RestaurantList(modifier: Modifier = Modifier) {
    LazyColumn(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.Start) {
        items(3) {
                i -> ListItem(modifier = Modifier.padding(8.dp)
        )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun RListPreview() {
    RestaurantReviewAppTheme {
        RestaurantList()
    }
}