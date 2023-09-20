package com.nikola.paintapp.compose.components

import android.graphics.Bitmap
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nikola.paintapp.R
import com.nikola.paintapp.utilities.data.SaveFormat
import com.nikola.paintapp.viewmodels.PaintViewModel


@Composable
fun FileSaveDialog(
    onDismissRequest: () -> Unit,
    paintViewModel: PaintViewModel = viewModel(),
    bitmap: ImageBitmap
) {
    var saveFormat by remember { mutableStateOf(SaveFormat.PNG) }
    val context = LocalContext.current
    Dialog(onDismissRequest, properties = DialogProperties(dismissOnClickOutside = false)) {
        Surface(
            modifier = Modifier.width(300.dp), shape = RoundedCornerShape(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .background(MaterialTheme.colorScheme.primaryContainer)
            ) {
                Text(stringResource(id = R.string.preview_of_image), modifier = Modifier.padding(8.dp))
                //Spacer(Modifier.size(10.dp))
                Image(
                    bitmap = bitmap,
                    contentDescription = stringResource(id = R.string.preview_of_image),
                )
                Text(
                    text = stringResource(R.string.select_export_format),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp)
                )
                ChipSelector(
                    selectedValue = saveFormat,
                    onSelectionChanged = { saveFormat = it })
                Spacer(modifier = Modifier.padding(vertical = 5.dp))

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 5.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(onClick = { onDismissRequest() }) {
                        Text(stringResource(id = R.string.cancel_button))
                    }
                    Spacer(modifier = Modifier.padding(horizontal = 5.dp))

                    Button(onClick = {
                        paintViewModel.saveBitmap(saveFormat, bitmap)
                        Toast.makeText(context,  R.string.file_save_successfully, Toast.LENGTH_LONG).show()
                        onDismissRequest()
                    }) {
                        Text(stringResource(id = R.string.save))
                    }
                }
            }
        }
    }
}