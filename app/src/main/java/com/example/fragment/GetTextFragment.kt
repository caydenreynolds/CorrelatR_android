package com.example.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.correlatr.R

class GetTextFragment(val prompt: String, val inputDefault: String, val inType: Int): DialogFragment()
{
    val callbacks = ArrayList<(TextResult)->Unit>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
    {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_get_text, container, false)
        view.findViewById<TextView>(R.id.confirmText).text = prompt
        view.findViewById<TextView>(R.id.textInput).apply {
            text = inputDefault
            inputType = inType
        }

            //Handle the button inputs
            callbacks.add { dismiss() }
            view.findViewById<Button>(R.id.cancelButton).setOnClickListener {
                for (callback in callbacks)
                    callback(TextResult("", true))
            }

            view.findViewById<Button>(R.id.okButton).setOnClickListener {
                val text = view.findViewById<TextView>(R.id.textInput).text.toString()
                for (callback in callbacks)
                    callback(TextResult(text, false))
            }
            return view
    }

    data class TextResult(val text: String, val cancelled: Boolean)
}