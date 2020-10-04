package com.droid.fileagenttest

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.droid.lib.fileagent.FileAgent
import com.droid.lib.fileagent.Md5FileNameGenerator
import com.droid.lib.fileagent.StorageAgent
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val fileAgent: FileAgent by lazy {
        FileAgent(this, StorageAgent(this, Md5FileNameGenerator()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnPickImage).setOnClickListener(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK && requestCode == 100) {
            val uri = data?.data

            Log.wtf("xyz", "uri extension " + fileAgent.getExtensionFromUri(uri))

            val ext = fileAgent.getExtensionFromUri(uri)

            val file = fileAgent.copyFileToAppStorage(uri, String.format("IMG_%d.%s", System.currentTimeMillis(), ext ?: "hakau"))

            fileAgent.log(file)

            Picasso.get().load(file).into(findViewById<ImageView>(R.id.preview))
        }
    }

    override fun onClick(p0: View?) {
        val intent =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"

        startActivityForResult(intent, 100)
    }
}
