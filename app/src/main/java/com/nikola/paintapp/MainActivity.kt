package com.nikola.paintapp

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.window.OnBackInvokedDispatcher
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nikola.paintapp.compose.PaintingApp
import com.nikola.paintapp.ui.theme.PaintAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PaintAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PaintingApp()
                   // ScratchPad()
                }
            }
        }
    }

}

//@OptIn(ExperimentalComposeUiApi::class)
//@Composable
//fun simpleDraw(){
//    val path = remember {mutableStateOf(mutableListOf<PathState>())}
//
//    val movePath = remember{ mutableStateOf<Offset?>(null) }
//    val drawColor = remember{ mutableStateOf(Color.White)}
//    val drawBrush = remember{ mutableStateOf(5f)}
//    path.value.add(PathState(Path(),drawColor.value,drawBrush.value))
//    Log.d("Path1",path.toString())
//    val currentPath = path.value.last().path
//    Canvas(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(top = 100.dp)
//            .pointerInteropFilter {
//                when(it.action){
//                    MotionEvent.ACTION_DOWN ->{
//                      //  currentPath.moveTo(it.x,it.y)
//                       // usedColor.value.add(drawColor.value)
//                    }
//                    MotionEvent.ACTION_MOVE ->{
//                        movePath.value = Offset(it.x,it.y)
//                    }
//                    else ->{
//                        movePath.value =null
//                    }
//                }
//                true
//            }
//    ){
//
//        movePath.value?.let {
//            currentPath.lineTo(it.x,it.y)
////            drawPath(
////                path = currentPath,
////                color = drawColor.value,
////                style = Stroke(drawBrush.value)
////            )
//        }
//        Log.d("Path",path.value.size.toString())
//        path.value.forEach {
//            drawPath(
//                path = it.path,
//                color = it.color,
//                style  = Stroke(it.stroke)
//            )
//        }
//    }
//}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PaintAppTheme {
        Greeting("Android")
    }
}