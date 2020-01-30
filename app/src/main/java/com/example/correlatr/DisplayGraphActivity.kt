package com.example.correlatr

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.client.RequestGraphTask

class DisplayGraphActivity : AppCompatActivity() {

    var ip = ""
    var port = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_graph)

        ip = intent.getStringExtra(EXTRA_IP) as String
        port = intent.getStringExtra(EXTRA_PORT) as String

        val taskResult = RequestGraphTask().execute(ip, port, "foo", "bar").get()
        displayStatus(findViewById(R.id.errorField), taskResult.statusMessage)

        if (taskResult != null && taskResult.graphImage != null)
        {
            val display_img = findViewById<ImageView>(R.id.display_img)
            val image_bitmap = BitmapFactory.decodeByteArray(taskResult.graphImage.toByteArray(), 0, taskResult.graphImage.size())
            display_img.setImageBitmap(image_bitmap)
        }

    }
}
