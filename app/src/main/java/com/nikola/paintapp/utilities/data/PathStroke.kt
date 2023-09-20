package com.nikola.paintapp.utilities.data

import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Shader
import android.os.Build
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toArgb
import com.nikola.paintapp.utilities.getRadius

class PathStroke(
    var path: Path = Path(),
    var strokeWidth: Float = 10f,
    var color: Color = Color.Blue,
    var drawType: DrawType = DrawType.Pen,
    var start: Offset = Offset.Zero,
    var end:Offset  =Offset.Zero,
) {
//    private val paint: Paint
//        get() {
//            return if (drawType == DrawType.Pen) {
//                Paint().apply {
//                    color = this@PathStroke.color.toArgb()
//                    style = Paint.Style.STROKE
//                    strokeWidth = this@PathStroke.strokeWidth
//                    strokeCap = Paint.Cap.ROUND
//                    strokeJoin = Paint.Join.ROUND
//                }
//            } else {
//                Paint().apply {
//                    color = Color.Transparent.toArgb()
//                    style = Paint.Style.STROKE
//                    strokeWidth = this@PathStroke.strokeWidth
//                    strokeCap = Paint.Cap.ROUND
//                    strokeJoin = Paint.Join.ROUND
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
//                        blendMode = android.graphics.BlendMode.CLEAR
//                }
//            }
//        }
//
//    private val androidPath
//        get() = path.asAndroidPath()

    fun draw(scope: DrawScope) {
        when (drawType) {
            DrawType.Pen -> {
                    scope.drawPath(
                        color = color,
                        path = path,
                        style = Stroke(
                            width = strokeWidth,
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round
                        )
                    )
                }

            DrawType.Eraser -> {
                scope.drawPath(
                    color = Color.Transparent,
                    path = path,
                    style = Stroke(
                        width = strokeWidth,
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round
                    ),
                    blendMode = BlendMode.Clear
                )
            }

            DrawType.None -> {

            }

            DrawType.Circle ->{
                scope.drawCircle(color= color,center = start,radius = getRadius(start,end))
            }

            DrawType.Rectangle -> {
                    scope.drawRect(color= color,topLeft = start,size = Size(end.x-start.x, end.y-start.y))
            }
            DrawType.Line -> {
                scope.drawLine(color = color,start = start,end = end,strokeWidth = strokeWidth)
            }

            DrawType.OutLineRectangle -> {
                scope.drawRect(color= color,topLeft = start,size = Size(end.x-start.x, end.y-start.y),style = Stroke(
                    width = strokeWidth,
                    cap = StrokeCap.Square,
                    join = StrokeJoin.Miter
                ),)
            }
            DrawType.OutlineCircle -> {
                scope.drawCircle(color= color,center = start,radius = getRadius(start,end),style = Stroke(
                    width = strokeWidth,
                    cap = StrokeCap.Round,
                    join = StrokeJoin.Round
                ),)
            }
        }
    }
}