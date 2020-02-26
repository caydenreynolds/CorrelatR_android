package com.example.recycler

import android.preference.PreferenceManager
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.client.RemoveColumnTask
import com.example.client.RenameColumnTask
import com.example.correlatr.R
import com.example.fragment.ConfirmFragment
import com.example.fragment.GetTextFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.recycler_tracked_data.view.*

class TrackNewDataAdapter(private val data: MutableList<String>,
                          private val fragmentManager: FragmentManager
                         ) : RecyclerView.Adapter<RecyclerHolder>()
{
    var ip = ""
    var port = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerHolder
    {
        val pref = PreferenceManager.getDefaultSharedPreferences(parent.context)
        ip = pref.getString(parent.context.getString(R.string.ip_key), "") ?: ""
        port = pref.getInt(parent.context.getString(R.string.port_key), -1)

        return RecyclerHolder(LayoutInflater.from(parent.context)
               .inflate(R.layout.recycler_tracked_data, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: RecyclerHolder, position: Int)
    {
        holder.view.column_name.text = data[position]

        holder.view.delete_button.setOnClickListener {
            val fragment = ConfirmFragment("Are you sure you want to delete " +
                data[position] + "? This cannot be undone")
            fragment.callbacks.add {
                if (it)
                {
                    val response = RemoveColumnTask(ip, port, data[position]).execute().get()
                    Snackbar.make(holder.view, response.text, Snackbar.LENGTH_SHORT).show()
                    if (!response.error)
                    {
                        data.removeAt(position)
                        notifyItemRemoved(position)
                    }
                }
            }
            fragment.show(fragmentManager, "Confirm Delete")
        }

        holder.view.edit_button.setOnClickListener {
            val fragment = GetTextFragment("Enter a new name", data[position], InputType.TYPE_TEXT_VARIATION_SHORT_MESSAGE)
            fragment.callbacks.add {
                if (it.text != "")
                {
                    val response = RenameColumnTask(ip, port, data[position], it.text).execute().get()
                    Snackbar.make(holder.view, response.text, Snackbar.LENGTH_SHORT).show()
                    if (!response.error)
                    {
                        data[position] = it.text
                        notifyItemChanged(position)
                    }
                }
            }
            fragment.show(fragmentManager, "Rename Column")
        }
    }

    override fun getItemCount(): Int
    {
        return data.size
    }
}