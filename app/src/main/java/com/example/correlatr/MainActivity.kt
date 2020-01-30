package com.example.correlatr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import com.example.client.PingServerTask

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun connectToServer(view: View)
    {
        val ipText = findViewById<EditText>(R.id.IPField).text.toString()
        val portText = findViewById<EditText>(R.id.portField).text.toString()
        val taskResult = PingServerTask().execute(ipText, portText).get()

        if (taskResult.error)
            displayStatus(findViewById(R.id.errorField), taskResult)
        else
        {
            val intent = Intent(this, TopLevelActivity::class.java).apply{
                putExtra(EXTRA_IP, ipText)
                putExtra(EXTRA_PORT, portText)
            }
            startActivity(intent)
        }
    }
}
