package com.example.recycler

import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.correlatr.R
import com.example.data.DataPoint
import com.example.fragment.GetTextFragment
import kotlinx.android.synthetic.main.recycler_tracked_data.view.column_name

class AddDataPointsAdapter(val dataPoints: MutableList<DataPoint>,
                           private val fragmentManager: FragmentManager
                          ) : RecyclerView.Adapter<RecyclerHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHolder
    {
        return RecyclerHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_add_datapoints, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: RecyclerHolder, position: Int)
    {
        holder.view.column_name.apply {
            var value = ""
            if (dataPoints[position].value == null)
                value = resources.getString(R.string.no_data)
            else
                value = dataPoints[position].value.toString()

            text = dataPoints[position].columnName + ": " + value
            setOnClickListener {
                if (value == resources.getString(R.string.no_data))
                    value = ""

                val fragment = GetTextFragment("Enter a value for " + dataPoints[position].columnName,
                    value, InputType.TYPE_CLASS_NUMBER.or(InputType.TYPE_NUMBER_FLAG_DECIMAL))

                fragment.callbacks.add {
                    if (!it.cancelled)
                    {
                        try { dataPoints[position] = DataPoint(dataPoints[position].columnName, it.text.toDouble()) }
                        catch (_: Throwable) { dataPoints[position] =  DataPoint(dataPoints[position].columnName, null) }
                        notifyItemChanged(position)
                    }
                }

                fragment.show(fragmentManager, "Set data")
            }
        }
    }

    override fun getItemCount(): Int
    {
        return dataPoints.size
    }
}