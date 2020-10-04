package com.droid.lib.fileagent

import android.content.Context
import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import java.io.File
import java.io.FileOutputStream
import java.text.DecimalFormat

class FileAgent(private val context: Context, private val storageAgent: StorageAgent) {

    private val TAG = FileAgent::class.simpleName

    fun getExtensionFromUri(fileUri: Uri?): String? {
        val typeMap = MimeTypeMap.getSingleton()

        fileUri?.let {
            return typeMap.getExtensionFromMimeType(context.contentResolver.getType(it))
        }

        return null
    }

    fun copyFileToAppStorage(fileUri: Uri?, fileName: String): File {
        val file = storageAgent.getPrivateFile(fileName)

        val resolver = context.contentResolver

        fileUri?.let {
            resolver.openInputStream(it)?.use { inputstream ->
                FileOutputStream(file).use { outputstream -> inputstream.copyTo(outputstream) }
            }
        }

        return file
    }

    fun getFileSize(file: File): String? {
        val size = file.length()
        if (size <= 0) return "0"
        val units = arrayOf("B", "KB", "MB", "GB", "TB")
        val digitGroups =
            (Math.log10(size.toDouble()) / Math.log10(1024.0)).toInt()
        return (DecimalFormat("#,##0.#")
            .format(size / Math.pow(1024.0, digitGroups.toDouble()))
                + " " + units[digitGroups])
    }

    fun log(file: File?) {
        if(file == null) {
            Log.wtf(TAG, "null file")
        } else {
            Log.wtf(TAG, String.format("===============================================\n" +
                    "file path: %s\n" +
                    "file name: %s\n", file.path, file.name)
                    + "file size " + getFileSize(file)
                    + "\nreadable " + file.canRead() + "\nwriteable " + file.canWrite()
                    + "\n===============================================")
        }
    }
}