package com.example.client

import android.os.AsyncTask
import com.example.protos.Client
import com.example.protos.Server
import java.lang.Exception
import java.net.InetAddress
import java.net.Socket
import java.nio.ByteBuffer

//Pings server to ensure we can connect.
class PingServerTask(val ip: String, val port: Int) :
      AsyncTask<Unit, Unit, Server.StatusMessage>()
{
    override fun doInBackground(vararg params: Unit): Server.StatusMessage
    {
        val clientMessage = Client.ClientMessage.newBuilder().setPing(Client.Ping.newBuilder().build()).build()
        return sendClientMessage(clientMessage, ip, port).statusMessage
    }
}