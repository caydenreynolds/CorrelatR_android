package com.example.activity

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import com.bumptech.glide.Glide
import com.example.client.GetColumnsTask
import com.example.client.RequestGraphTask
import com.example.data.getColumnNamesFromDataPoints
import com.example.correlatr.R
import com.google.android.material.snackbar.Snackbar

class DisplayGraphActivity : ConnectedActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_graph)

        val horizontalSpinner = findViewById<Spinner>(R.id.horizontalSpinner)
        val columnsResult = GetColumnsTask(ip, port).execute().get()
        if (columnsResult.statusMessage.error)
            Snackbar.make(horizontalSpinner, columnsResult.statusMessage.text, Snackbar.LENGTH_SHORT).show()


        ArrayAdapter(this, android.R.layout.simple_spinner_item, getColumnNamesFromDataPoints(columnsResult.dataPointsList)).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            horizontalSpinner.adapter = adapter
        }

        val verticalSpinner = findViewById<Spinner>(R.id.verticalSpinner)
        ArrayAdapter(this, android.R.layout.simple_spinner_item, getColumnNamesFromDataPoints(columnsResult.dataPointsList)).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            verticalSpinner.adapter = adapter
        }
    }

    fun getGraph(view: View)
    {
        val horizontalAxis = findViewById<Spinner>(R.id.horizontalSpinner).selectedItem.toString()
        val verticalAxis = findViewById<Spinner>(R.id.verticalSpinner).selectedItem.toString()

        val taskResult = RequestGraphTask(ip, port, horizontalAxis, verticalAxis).execute().get()
        if (taskResult.statusMessage.error)
            Snackbar.make(findViewById(R.id.display_img), taskResult.statusMessage.text, Snackbar.LENGTH_SHORT).show()
        else
        {
            val displayImg = findViewById<ImageView>(R.id.display_img)
            Glide.with(this).load(taskResult.graphImage.toByteArray()).into(displayImg)
        }
    }
}
