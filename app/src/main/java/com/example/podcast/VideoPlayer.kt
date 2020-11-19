package com.example.podcast

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.onCommit
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util

@Composable
fun VideoPlayer(modifier: Modifier, url: String, isPlaying: Boolean = true, seek: Float = 0f) {
    // This is the official way to access current context from Composable functions
    val context = ContextAmbient.current

    // Do not recreate the player everytime this Composable commits
    val exoPlayer = remember {
        SimpleExoPlayer.Builder(context).build()
    }

    onCommit(url) {
        val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(
            context,
            Util.getUserAgent(context, context.packageName)
        )

        val source = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(
                Uri.parse(
                    url
                )
            )

        exoPlayer.prepare(source)
    }

    onCommit(isPlaying) {
        exoPlayer.playWhenReady = isPlaying
    }

    onCommit(seek) {
        exoPlayer.seekTo((exoPlayer.duration * seek.toLong()))
    }

    AndroidView({
        PlayerView(it)
    }, modifier = modifier) { view ->
        view.player = exoPlayer
        view.useController = false
        view.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM

    }
}