package com.droid.lib.fileagent

import android.util.Log
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class Md5FileNameGenerator : FileNameGenerator {

    override fun generate(imageUri: String?): String? {
        val md5 = getMD5(imageUri?.toByteArray())
        val bi = BigInteger(md5).abs()
        return bi.toString(RADIX)
    }

    private fun getMD5(data: ByteArray?): ByteArray? {
        var hash: ByteArray? = null
        try {
            val digest =
                MessageDigest.getInstance(HASH_ALGORITHM)
            digest.update(data)
            hash = digest.digest()
        } catch (e: NoSuchAlgorithmException) {
            Log.e("_file_agent_", e.toString())
        }
        return hash
    }

    companion object {
        private const val HASH_ALGORITHM = "MD5"
        private const val RADIX = 10 + 26 // 10 digits + 26 letters
    }
}