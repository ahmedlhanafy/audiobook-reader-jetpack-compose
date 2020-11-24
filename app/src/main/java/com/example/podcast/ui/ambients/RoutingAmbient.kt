package com.example.podcast.ui.ambients

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.ambientOf

enum class SelectedScreen {
    PODCAST, YOUTUBE
}

val CurrentScreenAmbient = ambientOf<MutableState<SelectedScreen>> { error("No user found!") }

