package com.example.activity

import android.os.Bundle
import android.preference.PreferenceManager.getDefaultSharedPreferences
import android.view.View
import android.widget.EditText
import com.example.client.PingServerTask
import com.example.correlatr.R

class ConnectionActivity : ConnectedActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connection)

        findViewById<EditText>(R.id.IPField).apply{
            hint = ip
        }
        findViewById<EditText>(R.id.portField).apply{
            hint = port
        }
    }

    fun connectToServer(view: View)
    {
        //Get the user input
        val ipText = findViewById<EditText>(R.id.IPField).text.toString()
        if (ipText != "")
            ip = ipText

        val portText = findViewById<EditText>(R.id.portField).text.toString()
        if (portText != "")
            port = portText


        //Validate the user input
        val taskResult = PingServerTask().execute(ip, port).get()
        displayStatus(findViewById(R.id.errorField), taskResult)

        //store connection information
        if (!taskResult.error) {
            val pref = getDefaultSharedPreferences(this)
            with (pref.edit()) {
                putString(getString(R.string.ip_key), ipText)
                putString(getString(R.string.port_key), portText)
                commit()
            }
        }
    }
}
