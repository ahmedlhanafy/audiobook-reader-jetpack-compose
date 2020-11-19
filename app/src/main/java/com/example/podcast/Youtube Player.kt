package com.example.podcast

import android.util.DisplayMetrics
import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawOpacity
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.platform.DensityAmbient
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun getMaxHeight(): Dp {
    val displayMetrics = DisplayMetrics()
    ContextAmbient.current.display?.getMetrics(displayMetrics)

    return with(DensityAmbient.current) { displayMetrics.heightPixels.toDp() }  // 140dp is an arbitrary number
}

val PLAYER_HEIGHT = 240.dp
const val MAX_Y_SCALE = 0.3f

@Composable
fun OverlayPlayer() {
    var isPlaying by remember { mutableStateOf(true) }
    val height = getMaxHeight() - (PLAYER_HEIGHT * MAX_Y_SCALE)
    val stickyDraggingConfig = remember(height) { StickyDraggingConfig(0.dp, height) }

    val opacity = interpolate(stickyDraggingConfig.progress, 0f..1f, 1f..0f)
    val scaleY = interpolate(stickyDraggingConfig.progress, 0f..1f, 1f..0.3f)
    val scaleX = interpolate(
        stickyDraggingConfig.progress,
        0.7f..1f,
        1f..MAX_Y_SCALE
    )
    val iconButtonsOpacity = interpolate(
        stickyDraggingConfig.progress,
        0.7f..1f,
        0f..1f
    )
    val iconButtonsModifier = Modifier.fillMaxHeight().width(56.dp).drawOpacity(iconButtonsOpacity)


    Column {
        Row(
            Modifier.stickyDrag(config = stickyDraggingConfig)
                .clickable {
                    if (stickyDraggingConfig.isExpanded)
                        stickyDraggingConfig.reset()
                }.background(Color(0xFF292929))
                .height(PLAYER_HEIGHT * scaleY)

        ) {
            VideoPlayer(
                url = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
                modifier = Modifier.fillMaxWidth(scaleX)
                    .clickable(
                        enabled = !stickyDraggingConfig.isExpanded,
                        onClick = { isPlaying = !isPlaying }),
                isPlaying = isPlaying,
            )

            Box(Modifier.padding(8.dp).fillMaxHeight().weight(1f)) {
                Text(
                    "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries",
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    fontSize = 15.sp,
                    style = TextStyle(Color.White, fontWeight = FontWeight.W400)
                )
            }

            Icon(
                asset = Icons.Default.PlayArrow,
                tint = Color.White,
                modifier = iconButtonsModifier.clickable {}
            )
            Icon(
                asset = Icons.Default.Close,
                tint = Color.White,
                modifier = iconButtonsModifier.clickable {}
            )
        }

        Box(
            modifier = Modifier.drawOpacity(opacity).offset(y = stickyDraggingConfig.offset)
                .fillMaxWidth()
                .weight(1f)
                .background(Color.Black)

        )
    }
}

@Composable
fun YoutubeHomeRow(
    title: String,
    imageUrl: String,
    thumbnailUrl: String,
    channelName: String,
    views: String,
    duration: String
) {
    Column {
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


@Composable
fun YoutubeHome() {
    Column(Modifier.fillMaxSize()) {
        YoutubeHomeRow(
            title = "Hello world this is a new video",
            imageUrl = "https://static.toiimg.com/thumb/72975551.cms?width=680&height=512&imgsize=881753",
            duration = "2:32",
            channelName = "Jetpack Compose",
            thumbnailUrl = "https://static.toiimg.com/thumb/72975551.cms?width=680&height=512&imgsize=881753",
            views = "21M views"
        )

    }
}

@Composable
fun YoutubePlayer() {
    Box {
        Column {
            Scaffold(
                backgroundColor = Color(0xFF292929),
                topBar = {
                    TopAppBar(title = { Text("Youtube") })
                }
            ) {
                YoutubeHome()
            }
        }

        OverlayPlayer()
    }
}