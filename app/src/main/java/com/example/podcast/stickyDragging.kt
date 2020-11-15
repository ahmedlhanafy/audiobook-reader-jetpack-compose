package com.example.podcast

import android.app.Activity
import android.util.DisplayMetrics
import android.util.Log
import androidx.compose.animation.animate
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.gesture.scrollorientationlocking.Orientation
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.platform.DensityAmbient
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp


@Composable
fun Modifier.stickyDragging(yOffset: MutableState<Dp>): Modifier {
    this.then((with(DensityAmbient.current) {

        val displayMetrics = DisplayMetrics()
        (ContextAmbient.current as Activity)
            .windowManager
            .defaultDisplay
            .getMetrics(displayMetrics)
        val heightPx = displayMetrics.heightPixels.toDp()


        val handleDrag: Density.(Float) -> Unit = {
            yOffset.value += (it.toDp())
        }

        val handleDragStop: (velocity: Float) -> Unit = { velocity ->
            when {
                velocity < -5000 || (yOffset.value.times(-1)) > (heightPx / 2) -> {
                    yOffset.value = heightPx * -1
                }
                else -> {
                    yOffset.value = 0.toDp()
                }
            }
        }

        return Modifier.offset(y = animate(yOffset.value))
            .draggable(
                orientation = Orientation.Vertical,
                onDragStopped = handleDragStop,
                onDrag = handleDrag
            )
    }))
}
