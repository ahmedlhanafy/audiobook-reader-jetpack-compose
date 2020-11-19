package com.example.podcast

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.animate
import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.WithConstraints
import androidx.compose.ui.draw.drawOpacity
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.platform.DensityAmbient
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.ui.tooling.preview.Preview
import com.example.podcast.ui.PodcastTheme


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PodcastTheme {
                Box {
                    YoutubePlayer()
                }

            }
        }
    }
}

@Composable
fun StatusBar(color: Color) {
    val activity = (ContextAmbient.current as Activity)
    onCommit(color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = activity.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = color.hashCode()
        }
    }
}


@Composable
fun MainScreen() {
    WithConstraints {
        val screenHeight = with(DensityAmbient.current) { constraints.maxHeight.toDp() }
        val yOffset = remember { mutableStateOf(0.dp) }

        Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
            PlayerCard(
                title = "Atomic Habits: An Easy & Proven Way to Build Good Habits & Break Bad Ones",
                imageUrl = "https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1535115320l/40121378._SY475_.jpg",
                onPlayButtonClick = { yOffset.value = 0.dp },
                modifier = Modifier.padding(16.dp).align(Alignment.Center).fillMaxSize()
                    .offset(y = animate(yOffset.value + screenHeight + 40.dp))

            )
            FloatingPlayer(
                title = "Atomic Habits: An Easy & Proven Way to Build Good Habits & Break Bad Ones",
                duration = "-3:42:56",
                imageUrl = "https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1535115320l/40121378._SY475_.jpg",
                modifier = Modifier
                    .stickyDragging(yOffset)
                    .align(Alignment.BottomCenter)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PodcastTheme {
        MainScreen()
    }
}