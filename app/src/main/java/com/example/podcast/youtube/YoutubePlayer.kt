package com.example.podcast.youtube

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.podcast.ui.ambients.CurrentScreenAmbient
import com.example.podcast.ui.ambients.SelectedScreen
import com.example.podcast.ui.components.NetworkImage

@Composable
fun YoutubeHome(openPlayer: () -> Unit) {
    Column(Modifier.fillMaxSize()) {
        YoutubeHomeRow(
            title = "Hello world this is a new video",
            imageUrl = "https://static.toiimg.com/thumb/72975551.cms?width=680&height=512&imgsize=881753",
            duration = "2:32",
            channelName = "Jetpack Compose",
            thumbnailUrl = "https://static.toiimg.com/thumb/72975551.cms?width=680&height=512&imgsize=881753",
            views = "21M views",
            onClick = openPlayer
        )
    }
}

@ExperimentalAnimationApi
@Composable
fun YoutubeScreen() {
    val (_, setCurrentScreen) = CurrentScreenAmbient.current
    var isPlayerDisplayed by remember { mutableStateOf(true) }

    Box {
        Column {
            Scaffold(
                backgroundColor = Color(0xFF292929),
                topBar = {
                    TopAppBar(title = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            NetworkImage(
                                url = "https://i.pinimg.com/originals/de/1c/91/de1c91788be0d791135736995109272a.png",
                                modifier = Modifier.size(36.dp)
                            )
                            Spacer(Modifier.width(4.dp))
                            Text("Youtube")
                        }
                    })
                }
            ) {
                YoutubeHome(openPlayer = { setCurrentScreen(SelectedScreen.PODCAST) })
            }
        }

        AnimatedVisibility(
            visible = isPlayerDisplayed,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            OverlayPlayer(requestClose = { isPlayerDisplayed = false })
        }

    }
}