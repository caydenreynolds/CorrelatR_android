package com.example.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.correlatr.R

//TODO: Migrate project to androidx

class MainActivity : ConnectedActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun startTrackNewDataActivity(view: View)
    {
        val intent = Intent(this, TrackNewDataActivity::class.java).apply{}
        startActivity(intent)
    }

    fun startAddDatapointsActivity(view: View)
    {
        val intent = Intent(this, AddDatapointsActivity::class.java).apply{}
        startActivity(intent)
    }

    fun startDisplayGraphActivity(view: View)
    {
        val intent = Intent(this, DisplayGraphActivity::class.java).apply{}
        startActivity(intent)
    }

    fun connectionActivity(view: View)
    {
        val intent = Intent(this, ConnectionActivity::class.java).apply{}
        startActivity(intent)
    }
}
