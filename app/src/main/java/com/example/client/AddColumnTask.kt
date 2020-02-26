package com.example.client

import android.os.AsyncTask
import com.example.protos.Client
import com.example.protos.Server

//Sends a ChangeColumnMessage to the server, which adds a new column with the name 'columnToAdd'
class AddColumnTask(val ip: String, val port: Int, val columnToAdd: String) :
      AsyncTask<Unit, Unit, Server.StatusMessage>()
{
    override fun doInBackground(vararg params: Unit): Server.StatusMessage?
    {
        val change = Client.ChangeColumn.newBuilder()
        change.newColumnName = columnToAdd
        val clientMessage = Client.ClientMessage.newBuilder().setChangeColumn(change).build()
        return sendClientMessage(clientMessage, ip, port).statusMessage
    }
}