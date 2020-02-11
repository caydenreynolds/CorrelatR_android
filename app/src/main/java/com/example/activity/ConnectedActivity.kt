package com.example.activity

import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import com.example.correlatr.R

abstract class ConnectedActivity : AppCompatActivity() {
    var ip = ""
    var port = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setIP()
    }

    fun setIP() {
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        ip = pref.getString(getString(R.string.ip_key), "") ?: ""
        port = pref.getString(getString(R.string.port_key), "") ?: ""
    }
}