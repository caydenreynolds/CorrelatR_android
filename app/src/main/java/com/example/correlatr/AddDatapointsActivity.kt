package com.example.correlatr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.EditText
import android.widget.TextView
import com.example.client.UpdateDataTask

class AddDatapointsActivity : AppCompatActivity() {

    var ip = ""
    var port = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_datapoints)

        ip = intent.getStringExtra(EXTRA_IP) as String
        port = intent.getStringExtra(EXTRA_PORT) as String
    }

    fun submitDataPoints(view: View)
    {
        val dateMs = findViewById<CalendarView>(R.id.dateSelect).date.toString()
        val columnName = findViewById<EditText>(R.id.dataName).text.toString()
        val columnValue = findViewById<EditText>(R.id.dataValue).text.toString()

        val taskResult = UpdateDataTask().execute(ip, port, dateMs, columnName, columnValue).get()
        displayStatus(findViewById(R.id.errorField), taskResult)
    }
}
