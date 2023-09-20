package com.nikola.paintapp.utilities

import androidx.compose.ui.geometry.Offset
import java.lang.Float.max

fun getRadius(start: Offset,moving:Offset): Float {
    return max(max(
        moving.x - start.x,
        moving.y - start.y,
    ),max(
        start.x - moving.x,
        start.y - moving.y,
    ))
}