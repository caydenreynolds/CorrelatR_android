package com.example.client

import android.os.AsyncTask
import com.example.protos.Client
import com.example.protos.Server

class UpdateDataTask: AsyncTask<String, Unit, Server.StatusMessage>() {

    //Sends an UpdateDataMessage to the server
    //Args:
    //  params[0]: IP to connect to
    //  params[1]: Port number to connect to
    //  params[2]: Date in ms to insert at
    //  params[3]: Column to update
    //  params[4]: Data to insert
    override fun doInBackground(vararg params: String): Server.StatusMessage?
    {
        val update = Client.UpdateData.newBuilder()
        update.date = params[2].toLong()

        if (params[3].isNotBlank() and params[4].isNotEmpty())
        {
            val newData = Client.NewData.newBuilder()
            newData.columnName = params[3]
            newData.value = params[4].toDouble()
            update.addNewData(newData)

        }

        val clientMessage = Client.ClientMessage.newBuilder()
        clientMessage.setUpdateData(update)

        return sendClientMessage(clientMessage.build(), params[0], params[1]).statusMessage
    }
}