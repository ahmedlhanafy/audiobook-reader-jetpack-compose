package com.example.podcast.helpers

import android.content.Context
import android.content.res.Configuration
import android.util.DisplayMetrics
import android.view.ViewConfiguration
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.platform.DensityAmbient
import androidx.compose.ui.unit.Dp


fun getStatusBarHeight(context: Context): Int {
    var result = 0
    val resourceId: Int =
        context.resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = context.resources.getDimensionPixelSize(resourceId)
    }
    return result
}

fun getNavigationBarHeight(context: Context): Int {
    val hasMenuKey = ViewConfiguration.get(context).hasPermanentMenuKey()
    val resourceId: Int =
        context.resources.getIdentifier("navigation_bar_height", "dimen", "android")
    return if (resourceId > 0 && !hasMenuKey) {
        context.resources.getDimensionPixelSize(resourceId)
    } else 0
}

data class ScreenDimensions(val height: Dp, val width: Dp)

@Composable
fun screenDimensions(): ScreenDimensions {
    val displayMetrics = DisplayMetrics()
    val context = ContextAmbient.current
    context.display?.getRealMetrics(displayMetrics)

    with(DensityAmbient.current) {
        var height = displayMetrics.heightPixels.toDp()
        var width = displayMetrics.widthPixels.toDp()
        val statusBarHeight = getStatusBarHeight(context).toDp()
        val navigationBarHeight = getNavigationBarHeight(context).toDp()
        val orientation: Int = context.resources.configuration.orientation

        return if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            height -= statusBarHeight
            width -= navigationBarHeight
            ScreenDimensions(height = height, width = width)
        } else {
            height -= (statusBarHeight + navigationBarHeight)
            ScreenDimensions(height = height, width = width)
        }

    }
}