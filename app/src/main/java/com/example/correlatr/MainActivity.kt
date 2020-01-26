package com.example.correlatr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.example.client.PingServerTask

const val EXTRA_IP = "com.example.CorrelatR.ip"
const val EXTRA_PORT = "com.example.CorrelatR.port"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("muh stuff", "we on")
        setContentView(R.layout.activity_main)
    }

    fun connectToServer(view: View)
    {
        val ipText = findViewById<EditText>(R.id.IPField).text.toString()
        val portText = findViewById<EditText>(R.id.portField).text.toString()
        val taskResult = PingServerTask().execute(ipText, portText).get()

        if (taskResult)
        {
            val intent = Intent(this, TopLevelActivity::class.java).apply{
                putExtra(EXTRA_IP, ipText)
                putExtra(EXTRA_PORT, portText)
            }
            startActivity(intent)
        }
        else
        {
            findViewById<TextView>(R.id.errorField).apply{
                text = resources.getString(R.string.connection_error)
            }
        }
    }
}
