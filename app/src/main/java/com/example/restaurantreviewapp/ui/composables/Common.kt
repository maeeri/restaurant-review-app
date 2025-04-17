package com.example.restaurantreviewapp.ui.composables

import StarEmpty
import StarFull
import StarHalf
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.restaurantreviewapp.ui.theme.Grey

@Composable
fun CustomIcon(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    blurRadius: Dp = 2.dp,
    iconTintColor: Color = Color(0xfffff520),
    backGroundColor: Color = Color.Gray.copy(alpha = 0.8f),
    yShadowOffset: Dp = 1.dp,
    xShadowOffset: Dp = 1.dp
) {
    //Icon shadow modified from https://medium.com/@yuriyskul/jetpack-compose-creating-direct-and-spread-light-shadows-for-vector-drawables-api-12-230362982a0f
    Box(modifier = Modifier.background(color = Grey,shape = CircleShape),
        contentAlignment = Alignment.Center) {
        Icon(
            rememberVectorPainter(imageVector),
            contentDescription = null,
            modifier = Modifier
                .offset(x = xShadowOffset, y = yShadowOffset)
                .blur(blurRadius),
            tint = backGroundColor,
        )
        Icon(
            rememberVectorPainter(imageVector),
            modifier = Modifier,
            contentDescription = null,
            tint = iconTintColor
        )
    }
}


@Composable
fun StarRating(modifier: Modifier = Modifier, rating: Float, ratingCount: Int) {
    for (i in 1..5) {
        if (i <= rating) {
            CustomIcon(imageVector = StarFull)
        } else if (i - rating < 1) {
            CustomIcon(imageVector = StarHalf)
        } else {
            CustomIcon(imageVector = StarEmpty)
        }
    }
    Text(rating.toString().plus(" (".plus(ratingCount).plus(")")))
}

@Composable
fun ListItem(modifier: Modifier = Modifier, child: @Composable() () -> Unit) {
    Card(modifier = Modifier
        .padding(7.dp)
        .shadow(2.dp, shape = RoundedCornerShape(10.dp), ambientColor = Color.LightGray),
        shape = RoundedCornerShape(10.dp)
    ) {
        child()
    }
}