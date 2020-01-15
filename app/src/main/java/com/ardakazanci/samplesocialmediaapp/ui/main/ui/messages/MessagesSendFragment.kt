package com.ardakazanci.samplesocialmediaapp.ui.main.ui.messages

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ardakazanci.samplesocialmediaapp.R

class MessagesSendFragment : Fragment() {

    companion object {
        fun newInstance() = MessagesSendFragment()
    }

    private lateinit var viewModel: MessagesSendViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.messages_send_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MessagesSendViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
