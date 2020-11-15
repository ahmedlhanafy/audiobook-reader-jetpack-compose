package com.example.podcast

import android.util.Log
import androidx.compose.animation.animate
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.WithConstraints
import androidx.compose.ui.draw.drawShadow
import androidx.compose.ui.gesture.pressIndicatorGestureFilter
import androidx.compose.ui.gesture.scrollorientationlocking.Orientation
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.DensityAmbient
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.example.podcast.ui.PodcastTheme

val MAX_KNOB_HEIGHT = 20.dp
val KNOB_BORDER = 2.dp

@Composable
fun SeekBar(progress: Float, onChange: ((Float) -> Unit)? = null) {
    with(DensityAmbient.current) {
        WithConstraints {

            val controlled = onChange != null
            val (isDragging, setIsDragging) = remember { mutableStateOf(false) }
            val size = animate(if (isDragging) MAX_KNOB_HEIGHT else 16.dp)
            val onDrag: Density.(Float) -> Unit = {
                val oldWidth = (constraints.maxWidth * progress).toDp()
                val newWidth = oldWidth + it.toDp()
                val newProgress = newWidth * progress / oldWidth

                if (newProgress in 0.0..1.0) {
                    onChange?.invoke(newProgress)
                }
            }
            val onDragStart = { setIsDragging(true) }
            val onDragStop = { setIsDragging(false) }

            Box(
                alignment = Alignment.Center, modifier = Modifier.fillMaxWidth().height(
                    MAX_KNOB_HEIGHT + KNOB_BORDER
                )
            ) {
                Box(modifier = Modifier.fillMaxWidth().height(2.dp).background(Color(0xFF646464))) {
                    Box(
                        modifier = Modifier.width((constraints.maxWidth * progress).toDp())
                            .fillMaxHeight()
                            .background(Color(0xFFFFF370))
                    )
                }

                if (controlled) {
                    Box(
                        modifier = Modifier
                            .offset(x = (constraints.maxWidth * progress).toDp() - size / 2)
                            .size(size)
                            .drawShadow(6.dp, CircleShape)
                            .background(Color.White, CircleShape)
                            .border(KNOB_BORDER, Color.LightGray, CircleShape)
                            .align(Alignment.CenterStart)
                            .pressIndicatorGestureFilter(
                                onStart = { onDragStart() },
                                onStop = { onDragStop() })
                            .draggable(
                                onDrag = onDrag,
                                onDragStarted = { onDragStart() },
                                onDragStopped = { onDragStop() },
                                orientation = Orientation.Horizontal
                            )
                    )
                }
            }
        }
    }

}

@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun SeekBarPreview() {
    val (state, setState) = remember { mutableStateOf(0.5f) }
    PodcastTheme {
        SeekBar(progress = state, onChange = setState)
    }
}