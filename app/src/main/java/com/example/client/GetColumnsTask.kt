package com.example.client

import android.os.AsyncTask
import com.example.protos.Client
import com.example.protos.Server

//Asks the server for the list of columns in the database
class GetColumnsTask(val ip: String, val port: Int) :
      AsyncTask<Unit, Unit, Server.ServerMessage>()
{
    override fun doInBackground(vararg params: Unit): Server.ServerMessage
    {
        val request = Client.ColumnsRequest.newBuilder()
        val clientMessage = Client.ClientMessage.newBuilder().setColumnsRequest(request).build()
        return sendClientMessage(clientMessage, ip, port)
    }
}