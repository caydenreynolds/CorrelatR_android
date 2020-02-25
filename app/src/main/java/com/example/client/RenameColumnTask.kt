package com.example.client

import android.os.AsyncTask
import com.example.protos.Client
import com.example.protos.Server

//Sends a ChangeColumnMessage to the server, to rename a column named 'columnToRename' to 'newColumnName'
class RenameColumnTask(val ip: String, val port: Int, val columnToRename: String, val newColumnName: String) :
      AsyncTask<Unit, Unit, Server.StatusMessage>()
{
    override fun doInBackground(vararg params: Unit): Server.StatusMessage?
    {
        val change = Client.ChangeColumn.newBuilder()
        change.oldColumnName = columnToRename
        change.newColumnName = newColumnName
        val clientMessage = Client.ClientMessage.newBuilder().setChangeColumn(change).build()
        return sendClientMessage(clientMessage, ip, port).statusMessage
    }
}