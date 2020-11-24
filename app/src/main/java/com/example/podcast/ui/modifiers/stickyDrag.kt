package com.example.podcast.ui.modifiers

import androidx.compose.animation.animate
import androidx.compose.animation.core.SpringSpec
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
import kotlin.math.abs


class StickyDraggingConfig(
    private val initiallyExpanded: Boolean,
    private val minSize: Dp = 0.dp,
    private val maxSize: Dp,
) {
    private val initialOffset: Dp = if (initiallyExpanded) maxSize else minSize

    private var _offset by mutableStateOf(initialOffset)
    private var originalOffset: Dp? = null;

    var isDragging by mutableStateOf(false)
        private set

    val isExpanded get() = _offset == maxSize

    @Composable
    val offset
        get() = animate(_offset, )

    @Composable
    val progress
        get() = offset / maxSize


    internal fun onDragStart(startedPosition: Offset) {
        isDragging = true
        originalOffset = _offset
    }

    fun onDrag(distance: Dp) {
        val newVal = _offset + distance


        if (newVal in minSize..maxSize) {
            _offset = newVal
        }
    }

    fun onDragEnd(velocity: Float) {
        val progress = _offset / maxSize
        isDragging = false

        _offset = if (abs(velocity) > 200) {
            if (originalOffset == maxSize) {
                minSize
            } else {
                maxSize
            }
        } else {
            if (progress > 0.5f) {
                maxSize
            } else {
                minSize
            }
        }

        originalOffset = null
    }

    fun expand() {
        _offset = if (this.initiallyExpanded) minSize else maxSize
    }

    fun reset() {
        _offset = initialOffset
    }
}

@Composable
fun Modifier.stickyDrag(config: StickyDraggingConfig): Modifier {
    return this.offset(y = config.offset)
        .draggable(
            orientation = Orientation.Vertical,
            onDragStarted = config::onDragStart,
            onDragStopped = config::onDragEnd,
        ) { config.onDrag(it.toDp()) }
}
