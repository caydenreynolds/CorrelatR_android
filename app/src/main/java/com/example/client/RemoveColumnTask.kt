package com.example.client

import android.os.AsyncTask
import com.example.protos.Client
import com.example.protos.Server

class RemoveColumnTask: AsyncTask<String, Unit, Server.StatusMessage>()
{
    //Sends a ChangeColumnMessage to the server
    //Args:
    //  params[0]: IP to connect to
    //  params[1]: Port number to connect to
    //  params[2]: Column to remove
    override fun doInBackground(vararg params: String): Server.StatusMessage?
    {
        val change = Client.ChangeColumn.newBuilder()
        change.oldColumnName = params[2]
        val clientMessage = Client.ClientMessage.newBuilder().setChangeColumn(change).build()
        return sendClientMessage(clientMessage, params[0], params[1]).statusMessage
    }
}