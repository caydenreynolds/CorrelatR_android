package com.example.correlatr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class TopLevelActivity : AppCompatActivity() {

    var ip = ""
    var port = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_level)

        ip = intent.getStringExtra(EXTRA_IP) as String
        port = intent.getStringExtra(EXTRA_PORT) as String
    }

    fun startTrackNewDataActivity(view: View)
    {
        val intent = Intent(this, TrackNewDataActivity::class.java).apply{
            putExtra(EXTRA_IP, ip)
            putExtra(EXTRA_PORT, port)
        }
        startActivity(intent)
    }

    fun startAddDatapointsActivity(view: View)
    {
        val intent = Intent(this, AddDatapointsActivity::class.java).apply{
            putExtra(EXTRA_IP, ip)
            putExtra(EXTRA_PORT, port)
        }
        startActivity(intent)
    }

    fun startDisplayGraphActivity(view: View)
    {
        val intent = Intent(this, DisplayGraphActivity::class.java).apply{
            putExtra(EXTRA_IP, ip)
            putExtra(EXTRA_PORT, port)
        }
        startActivity(intent)
    }
}
