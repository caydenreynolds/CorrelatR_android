package com.example.correlatr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.example.client.ChangeColumnTask

class TrackNewDataActivity : AppCompatActivity() {

    var ip = ""
    var port = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_track_new_data)


        ip = intent.getStringExtra(EXTRA_IP) as String
        port = intent.getStringExtra(EXTRA_PORT) as String
    }

    fun sendNewDataToTrack(view: View)
    {
        val newColumn = findViewById<EditText>(R.id.newcolumnField).text.toString()
        val taskResult = ChangeColumnTask().execute(ip, port, "true",newColumn).get()

        displayStatus(findViewById(R.id.errorField), taskResult)
    }
}
