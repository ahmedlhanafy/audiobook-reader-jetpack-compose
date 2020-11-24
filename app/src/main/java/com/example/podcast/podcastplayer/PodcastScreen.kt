package com.example.podcast.podcastplayer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.podcast.FloatingPlayer
import com.example.podcast.PlayerCard
import com.example.podcast.helpers.screenDimensions
import com.example.podcast.ui.ambients.CurrentScreenAmbient
import com.example.podcast.ui.ambients.SelectedScreen
import com.example.podcast.ui.modifiers.StickyDraggingConfig
import com.example.podcast.ui.modifiers.stickyDrag


const val title = "Atomic Habits: An Easy & Proven Way to Build Good Habits & Break Bad Ones"
const val imageUrl =
    "https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1535115320l/40121378._SY475_.jpg"

@Composable
fun PodcastScreen() {
    val (_,setCurrentScreen) = CurrentScreenAmbient.current
    val height = screenDimensions().height
    val stickyDraggingConfig = remember(height) { StickyDraggingConfig(true, 0.dp, height) }

    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        PlayerCard(
            title = title,
            imageUrl = imageUrl,
            onPlayButtonClick = { stickyDraggingConfig.reset() },
            modifier = Modifier.stickyDrag(stickyDraggingConfig).fillMaxSize().padding(16.dp)
        )
        FloatingPlayer(
            title = title,
            imageUrl = imageUrl,
            duration = "-3:42:56",
            onPlayButtonClick = {
                setCurrentScreen(SelectedScreen.YOUTUBE)
            },
            modifier = Modifier
                .offset(y = (-140).dp)
                .stickyDrag(stickyDraggingConfig)
                .clickable(onClick = { stickyDraggingConfig.expand() })
        )
    }
}
