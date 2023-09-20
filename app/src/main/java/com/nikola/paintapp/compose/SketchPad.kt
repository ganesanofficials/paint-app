package com.nikola.paintapp.compose

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.gestures.calculateZoom
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nikola.paintapp.utilities.data.DrawType
import com.nikola.paintapp.utilities.data.MotionEvent
import com.nikola.paintapp.utilities.data.PathStroke
import com.nikola.paintapp.utilities.getRadius
import com.nikola.paintapp.viewmodels.PaintViewModel
import java.lang.Float.max
import java.lang.Float.min

@Composable
fun SketchPad(
    viewModel: PaintViewModel = viewModel()
) {

    val isStrokeFinished = remember {
        mutableStateOf(true)
    }

    val drawModifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
        .pointerInput(Unit) {
            awaitEachGesture {
                val downEvent = awaitFirstDown()
                viewModel.currentPosition =
                    (downEvent.position - viewModel.canvasTranslate) / viewModel.canvasScale

                if (isStrokeFinished.value) {
                    viewModel.motionEvent = MotionEvent.Down
                }

                if (downEvent.pressed != downEvent.previousPressed) downEvent.consume()
                var canvasMoved = false
                do {
                    val event = awaitPointerEvent()
                    if (event.changes.size == 1) {
                        if (canvasMoved) break
                        viewModel.currentPosition =
                            (event.changes[0].position - viewModel.canvasTranslate) / viewModel.canvasScale
                        viewModel.motionEvent = MotionEvent.Move
                        if (event.changes[0].positionChange() != Offset.Zero) event.changes[0].consume()
                    } else if (event.changes.size > 1) {
                        val zoom = event.calculateZoom()
                        viewModel.canvasScale = (viewModel.canvasScale * zoom).coerceIn(0.5f..2f)
                        val offset = event.calculatePan()
                        viewModel.canvasTranslate += offset //- (canvasPivot / zoom*2f)
                        canvasMoved = true
                    }
                } while (event.changes.any { it.pressed })
                viewModel.motionEvent = MotionEvent.Up

            }
        }
     Canvas(modifier = drawModifier) {
        withTransform({
            translate(viewModel.canvasTranslate.x, viewModel.canvasTranslate.y)
            scale(viewModel.canvasScale, viewModel.canvasScale, viewModel.canvasPivot)
        }) {
            with(drawContext.canvas.nativeCanvas) {
                val checkPoint = saveLayer(null, null)
                when (viewModel.motionEvent) {
                    MotionEvent.Idle -> Unit
                    MotionEvent.Down -> {
                        if(isStrokeFinished.value){
                            isStrokeFinished.value = false
                            viewModel.paths.add(viewModel.currentPath)
                           viewModel.currentPath.start = viewModel.currentPosition
                            viewModel.currentPath.end = viewModel.currentPosition
                        }

                        viewModel.currentPath.path.moveTo(
                            viewModel.currentPosition.x, viewModel.currentPosition.y
                        )
                    }

                    MotionEvent.Move -> {
                        if(viewModel.mainToolBarVisibility)
                            viewModel.mainToolBarVisibility = false
                        viewModel.currentPath.path.lineTo(
                            viewModel.currentPosition.x, viewModel.currentPosition.y
                        )
                        viewModel.currentPath.end= viewModel.currentPosition
                    }

                    MotionEvent.Up -> {
                        isStrokeFinished.value = true
                        viewModel.currentPath.path.lineTo(
                            viewModel.currentPosition.x, viewModel.currentPosition.y
                        )
                        viewModel.currentPath = PathStroke(
                            path = Path(),
                            strokeWidth = viewModel.currentPath.strokeWidth,
                            color = viewModel.currentPath.color,
                            drawType = viewModel.currentPath.drawType,
                            start = viewModel.currentPath.start,
                            end = viewModel.currentPath.end
                        )
                        viewModel.pathsUndone.clear()
                        viewModel.currentPosition = Offset.Unspecified
                        viewModel.motionEvent = MotionEvent.Idle
                        viewModel.canvasSize = this@Canvas.size
                    }
                }

                viewModel.paths.forEach { path ->
                    path.draw(this@withTransform )
                }
                restoreToCount(checkPoint)
            }
        }
    }
}