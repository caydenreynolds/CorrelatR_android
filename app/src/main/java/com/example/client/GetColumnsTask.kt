package com.example.client

import android.os.AsyncTask
import com.example.protos.Client
import com.example.protos.Server

class GetColumnsTask :  AsyncTask<String, Unit, Server.ServerMessage>()
{
    //Returns the columns defined in the database table
    //Args:
    //  params[0]: IP to connect to
    //  params[1]: Port number to connect to
    override fun doInBackground(vararg params: String): Server.ServerMessage
    {
        val request = Client.ColumnsRequest.newBuilder()
        val clientMessage = Client.ClientMessage.newBuilder().setColumnsRequest(request).build()
        return sendClientMessage(clientMessage, params[0], params[1])
    }
}