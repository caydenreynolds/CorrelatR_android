package com.example.activity

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.client.RequestGraphTask
import com.example.correlatr.R

class DisplayGraphActivity : ConnectedActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_graph)

        val taskResult = RequestGraphTask().execute(ip, port, "foo", "bar").get()
        displayStatus(
            findViewById(R.id.errorField),
            taskResult.statusMessage
        )

        if (taskResult != null && taskResult.graphImage != null)
        {
            val display_img = findViewById<ImageView>(R.id.display_img)
            val image_bitmap = BitmapFactory.decodeByteArray(taskResult.graphImage.toByteArray(), 0, taskResult.graphImage.size())
            display_img.setImageBitmap(image_bitmap)
        }

    }
}
