package com.example.recycler

import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.correlatr.R
import com.example.fragment.GetTextFragment
import kotlinx.android.synthetic.main.recycler_tracked_data.view.column_name

class AddDatapointsAdapter(val columnNames: MutableList<DataUpdate>,
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
            if (columnNames[position].value == null)
                value = resources.getString(R.string.no_data)
            else
                value = columnNames[position].value.toString()

            text = columnNames[position].columnName + ": " + value
            setOnClickListener {
                if (value == resources.getString(R.string.no_data))
                    value = ""

                val fragment = GetTextFragment("Enter a value for " + columnNames[position].columnName,
                    value, InputType.TYPE_CLASS_NUMBER.or(InputType.TYPE_NUMBER_FLAG_DECIMAL))

                fragment.callbacks.add {
                    if (!it.cancelled)
                    {
                        columnNames[position] = DataUpdate(columnNames[position].columnName, it.text.toFloat())
                        notifyItemChanged(position)
                    }
                }

                fragment.show(fragmentManager, "Set data")
            }
        }
    }

    override fun getItemCount(): Int
    {
        return columnNames.size
    }

    fun getDataUpdates(): Array<DataUpdate>
    {
        val updates = Array(itemCount) {DataUpdate("", 0.0f)}
        for (i in 0 until itemCount)
        {

        }

        return updates
    }

    data class DataUpdate(val columnName: String, val value: Float?)
}