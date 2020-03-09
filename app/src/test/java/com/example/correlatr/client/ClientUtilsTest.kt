package com.example.correlatr.client

import com.example.client.sendClientMessage
import com.example.protos.Client
import com.example.protos.Server
import org.junit.Test

import org.junit.Assert.*
import java.net.ServerSocket
import java.nio.ByteBuffer

class ClientUtilsTest
{
    @Test
    fun sendClientMessageTest()
    {
        val clientMessage = Client.ClientMessage.newBuilder()
        clientMessage.ping = Client.Ping.newBuilder().build()
        val serverMessage = Server.ServerMessage.newBuilder()
        val statusMessage = Server.StatusMessage.newBuilder()
        statusMessage.text = "foo"
        statusMessage.error = false
        serverMessage.statusMessage = statusMessage.build()

        val serverThread = ClientUtilsSocketThread(serverMessage.build(), clientMessage.build())
        serverThread.start()

        val result = sendClientMessage(clientMessage.build(), "localhost", 1337)
        assertEquals(serverMessage.statusMessage.text, result.statusMessage.text)
        assertEquals(serverMessage.statusMessage.error, result.statusMessage.error)
    }

    @Test
    fun sendClientMessageFailsTest()
    {
        val clientMessage = Client.ClientMessage.newBuilder()
        clientMessage.ping = Client.Ping.newBuilder().build()

        val result = sendClientMessage(clientMessage.build(), "localhost", 1337)
        assertEquals(result.statusMessage.text, "Cannot connect")
        assertEquals(result.statusMessage.error, true)
    }
}

class ClientUtilsSocketThread(val messageToSend: Server.ServerMessage,
                              val messageToReceive: Client.ClientMessage
                             ) : Thread()
{
    override fun run()
    {
        val socket = ServerSocket(1337)
        socket.accept().use {
            //Ensure We get sent the expected message
            val inStream = it.getInputStream()
            val lenBytes = ByteArray(4)
            inStream.read(lenBytes)
            val messageLen = ByteBuffer.allocate(4).put(lenBytes).getInt(0)
            assertEquals(messageLen, messageToReceive.toByteArray().size)
            val clientMessageBytes = ByteArray(messageLen)
            inStream.read(clientMessageBytes)
            val clientMessage = Client.ClientMessage.parseFrom(clientMessageBytes)
            assertEquals(clientMessage, messageToReceive)

            //Send a server response
            val serverMessageBytes = messageToSend.toByteArray()
            val length = ByteBuffer.allocate(4)
            length.putInt(serverMessageBytes.size)
            val outStream = it.getOutputStream()
            outStream.write(length.array())
            outStream.write(serverMessageBytes)
        }
        socket.close()
    }
}