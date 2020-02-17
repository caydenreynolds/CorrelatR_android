package com.example.activity

import android.os.Bundle
import android.preference.PreferenceManager.getDefaultSharedPreferences
import android.view.View
import android.widget.EditText
import com.example.client.PingServerTask
import com.example.correlatr.R
import com.google.android.material.snackbar.Snackbar

class ConnectionActivity : ConnectedActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connection)

        findViewById<EditText>(R.id.IPField).apply{
            hint = ip
        }
        findViewById<EditText>(R.id.portField).apply{
            hint = port.toString()
        }
    }

    fun connectToServer(view: View)
    {
        //Get the user input
        val ipText = findViewById<EditText>(R.id.IPField).text.toString()
        val newIp = if(ipText == "") ip else ipText
        val portText = findViewById<EditText>(R.id.portField).text.toString()
        val newPort = if(portText == "") port else portText.toInt()

        //Validate the user input
        val taskResult = PingServerTask(newIp, newPort).execute().get()
        Snackbar.make(view, taskResult.text, Snackbar.LENGTH_SHORT).show()

        //store connection information
        if (!taskResult.error) {
            val pref = getDefaultSharedPreferences(this)
            with (pref.edit()) {
                putString(getString(R.string.ip_key), newIp)
                putInt(getString(R.string.port_key), newPort)
                commit()
            }
        }
    }
}
