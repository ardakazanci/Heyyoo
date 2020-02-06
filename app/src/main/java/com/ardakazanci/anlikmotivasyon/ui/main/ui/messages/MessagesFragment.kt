package com.ardakazanci.anlikmotivasyon.ui.main.ui.messages

import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.findNavController

import com.ardakazanci.anlikmotivasyon.R
import com.ardakazanci.anlikmotivasyon.databinding.MessagesFragmentBinding
import com.ardakazanci.anlikmotivasyon.ui.main.ui.messages.adapter.MessagesAdapter
import com.ardakazanci.anlikmotivasyon.ui.main.ui.messages.adapter.MessagesClickListener
import com.ardakazanci.anlikmotivasyon.utils.Constants
import com.securepreferences.SecurePreferences


class MessagesFragment : Fragment() {

    private lateinit var binding: MessagesFragmentBinding
    private lateinit var preferences: SharedPreferences
    private lateinit var viewModel: MessagesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.messages_fragment, container, false)

        binding.lifecycleOwner = requireActivity()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preferences = SecurePreferences(
            this.context,
            Constants.PREF_USER_TOKEN_VALUE,
            Constants.PREF_USER_TOKEN
        )

        val currentId: String? = "5e26bc3645cdeb0efb0629d2"


        viewModel = ViewModelProviders.of(this).get(MessagesViewModel::class.java)

        binding.viewmodel = viewModel




        binding.btnUserSelect.setOnClickListener { view: View ->
            view.findNavController()
                .navigate(R.id.action_navigation_messages_to_messageFollowedList)
        }


        viewModel.visibleControl.observe(this, Observer {

            if (it) {
                binding.rcConversationList.visibility = View.VISIBLE
                binding.csConversationUserSelected.visibility = View.GONE
            } else {
                binding.rcConversationList.visibility = View.GONE
                binding.csConversationUserSelected.visibility = View.VISIBLE
            }

        })


        val adapter = MessagesAdapter(MessagesClickListener {

            view.let { view: View ->

                if (currentId != null) {
                    view.findNavController()
                        .navigate(
                            MessagesFragmentDirections.actionNavigationMessagesToMessagesSendFragment(
                                currentId,
                                it
                            )
                        )

                    viewModel.coroutinesClear()
                } else {
                    Log.e("MessagesFragment", "CurrentID Değeri null geldi ")
                    viewModel.coroutinesClear()
                }

                viewModel.coroutinesClear()
            }
        })

        binding.rcConversationList.adapter = adapter

        viewModel.conversationList.observe(this, Observer {
            adapter.submitList(it)
        })


    }


    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.coroutinesClear()
    }

}
