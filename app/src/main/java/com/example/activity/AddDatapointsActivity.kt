package com.example.activity

import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.EditText
import com.example.client.UpdateDataTask
import com.example.correlatr.R
import com.google.android.material.snackbar.Snackbar

//TODO: In the future, we'll want a listfragment to display this activity

class AddDatapointsActivity : ConnectedActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_datapoints)
    }

    fun submitDataPoints(view: View)
    {
        val dateMs = findViewById<CalendarView>(R.id.dateSelect).date.toString()
        val columnName = findViewById<EditText>(R.id.dataName).text.toString()
        val columnValue = findViewById<EditText>(R.id.dataValue).text.toString()

        val taskResult = UpdateDataTask().execute(ip, port, dateMs, columnName, columnValue).get()
        Snackbar.make(view, taskResult.text, Snackbar.LENGTH_SHORT).show()
    }
}
