package com.example.activity

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import com.example.client.RequestGraphTask
import com.example.correlatr.R
import com.google.android.material.snackbar.Snackbar

class DisplayGraphActivity : ConnectedActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_graph)

        val taskResult = RequestGraphTask().execute(ip, port, "foo", "bar").get()
        Snackbar.make(findViewById(R.id.display_img), taskResult.statusMessage.text, Snackbar.LENGTH_SHORT).show()

        if (taskResult != null && taskResult.graphImage != null)
        {
            val displayImg = findViewById<ImageView>(R.id.display_img)
            val imageBitmap = BitmapFactory.decodeByteArray(taskResult.graphImage.toByteArray(), 0, taskResult.graphImage.size())
            displayImg.setImageBitmap(imageBitmap)
        }

    }
}
