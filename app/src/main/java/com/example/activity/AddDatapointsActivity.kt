package com.example.activity

import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.client.GetColumnsTask
import com.example.correlatr.R
import com.example.recycler.AddDatapointsAdapter
import com.google.android.material.snackbar.Snackbar

class AddDatapointsActivity : ConnectedActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_datapoints)

        val response = GetColumnsTask(ip, port).execute().get()
        if (response.statusMessage.error)
        {
            Snackbar.make(findViewById<RecyclerView>(R.id.addDatapointsRecycler), response.statusMessage.text, Snackbar.LENGTH_SHORT).show()
        }

        val columns = serverResponseToArrayList(response.columnNamesList, response.columnValuesList)
        findViewById<RecyclerView>(R.id.addDatapointsRecycler).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = AddDatapointsAdapter(columns, supportFragmentManager)
        }
    }

    fun serverResponseToArrayList(names: List<String>, values: List<Float>): ArrayList<AddDatapointsAdapter.DataUpdate>
    {
        val updates = ArrayList<AddDatapointsAdapter.DataUpdate>()
        for (i in 0 until names.size)
            updates.add(AddDatapointsAdapter.DataUpdate(names[i], values[i]))

        return updates
    }

    fun submitDataPoints(view: View)
    {
//        val dateMs = findViewById<CalendarView>(R.id.dateSelect).date.toString()
//        val columnName = findViewById<EditText>(R.id.dataName).text.toString()
//        val columnValue = findViewById<EditText>(R.id.dataValue).text.toString()
//
//        val taskResult = UpdateDataTask().execute(ip, port, dateMs, columnName, columnValue).get()
//        Snackbar.make(view, taskResult.text, Snackbar.LENGTH_SHORT).show()
    }
}
