package com.example.restaurantreviewapp.ui.composables

import StarEmpty
import StarFull
import StarHalf
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.navigation.NavController
import com.example.restaurantreviewapp.ui.theme.CardBackground
import com.example.restaurantreviewapp.ui.theme.Turquoise

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
    Box(modifier = Modifier.background(color = CardBackground,shape = CircleShape),
        contentAlignment = Alignment.Center) {
        Icon(
            rememberVectorPainter(imageVector),
            contentDescription = null,
            modifier = modifier
                .offset(x = xShadowOffset, y = yShadowOffset)
                .blur(blurRadius),
            tint = backGroundColor,
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
    Row {
        for (i in 1..5) {
            if (i <= rating) {
                CustomIcon(imageVector = StarFull)
            } else if (i - rating < 1) {
                CustomIcon(imageVector = StarHalf)
            } else {
                CustomIcon(imageVector = StarEmpty)
            }
        }
        Spacer(Modifier.padding(3.dp))
        Text(rating.toString(), modifier.align(Alignment.CenterVertically))
    }
}

@Composable
fun CustomCard(modifier: Modifier = Modifier, child: @Composable() (modifier: Modifier) -> Unit) {
    Card(modifier = modifier
        .padding(7.dp)
        .shadow(2.dp, shape = RoundedCornerShape(10.dp), ambientColor = Color.LightGray)
        .background(CardBackground),
        shape = RoundedCornerShape(10.dp)
    ) {
        child(modifier.background(CardBackground))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(modifier: Modifier = Modifier, text: String, navController: NavController) {
    TopAppBar({ Text(text)}, modifier.clickable { navController.navigate("RestaurantListPage") },
        navigationIcon = {
        IconButton(onClick = { /* do something */ }) {
            Icon(
                imageVector = Icons.Filled.Menu,
                contentDescription = "menu"
            )
        }
    },
        colors = TopAppBarColors(Turquoise, Color.Yellow, Color.White, Color.White, Color.White)
    )
}

