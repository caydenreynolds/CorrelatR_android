package com.example.client

import android.os.AsyncTask
import com.example.protos.Client
import com.example.protos.Server

//Sends a ChangeColumnMessage to the server, to remove the column with the name 'columnToRemove'
class RemoveColumnTask(val ip: String, val port: Int, val columnToRemove: String) :
      AsyncTask<Unit, Unit, Server.StatusMessage>()
{
    override fun doInBackground(vararg params: Unit): Server.StatusMessage?
    {
        val change = Client.ChangeColumn.newBuilder()
        change.oldColumnName = columnToRemove
        val clientMessage = Client.ClientMessage.newBuilder().setChangeColumn(change).build()
        return sendClientMessage(clientMessage, ip, port).statusMessage
    }
}