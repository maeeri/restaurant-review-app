package com.example.restaurantreviewapp.ui.composables

import StarEmpty
import StarFull
import StarHalf
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
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
import com.example.restaurantreviewapp.ui.theme.DarkCardBackground
import com.example.restaurantreviewapp.ui.theme.LightCardBackground
import com.example.restaurantreviewapp.ui.theme.Turquoise
import com.example.restaurantreviewapp.ui.theme.Yellow

@Composable
fun CustomIcon(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    blurRadius: Dp = 2.dp,
    iconTintColor: Color = Yellow,
    backGroundColor: Color = Color.Gray.copy(alpha = 0.8f),
    yShadowOffset: Dp = 1.dp,
    xShadowOffset: Dp = 1.dp
) {
    //Icon shadow modified from https://medium.com/@yuriyskul/jetpack-compose-creating-direct-and-spread-light-shadows-for-vector-drawables-api-12-230362982a0f
    Box(modifier = Modifier.background(color = backGroundColor,shape = CircleShape),
        contentAlignment = Alignment.Center) {
        Icon(
            rememberVectorPainter(imageVector),
            contentDescription = null,
            modifier = modifier
                .offset(x = xShadowOffset, y = yShadowOffset)
                .blur(blurRadius),
            tint = Color.Gray.copy(alpha = 0.8f),
        )
        Icon(
            rememberVectorPainter(imageVector),
            modifier = modifier,
            contentDescription = null,
            tint = iconTintColor
        )
    }
}


@Composable
fun StarRating(modifier: Modifier = Modifier, rating: Float) {
    val background = if (isSystemInDarkTheme()) DarkCardBackground else LightCardBackground
    Row {
        for (i in 1..5) {
            if (i <= rating) {
                CustomIcon(imageVector = StarFull, backGroundColor = background)
            } else if (i - rating < 1) {
                CustomIcon(imageVector = StarHalf, backGroundColor = background)
            } else {
                CustomIcon(imageVector = StarEmpty, backGroundColor = background)
            }
        }
        Spacer(Modifier.padding(3.dp))
        Text(rating.toString(), modifier.align(Alignment.CenterVertically))
    }
}

@Composable
fun CustomCard(modifier: Modifier = Modifier,
               child: @Composable (modifier: Modifier) -> Unit) {
    val backGroundColor: Color = if (isSystemInDarkTheme()) DarkCardBackground else LightCardBackground
    Card(modifier = modifier
        .padding(7.dp)
        .shadow(2.dp, shape = RoundedCornerShape(10.dp), ambientColor = Color.LightGray)
        .background(backGroundColor),
        shape = RoundedCornerShape(10.dp)
    ) {
        child(modifier.background(backGroundColor))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(modifier: Modifier = Modifier,
           text: String,
           navigationButton: @Composable () -> Unit) {
    TopAppBar({ Text(text)},
        modifier,
        navigationIcon = navigationButton,
        colors = TopAppBarColors(Turquoise, Color.Yellow, Color.White, Color.White, Color.White)
    )
}


