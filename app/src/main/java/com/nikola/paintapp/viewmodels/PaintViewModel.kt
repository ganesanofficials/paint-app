package com.nikola.paintapp.viewmodels

import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nikola.paintapp.utilities.file.StorageHelper
import com.nikola.paintapp.utilities.data.SaveFormat
import com.nikola.paintapp.utilities.data.MotionEvent
import com.nikola.paintapp.utilities.data.PathStroke
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.FileOutputStream


class PaintViewModel : ViewModel() {
    var paths = mutableStateListOf<PathStroke>()
    var pathsUndone = mutableStateListOf<PathStroke>()
    var motionEvent by mutableStateOf(MotionEvent.Idle)
    var currentPath by mutableStateOf(PathStroke())
    var canvasScale by mutableFloatStateOf(value = 1f)
    var canvasSize: Size? = null
    var currentPosition by mutableStateOf(Offset.Unspecified)
    var canvasTranslate by mutableStateOf(Offset(x = 0f, y = 0f))
    val canvasPivot by mutableStateOf(Offset(x = 0f, y = 0f))

    var mainToolBarVisibility by mutableStateOf(false)

    fun saveBitmap(format: SaveFormat,bitmap:ImageBitmap) {
        if (canvasSize != null && paths.isNotEmpty()) {
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
//                    val bitmap =
//                        Bitmap.createBitmap(
//                            canvasSize!!.width.toInt(),
//                            canvasSize!!.height.toInt(),
//                            Bitmap.Config.ARGB_8888
//                        )
//                    val canvas = Canvas(bitmap)
//                    format.backgroundColor?.let { bgColor ->
//                        canvas.drawPaint(bgColor)
//                    }
//                    canvas.translate(canvasTranslate.x, canvasTranslate.y)
//                    canvas.scale(canvasScale, canvasScale)
                    val outputStream =
                        FileOutputStream(StorageHelper.getOutputFile(format.extension))
//
//                    paths.forEach { path ->
//                        path.drawNative(canvas)
//                    }
                    try {
                        bitmap.asAndroidBitmap().compress(format.format, 100, outputStream)
                    }catch (_:Exception){

                    }finally {
                        outputStream.flush()
                        outputStream.close()
                    }

                }
            }
        }
    }
}