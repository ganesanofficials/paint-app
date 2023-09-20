package com.nikola.paintapp.compose

import android.content.res.Resources.Theme
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PanTool
import androidx.compose.material.icons.filled.PanToolAlt
import androidx.compose.material.icons.filled.Redo
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Undo
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nikola.paintapp.R
import com.nikola.paintapp.compose.components.ClearDialog
import com.nikola.paintapp.compose.components.FileSaveDialog
import com.nikola.paintapp.compose.tools.BottomBar
import com.nikola.paintapp.viewmodels.PaintViewModel
import dev.shreyaspatil.capturable.Capturable
import dev.shreyaspatil.capturable.controller.rememberCaptureController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaintingApp(viewModel: PaintViewModel = viewModel()) {
    var showSaveDialog by remember { mutableStateOf(false) }
    var showClearDialog by remember {
        mutableStateOf(false)
    }
    val captureController = rememberCaptureController()
    var ticketBitmap: ImageBitmap? by remember { mutableStateOf(null) }
    Scaffold(topBar = {
        Row(modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.primaryContainer),Arrangement.SpaceEvenly) {
            IconButton(onClick = {
                viewModel.mainToolBarVisibility = !viewModel.mainToolBarVisibility
            }) {
                Icon(Icons.Filled.Brush, contentDescription = null)

            }
            IconButton(onClick = { showClearDialog = !showClearDialog }) {
                Icon(Icons.Filled.Delete, contentDescription = null)
            }
            IconButton(onClick = {
                if (viewModel.paths.isNotEmpty()) {
                    viewModel.pathsUndone.add(viewModel.paths.last())
                    viewModel.paths.removeLast()
                }
            }) {
                Icon(Icons.Filled.Undo, contentDescription = null)
            }
            IconButton(onClick = {
                if (viewModel.pathsUndone.isNotEmpty()) {
                    viewModel.paths.add(viewModel.pathsUndone.last())
                    viewModel.pathsUndone.removeLast()
                }
            }) {
                Icon(Icons.Filled.Redo, contentDescription = null)
            }
            IconButton(onClick = {
                showSaveDialog = true
                captureController.capture()
            }) {
                Icon(Icons.Filled.Save, contentDescription = null)
            }
        }

    }, bottomBar = {
        if(viewModel.mainToolBarVisibility)
            BottomBar()
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Capturable(controller = captureController , onCaptured = { bitmap, error -> ticketBitmap = bitmap }){
                SketchPad()
            }

        }
    }


    if (showSaveDialog) {
        ticketBitmap?.let { FileSaveDialog( bitmap = it,onDismissRequest = { showSaveDialog = false }) }
    }
    if (showClearDialog) {
        ClearDialog(onDismissRequest = { showClearDialog = false })
    }
}