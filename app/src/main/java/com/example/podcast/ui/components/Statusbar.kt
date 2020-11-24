package com.example.podcast.ui.components

import android.app.Activity
import android.os.Build
import android.view.Window
import android.view.WindowManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.onCommit
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ContextAmbient

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
