package com.example.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.correlatr.R

class ConfirmFragment(val prompt: String): DialogFragment()
{
    val callbacks = ArrayList<(Boolean)->Unit>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_confirm, container, false)
        view.findViewById<TextView>(R.id.confirmText).text = prompt

        //Handle the button inputs
        callbacks.add { dismiss() }
        view.findViewById<Button>(R.id.noButton).setOnClickListener {
            for (callback in callbacks)
                callback(false)
        }
        view.findViewById<Button>(R.id.yesButton).setOnClickListener {
            for (callback in callbacks)
                callback(true)
        }
        return view
    }
}