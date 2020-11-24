package com.example.podcast

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Providers
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.setContent
import com.example.podcast.podcastplayer.PodcastScreen
import com.example.podcast.ui.ambients.CurrentScreenAmbient
import com.example.podcast.ui.ambients.SelectedScreen
import com.example.podcast.ui.styles.PodcastTheme
import com.example.podcast.youtube.YoutubeScreen


@ExperimentalAnimationApi
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val currentScreen = remember { mutableStateOf((SelectedScreen.YOUTUBE)) }
            Providers(CurrentScreenAmbient provides currentScreen) {
                PodcastTheme {
                    Box {
                        Crossfade(current = currentScreen) {
                            when (it.value) {
                                SelectedScreen.PODCAST -> PodcastScreen()
                                SelectedScreen.YOUTUBE -> YoutubeScreen()
                            }
                        }
                    }
                }

            }
        }
    }
}

