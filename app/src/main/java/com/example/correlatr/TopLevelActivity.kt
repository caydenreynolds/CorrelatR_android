package com.example.correlatr

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class TopLevelActivity : AppCompatActivity() {

    var ip = ""
    var port = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_level)

        ip = intent.getStringExtra(EXTRA_IP) as String
        port = intent.getStringExtra(EXTRA_PORT) as String
    }
}
