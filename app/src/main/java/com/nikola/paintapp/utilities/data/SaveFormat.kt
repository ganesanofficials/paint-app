package com.nikola.paintapp.utilities.data

import android.graphics.Bitmap
import android.graphics.Paint

enum class SaveFormat(val format: Bitmap.CompressFormat, val extension: String, var backgroundColor:Paint?) {
    PNG(Bitmap.CompressFormat.PNG, "png",null),
    JPEG(Bitmap.CompressFormat.JPEG, "jpg", Paint().apply { color = android.graphics.Color.WHITE })
}