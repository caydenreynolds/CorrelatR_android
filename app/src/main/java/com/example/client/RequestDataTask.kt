import android.os.AsyncTask
import com.example.client.sendClientMessage
import com.example.protos.Client
import com.example.protos.Server

//Asks the server for the list of columns in the database
class RequestDataTask(val ip: String, val port: Int, val date: Long) :
    AsyncTask<Unit, Unit, Server.ServerMessage>()
{
    override fun doInBackground(vararg params: Unit): Server.ServerMessage
    {
        val request = Client.DataRequest.newBuilder()
        request.date = date
        val clientMessage = Client.ClientMessage.newBuilder().setDataRequest(request).build()
        return sendClientMessage(clientMessage, ip, port)
    }
}