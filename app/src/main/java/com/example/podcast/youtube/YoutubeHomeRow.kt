package com.example.podcast.youtube

import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.podcast.ui.components.NetworkImage


@Composable
fun YoutubeHomeRow(
    title: String,
    imageUrl: String,
    thumbnailUrl: String,
    channelName: String,
    views: String,
    duration: String,
    onClick: () -> Unit = {}
) {
    Column(Modifier.clickable(onClick = onClick)) {
        Box(Modifier.preferredHeight(180.dp)) {
            NetworkImage(url = imageUrl, Modifier.fillMaxSize()) {
                Box(it)
            }
            Box(
                modifier = Modifier.offset(x = (-4).dp, y = (-4).dp).align(Alignment.BottomEnd)
                    .background(Color.Black, RoundedCornerShape(4.dp)).padding(6.dp)


            ) {
                Text(
                    duration,
                    style = TextStyle(Color.White, fontSize = 14.sp),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        Row(Modifier.padding(12.dp)) {
            NetworkImage(url = thumbnailUrl, modifier = Modifier.size(42.dp).clip(CircleShape)) {
                Box(it)
            }
            Spacer(Modifier.width(12.dp))

            Column {
                Text(
                    title,
                    style = TextStyle(Color.White, fontSize = 16.sp, fontWeight = FontWeight.W500),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Row {
                    Text(channelName, style = TextStyle(Color(0xFFAEAEAE)))
                    Spacer(Modifier.width(4.dp))
                    Text(views, style = TextStyle(Color(0xFFAEAEAE)))
                }
            }

        }
    }
}
