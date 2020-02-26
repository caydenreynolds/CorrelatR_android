package com.example.activity

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import com.example.client.GetColumnsTask
import com.example.client.RequestGraphTask
import com.example.client.getColumnNamesFromDataPoints
import com.example.correlatr.R
import com.google.android.material.snackbar.Snackbar

class DisplayGraphActivity : ConnectedActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_graph)

        val horizontalSpinner: Spinner = findViewById(R.id.horizontalSpinner)
        val columnsResult = GetColumnsTask(ip, port).execute().get()
        if (columnsResult.statusMessage.error)
            Snackbar.make(horizontalSpinner, columnsResult.statusMessage.text, Snackbar.LENGTH_SHORT).show()


        ArrayAdapter(this, android.R.layout.simple_spinner_item, getColumnNamesFromDataPoints(columnsResult.dataPointsList)).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            horizontalSpinner.adapter = adapter
        }

        val taskResult = RequestGraphTask(ip, port, "foo", "bar").execute().get()
        Snackbar.make(findViewById(R.id.display_img), taskResult.statusMessage.text, Snackbar.LENGTH_SHORT).show()

        if (taskResult != null && taskResult.graphImage != null)
        {
            val displayImg = findViewById<ImageView>(R.id.display_img)
            val imageBitmap = BitmapFactory.decodeByteArray(taskResult.graphImage.toByteArray(), 0, taskResult.graphImage.size())
            displayImg.setImageBitmap(imageBitmap)
        }

    }
}
