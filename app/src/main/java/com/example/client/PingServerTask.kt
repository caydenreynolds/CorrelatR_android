package com.example.client

import android.os.AsyncTask
import com.example.protos.Client
import java.lang.Exception
import java.net.InetAddress
import java.net.Socket
import java.nio.ByteBuffer

class PingServerTask : AsyncTask<String, Unit, Boolean>()
{
    override fun doInBackground(vararg params: String): Boolean {
        try
        {
            Socket(InetAddress.getByName(params[0]), params[1].toInt()).use {
                val clientMessage = Client.ClientMessage.newBuilder().setPing(Client.Ping.newBuilder().build()).build()
                val messageBytes = clientMessage.toByteArray()
                val length = ByteBuffer.allocate(4)
                length.putInt(messageBytes.size)
                val stream = it.getOutputStream()
                stream.write(length.array())
                stream.write(messageBytes)
            }
            return true
        }
        catch (e: Exception)
        {
            return false
        }
    }
}