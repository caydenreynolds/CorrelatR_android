package com.example.client

import android.os.AsyncTask
import com.example.protos.Client
import com.example.protos.Server

//Sends a GraphRequest message to the server, to get a a graph with horizontal axis 'horizAxis'
//and vertical axis 'vertAxis'
class RequestGraphTask(val ip: String, val port: Int, val horizAxis: String, val vertAxis: String) :
      AsyncTask<Unit, Unit, Server.ServerMessage>()
{
    override fun doInBackground(vararg params: Unit): Server.ServerMessage
    {
        val request = Client.GraphRequest.newBuilder()
        request.vertical = vertAxis
        request.horizontal = horizAxis

        val clientMessage = Client.ClientMessage.newBuilder()
        clientMessage.setGraphRequest(request)

        return sendClientMessage(clientMessage.build(), ip, port)
    }
}