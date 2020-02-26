package com.example.activity

import RequestDataTask
import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.client.UpdateDataTask
import com.example.correlatr.R
import com.example.data.serverResponseToList
import com.example.recycler.AddDataPointsAdapter
import com.google.android.material.snackbar.Snackbar
import java.lang.Exception
import java.util.*
import java.util.Calendar.*

class AddDataPointsActivity : ConnectedActivity() {

    private var selectedDate = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_datapoints)

        val calendar = findViewById<CalendarView>(R.id.dateSelect)
        calendar.setOnDateChangeListener { view, year, month, day -> setRecyclerView(view, year, month, day)}
        val todayCalendar = Calendar.getInstance()
        setRecyclerView(calendar, todayCalendar.get(YEAR), todayCalendar.get(MONTH),
            todayCalendar.get(DAY_OF_MONTH))
    }

    fun setRecyclerView(view: CalendarView, year: Int, month: Int, day: Int)
    {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day, 0, 0, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        selectedDate = calendar.time.time
        val response = RequestDataTask(ip, port, selectedDate).execute().get()
        if (response.statusMessage.error) {
            Snackbar.make(view, response.statusMessage.text, Snackbar.LENGTH_SHORT).show()
        }
        else
        {
            val columns = serverResponseToList(response.dataPointsList)
            if (response.dataPointsList.size != 4)
            {
                throw Exception(columns.size.toString())
            }
            findViewById<RecyclerView>(R.id.addDatapointsRecycler).apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = AddDataPointsAdapter(columns, supportFragmentManager)
            }
        }
    }

    fun submitDataPoints(view: View)
    {
        val recycler = findViewById<RecyclerView>(R.id.addDatapointsRecycler)

        val taskResult = UpdateDataTask(ip, port, selectedDate, (recycler.adapter as AddDataPointsAdapter).dataPoints).execute().get()
        Snackbar.make(view, taskResult.text, Snackbar.LENGTH_SHORT).show()
    }
}
