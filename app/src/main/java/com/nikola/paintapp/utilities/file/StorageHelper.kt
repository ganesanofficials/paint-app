package com.nikola.paintapp.utilities.file

import android.annotation.SuppressLint
import android.os.Environment
import com.nikola.paintapp.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.Calendar

object StorageHelper {
    @SuppressLint("SimpleDateFormat")
    private val dateTimeFormat = SimpleDateFormat("yyyy-MM-dd_HH-mm-ss")

    fun getOutputFile(extension:String): File {
        val currentTimeMillis = Calendar.getInstance().time
        val currentDateTime = dateTimeFormat.format(currentTimeMillis)
        val currentDate = currentDateTime.split("_").first()
        val currentTime = currentDateTime.split("_").last()

        val fileName = "${R.string.app_name}_${currentDate}_${currentTime}"
        return File(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            "$fileName.$extension"
        )
    }

}