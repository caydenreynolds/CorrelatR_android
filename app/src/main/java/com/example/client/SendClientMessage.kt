package com.example.client

import com.example.protos.Client
import com.example.protos.Server
import java.net.InetAddress
import java.net.Socket
import java.nio.ByteBuffer

//Sends the given ClientMessage to the specified ip and port.
//Returns: Whether the connection was successful
fun sendClientMessage(clientMessage: Client.ClientMessage, ip: String, port: String): Server.ServerMessage
{
    try
    {
        Socket(InetAddress.getByName(ip), port.toInt()).use{

            //Send the message
            val clientMessageBytes = clientMessage.toByteArray()
            val length = ByteBuffer.allocate(4)
            length.putInt(clientMessageBytes.size)
            val outStream = it.getOutputStream()
            outStream.write(length.array())
            outStream.write(clientMessageBytes)

            //Get a response
            val inStream = it.getInputStream()
            val lenBytes = ByteArray(4)
            inStream.read(lenBytes)
            val messageLen = ByteBuffer.allocate(4).put(lenBytes).getInt(0)
            val serverMessageBytes = ByteArray(messageLen)
            inStream.read(serverMessageBytes)
            return Server.ServerMessage.parseFrom(serverMessageBytes)
        }
    }
    catch (e: Throwable)
    {
        //TODO: Instead of returning null, construct a servermessage with error=true.
        val statusMessage = Server.StatusMessage.newBuilder()
        statusMessage.error = true
        statusMessage.text = "Cannot connect"

        val serverMessage = Server.ServerMessage.newBuilder()
        serverMessage.setStatusMessage(statusMessage)

        return serverMessage.build()
    }
}