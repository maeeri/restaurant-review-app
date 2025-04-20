package com.example.restaurantreviewapp.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.restaurantreviewapp.ui.theme.DarkGreen
import com.example.restaurantreviewapp.ui.theme.RestaurantReviewAppTheme
import com.example.restaurantreviewapp.ui.theme.Turquoise
import java.math.RoundingMode

@Composable
fun AddReviewForm(modifier: Modifier = Modifier, onDismissRequest: () -> Unit) {
    var description by remember { mutableStateOf("") }
    var review by remember { mutableFloatStateOf(0f) }
    CustomCard {
        Column(modifier.padding(30.dp)) {
            Row {
                Text("Review this restaurant")
            }
            Row {
                Slider(
                    value = review,
                    onValueChange = { review = it },
                    steps = 9,
                    valueRange = 0f..5f,
                    colors = SliderDefaults.colors(
                        thumbColor = Turquoise,
                        activeTrackColor = Color.Gray,
                        inactiveTrackColor = Color.LightGray,
                        activeTickColor = DarkGreen,
                        inactiveTickColor = Turquoise
                    )
                )
            }
            Row {
                Text(review.toBigDecimal().setScale(1, RoundingMode.UP).toDouble().toString())
            }
            Row {
                TextField(
                    modifier = modifier,
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Your review") },
                    maxLines = 10
                )
            }
            Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Button(
                        onClick = {
                            println(description)
                            println(review.toBigDecimal().setScale(1, RoundingMode.UP).toDouble().toString())
                        },
                        colors = ButtonColors(Turquoise, Color.White, Color.Gray, Color.Black)
                    ) { Text("Submit")}
                }
                Column {
                    ElevatedButton(
                        onClick = { onDismissRequest() },
                        colors = ButtonColors(Color.White, Turquoise, Color.Gray, Color.Black)
                    ) { Text("Cancel")}
                }
            }
        }
    }
}

@Composable
fun AddReviewModal(modifier: Modifier = Modifier,
                   onDismissRequest: () -> Unit,
                   visible: Boolean) {
    if (visible) {
        Dialog(
            onDismissRequest = {/*do nothing */}
        ) {
            AddReviewForm(modifier, onDismissRequest)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReviewPreview() {
    RestaurantReviewAppTheme {
        AddReviewModal(Modifier, { println("hi")}, true)
    }
}

