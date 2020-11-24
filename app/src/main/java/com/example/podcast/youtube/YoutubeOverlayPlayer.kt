package com.example.podcast.youtube

import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawOpacity
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.podcast.helpers.interpolate
import com.example.podcast.helpers.screenDimensions
import com.example.podcast.ui.components.VideoPlayer
import com.example.podcast.ui.modifiers.StickyDraggingConfig
import com.example.podcast.ui.modifiers.stickyDrag


val PLAYER_HEIGHT = 240.dp
const val MAX_Y_SCALE = 0.3f

@Composable
fun OverlayPlayer(requestClose: () -> Unit) {
    var isPlaying by remember { mutableStateOf(true) }
    val height = screenDimensions().height - (PLAYER_HEIGHT * MAX_Y_SCALE)
    val stickyDraggingConfig = remember(height) { StickyDraggingConfig(false, 0.dp, height) }

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
            Modifier
                .stickyDrag(config = stickyDraggingConfig)
                .clickable {
                    stickyDraggingConfig.expand()
                }
                .background(Color(0xFF292929))
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
                modifier = iconButtonsModifier.clickable(onClick = requestClose)
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