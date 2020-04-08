package com.example.client

import com.example.protos.Client
import com.example.protos.Server
import com.example.protos.Shared
import com.example.recycler.AddDataPointsAdapter
import java.net.InetAddress
import java.net.Socket
import java.nio.ByteBuffer

//Sends the given ClientMessage to the specified ip and port.
//Returns: Whether the connection was successful
fun sendClientMessage(clientMessage: Client.ClientMessage, ip: String, port: Int): Server.ServerMessage
{
    try
    {
        Socket(InetAddress.getByName(ip), port).use{

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

            var offset = 0
            while (offset < messageLen)
                offset += inStream.read(serverMessageBytes, offset, messageLen - offset)

            return Server.ServerMessage.parseFrom(serverMessageBytes)
        }
    }
    catch (e: Throwable)
    {
        val statusMessage = Server.StatusMessage.newBuilder()
        statusMessage.error = true
        statusMessage.text = "Cannot connect"

        val serverMessage = Server.ServerMessage.newBuilder()
        serverMessage.setStatusMessage(statusMessage)

        return serverMessage.build()
    }
}

//Gets all of the column names from a list of DataPoints, and returns the list of column names
fun getColumnNamesFromDataPoints(dataPoints: MutableList<Shared.DataPoint>): MutableList<String>
{
    val columnNames = ArrayList<String>(dataPoints.size)

    for (point in dataPoints)
        columnNames.add(point.columnName)

    return columnNames
}