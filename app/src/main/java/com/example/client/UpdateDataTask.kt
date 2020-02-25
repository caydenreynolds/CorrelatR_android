package com.example.client

import android.os.AsyncTask
import com.example.data.DataPoint
import com.example.data.listToClientMessage
import com.example.protos.Client
import com.example.protos.Server

class UpdateDataTask(val ip: String, val port: Int, val date: Long, val updates: MutableList<DataPoint>) :
      AsyncTask<Unit, Unit, Server.StatusMessage>() {

    override fun doInBackground(vararg params: Unit): Server.StatusMessage?
    {
        val update = Client.UpdateData.newBuilder()
        update.date = date

        val protoUpdates = listToClientMessage(updates)
        update.addAllNewData(protoUpdates)

        val clientMessage = Client.ClientMessage.newBuilder()
        clientMessage.setUpdateData(update)

        return sendClientMessage(clientMessage.build(), ip, port).statusMessage
    }
}