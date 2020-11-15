package com.example.podcast

import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.WithConstraints
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.DensityAmbient
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.ui.tooling.preview.Preview
import com.example.podcast.ui.PodcastTheme


@Composable
private fun PlayButton(isPlaying: Boolean, onClick: () -> Unit = {}) {
    Box(
        modifier = Modifier.size(52.dp).background(Color.White, CircleShape)
            .clickable(onClick = onClick)
    ) {
        Icon(
            asset = if (isPlaying) Icons.Filled.PlayArrow else Icons.Filled.PlayArrow,
            modifier = Modifier.align(
                Alignment.Center
            ).size(32.dp)
        )
    }
}



@Composable
fun FloatingPlayer(
    title: String,
    duration: String,
    imageUrl: String,
    isPlaying: Boolean = true,
    onPlayButtonClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val roundedCornerShape = RoundedCornerShape(36.dp)
    Row(
        modifier = modifier
            .padding(16.dp)
            .drawShadow(8.dp, roundedCornerShape)
            .background(Color(0xFF303030), roundedCornerShape)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        PlayButton(isPlaying = isPlaying, onClick = onPlayButtonClick)
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                title,
                style = TextStyle(Color.White, fontWeight = FontWeight.W500, fontSize = 18.sp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                duration,
                style = TextStyle(Color(0xFF959595), fontSize = 14.sp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            SeekBar(progress = 0.1f)
        }
        Spacer(modifier = Modifier.width(12.dp))
        NetworkImage(
            imageUrl,
            modifier = Modifier.clip(RoundedCornerShape(12.dp)).background(Color(0xFF959595))
                .size(56.dp)

        ) {
            Box(modifier = it)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun FloatingPlayerPreview() {
    PodcastTheme {
        FloatingPlayer(
            title = "Atomic Habits: An Easy & Proven Way to Build Good Habits & Break Bad Ones",
            duration = "-3:42:56",
            imageUrl = "https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1535115320l/40121378._SY475_.jpg",
        )
    }
}
