package com.droid.lib.fileagent

interface FileNameGenerator {
    /** Generates unique file name for image defined by URI  */
    fun generate(imageUri: String?): String?
}