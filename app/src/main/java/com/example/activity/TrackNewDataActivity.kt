package com.example.activity

import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.example.client.ChangeColumnTask
import com.example.correlatr.R

class TrackNewDataActivity : ConnectedActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_track_new_data)
    }

    fun sendNewDataToTrack(view: View)
    {
        val newColumn = findViewById<EditText>(R.id.newcolumnField).text.toString()
        val taskResult = ChangeColumnTask().execute(ip, port, "true",newColumn).get()

        displayStatus(
            findViewById(R.id.errorField),
            taskResult
        )
    }
}