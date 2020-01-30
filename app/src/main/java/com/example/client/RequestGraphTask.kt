package com.example.client

import android.os.AsyncTask
import com.example.protos.Client
import com.example.protos.Server

class RequestGraphTask: AsyncTask<String, Unit, Server.ServerMessage>()
{
    //Sends a GraphRequest message to the server
    //Args:
    //  params[0]: IP to connect to
    //  params[1]: Port number to connect to
    //  params[2]: Vertical axis name
    //  params[3]: Horizontal axis name
    override fun doInBackground(vararg params: String): Server.ServerMessage
    {
        val request = Client.GraphRequest.newBuilder()
        request.vertical = params[2]
        request.horizontal = params[3]

        val clientMessage = Client.ClientMessage.newBuilder()
        clientMessage.setGraphRequest(request)

        return sendClientMessage(clientMessage.build(), params[0], params[1])

    }
}