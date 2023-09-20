package com.nikola.paintapp.compose.tools

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Draw
import androidx.compose.material.icons.filled.LineAxis
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.PanTool
import androidx.compose.material.icons.filled.Rectangle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material.icons.outlined.Rectangle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nikola.paintapp.R
import com.nikola.paintapp.compose.components.ColorButton
import com.nikola.paintapp.compose.components.ToggleIconButton
import com.nikola.paintapp.utilities.data.DrawType
import com.nikola.paintapp.viewmodels.PaintViewModel


@Composable
fun BottomBar(
    viewModel: PaintViewModel = viewModel()
) {
    var showColorDialog by remember { mutableStateOf(false) }
    var showProperties by remember { mutableStateOf(true) }
    var drawType by remember { mutableStateOf(viewModel.currentPath.drawType) }
    val scrollState = rememberScrollState()
    //  var showBrushes by remember { mutableStateOf(drawType == DrawType.Pen) }
    fun switchDrawType(newDrawMode: DrawType) {
     //   showBrushes = (newDrawMode == DrawType.Pen)
        drawType = newDrawMode
        viewModel.currentPath.drawType = drawType
    }
    Surface(color = MaterialTheme.colorScheme.primaryContainer) {
        Column {
            AnimatedVisibility(visible = showProperties) {
                SizeConfigBar()
            }
          //  AnimatedVisibility() {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
//                    ToggleIconButton(
//                        onClick = { showProperties = !showProperties },
//                        isToggled = showProperties,
//                        imageVector = Icons.Default.Tune,
//                        contentDescription = R.string.change_brush_width
//                    )
                    IconButton(
                        onClick = { showColorDialog = true },
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Palette,
                            contentDescription = "Custom Color"
                        )
                    }
                    LazyRow(contentPadding = PaddingValues(vertical = 8.dp)) {
                        items(rainbowColors) {
                            ColorButton(color = it)
                        }
                    }

                }
            //}
            Row(
                modifier = Modifier.fillMaxWidth().horizontalScroll(scrollState),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ToggleIconButton(
                    onClick = {
                        switchDrawType(DrawType.None)
                    },
                    isToggled = (drawType == DrawType.None),
                    imageVector = Icons.Default.PanTool,
                    contentDescription = R.string.pan_mode
                )
                ToggleIconButton(
                    onClick = {
                        switchDrawType(DrawType.Pen)
                    },
                    isToggled = (drawType == DrawType.Pen),
                    imageVector = Icons.Default.Draw,
                    contentDescription = R.string.pan_mode
                )
                ToggleIconButton(
                    onClick = {
                        switchDrawType(DrawType.Eraser)
                    },
                    isToggled = (drawType == DrawType.Eraser),
                    painter = painterResource(id = R.drawable.ic_eraser_black_24dp),
                    contentDescription = R.string.pan_mode
                )

                ToggleIconButton(
                    onClick = {
                        switchDrawType(DrawType.OutlineCircle)
                    },
                    isToggled = (drawType == DrawType.OutlineCircle),
                    imageVector = Icons.Outlined.Circle,
                    contentDescription = R.string.pan_mode
                )

                ToggleIconButton(
                    onClick = {
                        switchDrawType(DrawType.Circle)
                    },
                    isToggled = (drawType == DrawType.Circle),
                    imageVector = Icons.Default.Circle,
                    contentDescription = R.string.pan_mode
                )
                ToggleIconButton(
                    onClick = {
                        switchDrawType(DrawType.OutLineRectangle)
                    },
                    isToggled = (drawType == DrawType.OutLineRectangle),
                    imageVector = Icons.Outlined.Rectangle,
                    contentDescription = R.string.pan_mode
                )

                ToggleIconButton(
                    onClick = {
                        switchDrawType(DrawType.Rectangle)
                    },
                    isToggled = (drawType == DrawType.Rectangle),
                    imageVector = Icons.Default.Rectangle,
                    contentDescription = R.string.pan_mode
                )

                ToggleIconButton(
                    onClick = {
                        switchDrawType(DrawType.Line)
                    },
                    isToggled = (drawType == DrawType.Line),
                    imageVector = Icons.Default.LineAxis,
                    contentDescription = R.string.pan_mode
                )
            }
        }
    }
    if (showColorDialog) {
        ColorPickerDialog(onDismiss = { showColorDialog = !showColorDialog })
    }
}

@Preview(showBackground = true)
@Composable
private fun MainToolbarPreview() {
    BottomBar()
}