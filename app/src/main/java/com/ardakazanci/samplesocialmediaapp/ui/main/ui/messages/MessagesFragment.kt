package com.ardakazanci.samplesocialmediaapp.ui.main.ui.messages

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ardakazanci.samplesocialmediaapp.R
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button
import android.widget.Toast
import com.ardakazanci.samplesocialmediaapp.data.model.DataModel
import com.ardakazanci.samplesocialmediaapp.ui.main.ui.messages.adapter.MessageAdapter
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.DefaultItemAnimator
import com.github.nkzawa.emitter.Emitter
import kotlinx.android.synthetic.main.messages_fragment.view.*

import org.json.JSONObject










class MessagesFragment : Fragment() {


    private lateinit var myRecylerView: RecyclerView
    private lateinit var messageList: List<DataModel.MessageValue>
    private lateinit var chatBoxAdapter: MessageAdapter
    private lateinit var messagetxt: EditText
    private lateinit var send: Button
    private lateinit var socket: Socket
    var nickname: String? = null

    companion object {
        fun newInstance() = MessagesFragment()
    }


    private lateinit var viewModel: MessagesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.messages_fragment, container, false)

        messagetxt = view.findViewById(R.id.message)
        send = view.findViewById(R.id.send)

        messageList = ArrayList<DataModel.MessageValue>()
        nickname = "İlayda"

        try {
            socket = IO.socket("http://10.0.2.2:3001")
            socket.connect()
            socket.emit("join", nickname)
        } catch (e: Exception) {
            Log.e("Hata", e.printStackTrace().toString())
        }

        myRecylerView = view.findViewById(R.id.messagelist)
        val mLayoutManager = LinearLayoutManager(this.context)
        myRecylerView.layoutManager = mLayoutManager
        myRecylerView.itemAnimator = DefaultItemAnimator()

        send.setOnClickListener {
            if (messagetxt.text.toString().isNotEmpty()) {
                socket.emit("messagedetection", nickname, messagetxt.text.toString())
                messagetxt.setText(" ")
            }

        }

        socket.on("userjoinedthechat", object : Emitter.Listener {

            override fun call(vararg args: Any?) {
                requireActivity().runOnUiThread {
                    val data: String = args[0] as String
                    Toast.makeText(requireContext(), data, Toast.LENGTH_LONG).show()
                }
            }


        })

        socket.on("userdisconnect", object : Emitter.Listener {

            override fun call(vararg args: Any?) {
                requireActivity().runOnUiThread {
                    val data: String = args[0] as String
                    Toast.makeText(requireContext(), data, Toast.LENGTH_LONG).show()
                }
            }


        })


        socket.on("message", object : Emitter.Listener {

            override fun call(vararg args: Any?) {
                requireActivity().runOnUiThread {
                    val data = args[0] as JSONObject
                    try {
                        val nickname = data.getString("senderNickname")
                        val message = data.getString("message")
                        val messageObject = DataModel.MessageValue(nickname, message)
                        (messageList as ArrayList<DataModel.MessageValue>).add(messageObject);
                        chatBoxAdapter = MessageAdapter(messageList)
                        chatBoxAdapter.notifyDataSetChanged()
                        myRecylerView.adapter = chatBoxAdapter
                    } catch (e: Exception) {
                        Log.e("Hata", e.printStackTrace().toString())
                    }
                }
            }


        })






        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        socket.disconnect()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MessagesViewModel::class.java)
        // TODO: Use the ViewModel
    }


}
