package com.example.podcast

import androidx.compose.animation.animate
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.gesture.scrollorientationlocking.Orientation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


class StickyDraggingConfig(private val initialOffset: Dp = 0.dp, private val maxSize: Dp) {
    private var _offset by mutableStateOf(initialOffset)

    var isDragging by mutableStateOf(false)
        private set

    val isExpanded get() = _offset == maxSize

    @Composable
    val offset
        get() = animate(_offset)

    @Composable
    val progress
        get() = offset / maxSize


    internal fun onDragStart(startedPosition: Offset) {
        isDragging = true
    }

    fun onDrag(distance: Dp) {
        val newVal = _offset + distance

        if (newVal in initialOffset..maxSize) {
            _offset = newVal
        }
    }

    fun onDragEnd(velocity: Float) {
        val progress = _offset / maxSize

        isDragging = false



        _offset = if (progress > 0.5f || (velocity < -5000 && isExpanded)) {
            maxSize
        } else {
            initialOffset
        }
    }

    fun reset() {
        _offset = 0.dp
    }
}

@Composable
fun Modifier.stickyDrag(config: StickyDraggingConfig): Modifier {
    return this.offset(y = config.offset)
        .draggable(
            orientation = Orientation.Vertical,
            onDragStarted = config::onDragStart,
            onDragStopped = config::onDragEnd,
        ) {
            config.onDrag(it.toDp())
        }
}
