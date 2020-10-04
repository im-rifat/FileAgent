package com.droid.lib.fileagent

import android.content.Context
import java.io.File

class StorageAgent(private val context: Context, private val nameGenerator: FileNameGenerator) {

    private fun getPrivateAppDir(): File? {
        val dir = context.getExternalFilesDir("." + nameGenerator.generate(context.applicationContext.packageName.replace(".", "_")))

        val exists = dir?.exists() ?: false

        if(!exists) {
            dir?.mkdirs()
        }

        return dir
    }

    internal fun getPrivateFile(fileName: String): File {
        val file = File(getPrivateAppDir(), fileName)

        return file
    }
}