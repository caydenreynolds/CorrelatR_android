package com.example.activity

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.client.AddColumnTask
import com.example.client.GetColumnsTask
import com.example.correlatr.R
import com.example.recycler.TrackNewDataAdapter
import com.google.android.material.snackbar.Snackbar

class TrackNewDataActivity : ConnectedActivity() {

    lateinit var columns: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_track_new_data)

        val response = GetColumnsTask(ip, port).execute().get()
        if (response.statusMessage.error)
        {
            Snackbar.make(findViewById<RecyclerView>(R.id.trackedDataRecycler), response.statusMessage.text, Snackbar.LENGTH_SHORT).show()
        }

        columns = protobufListToArrayList(response.columnNamesList)
        findViewById<RecyclerView>(R.id.trackedDataRecycler).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = TrackNewDataAdapter(columns, supportFragmentManager)
        }
    }

    fun sendNewDataToTrack(view: View)
    {
        //Tell the server
        val newColumn = findViewById<EditText>(R.id.newcolumnField).text.toString()
        val taskResult = AddColumnTask(ip, port, newColumn).execute().get()
        Snackbar.make(view, taskResult.text, Snackbar.LENGTH_SHORT).show()

        //Update the view
        if (!taskResult.error)
        {
            columns.add(newColumn)
            findViewById<RecyclerView>(R.id.trackedDataRecycler).adapter?.notifyItemInserted(columns.size-1)
        }
    }

    //We need to convert the immutable protobuf list to a mutable array list
    //Yes, the protobuf list *says* it's mutable. But that's a lie.
    fun protobufListToArrayList(protoList: List<String>): ArrayList<String>
    {
        val result = ArrayList<String>()
        for (str in protoList)
        {
            result.add(str)
        }
        return result
    }
}
