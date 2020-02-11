package com.example.client

import android.os.AsyncTask
import com.example.protos.Client
import com.example.protos.Server
import java.lang.Exception
import java.net.InetAddress
import java.net.Socket
import java.nio.ByteBuffer

class PingServerTask : AsyncTask<String, Unit, Server.StatusMessage>()
{
    //Pings server to ensure we can connect.
    //Args:
    //  params[0]: IP to connect to
    //  params[1]: Port number to connect to
    override fun doInBackground(vararg params: String): Server.StatusMessage
    {
        val clientMessage = Client.ClientMessage.newBuilder().setPing(Client.Ping.newBuilder().build()).build()
        return sendClientMessage(clientMessage, params[0], params[1]).statusMessage
    }
}