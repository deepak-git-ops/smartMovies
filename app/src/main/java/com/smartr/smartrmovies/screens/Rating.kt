package com.smartr.smartrmovies.screens

import android.graphics.drawable.Icon

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.ui.unit.dp


@Composable
fun RatingBar(
    maxRating: Int = 10, currentRating: Double,
    starsColor: Color = Color.Yellow
) {

    Row (Modifier.drawBehind {
        drawRect(
            color = Color.Gray, size = size)
    }) {

        for(i in 1  ..  maxRating) {

            Icon(
                imageVector = if(i <= currentRating) Icons.Filled.Star
                else Icons.Filled.StarOutline,
                contentDescription = null,
                tint = if(i < currentRating) starsColor
                else Color.Unspecified,
                modifier = Modifier
                    .padding(4.dp)


            )

        }
    }
}