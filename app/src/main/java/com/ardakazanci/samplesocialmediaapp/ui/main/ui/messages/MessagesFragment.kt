package com.ardakazanci.samplesocialmediaapp.ui.main.ui.messages

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController

import com.ardakazanci.samplesocialmediaapp.R
import com.ardakazanci.samplesocialmediaapp.databinding.MessagesFragmentBinding


class MessagesFragment : Fragment() {

    private lateinit var binding: MessagesFragmentBinding

    companion object {
        fun newInstance() = MessagesFragment()
    }


    private lateinit var viewModel: MessagesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.messages_fragment, container, false)


        binding.btnUserSelect.setOnClickListener { view: View ->
            view.findNavController()
                .navigate(R.id.action_navigation_messages_to_messageFollowedList)
        }


        binding.lifecycleOwner = this


        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MessagesViewModel::class.java)

    }


}
