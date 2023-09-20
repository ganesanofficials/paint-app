package com.nikola.paintapp.compose.components

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nikola.paintapp.R
import com.nikola.paintapp.viewmodels.PaintViewModel


@Composable
fun ClearDialog(
    onDismissRequest: () -> Unit,
    paintViewModel: PaintViewModel = viewModel()
) {
    Dialog(onDismissRequest, properties = DialogProperties(dismissOnClickOutside = false)) {
        Surface(
            modifier = Modifier.width(300.dp), shape = RoundedCornerShape(10.dp)
        ) {
            Column(modifier = Modifier.padding(10.dp)) {
                Text("Do you want to clear current drawing", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.padding(vertical = 5.dp))
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                    Button(onClick = { onDismissRequest() }) {
                        Text(stringResource(id = R.string.no))
                    }
                    Spacer(modifier = Modifier.padding(horizontal = 5.dp))
                    Button(onClick = {
                        paintViewModel.paths.clear()
                        onDismissRequest()
                    }) {
                        Text(stringResource(id = R.string.yes))
                    }
                }
            }
        }
    }
}