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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawOpacity
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.ui.tooling.preview.Preview
import com.example.podcast.ui.components.NetworkImage
import com.example.podcast.ui.components.SeekBar
import com.example.podcast.ui.styles.PodcastTheme
import kotlin.math.roundToInt

@Composable
private fun PlayButton(isPlaying: Boolean, onClick: () -> Unit = {}) {
    Box(
        modifier = Modifier.size(82.dp).background(Color.White, CircleShape)
            .clickable(onClick = onClick)
    ) {
        Icon(
            asset = if (isPlaying) Icons.Filled.PlayArrow else Icons.Filled.PlayArrow,
            modifier = Modifier.align(
                Alignment.Center
            ).size(68.dp)
        )
    }
}

@Composable
fun PlayerCard(
    title: String,
    imageUrl: String,
    isPlaying: Boolean = true,
    onPlayButtonClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val roundedCornerShape = RoundedCornerShape(36.dp)
    val (seek, setSeek) = remember { mutableStateOf(0.5f) }

    Column(
        modifier = modifier.background(Color(0xFF1c1c1e), roundedCornerShape)
            .clip(roundedCornerShape)
    ) {
        NetworkImage(url = imageUrl, modifier = Modifier.weight(1f).fillMaxWidth()) {
            Box(it)
        }

        Column(
            modifier = Modifier.weight(1f).padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    title,
                    style = TextStyle(Color.White, fontWeight = FontWeight.W500, fontSize = 18.sp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    "${(seek * 100).roundToInt()}% complete",
                    style = TextStyle(Color(0xFF959595), fontSize = 14.sp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            SeekBar(progress = seek, onChange = setSeek)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "5:21:08",
                    style = TextStyle(Color.White, fontSize = 14.sp)
                )
                Text(
                    "-3:27:22",
                    style = TextStyle(Color.White, fontSize = 14.sp)
                )
            }

            Spacer(Modifier.height(32.dp))

            PlayButton(true, onClick = onPlayButtonClick)

        }
    }
}


@Preview(showBackground = true)
@Composable
fun PlayerCardPreview() {
    PodcastTheme {
        PlayerCard(
            title = "Atomic Habits: An Easy & Proven Way to Build Good Habits & Break Bad Ones",
            imageUrl = "https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1535115320l/40121378._SY475_.jpg",
            modifier = Modifier.fillMaxHeight()
        )
    }
}
