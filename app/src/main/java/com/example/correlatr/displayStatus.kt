package com.example.correlatr

import android.widget.TextView
import com.example.protos.Server

fun displayStatus(textView: TextView, statusMessage: Server.StatusMessage)
{
    if (statusMessage.error)
    {
        textView.apply{
            text = statusMessage.text
            setTextColor(resources.getColor(R.color.errorColor))
        }
    }
    else
    {
        textView.apply{
            text = statusMessage.text
            setTextColor(resources.getColor(R.color.textColor))
        }
    }

}