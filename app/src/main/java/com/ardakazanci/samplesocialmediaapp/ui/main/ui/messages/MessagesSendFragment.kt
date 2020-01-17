package com.ardakazanci.samplesocialmediaapp.ui.main.ui.messages

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer


import com.ardakazanci.samplesocialmediaapp.data.model.DataModel
import com.ardakazanci.samplesocialmediaapp.databinding.MessagesSendFragmentBinding
import com.ardakazanci.samplesocialmediaapp.ui.main.ui.messages.adapter.MessageListAdapter
import com.ardakazanci.samplesocialmediaapp.utils.toast
import android.content.Context.INPUT_METHOD_SERVICE


import android.content.Context.INPUT_METHOD_SERVICE
import androidx.core.content.ContextCompat.getSystemService


import android.app.Activity
import android.content.Context
import android.os.Handler
import android.view.inputmethod.InputMethodManager
import androidx.core.view.size
import com.ardakazanci.samplesocialmediaapp.R


class MessagesSendFragment : Fragment() {

    private lateinit var viewModel: MessagesSendViewModel
    private lateinit var binding: MessagesSendFragmentBinding

    // Message List


    companion object {
        fun newInstance() = MessagesSendFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.messages_send_fragment, container, false)
        viewModel = ViewModelProviders.of(this).get(MessagesSendViewModel::class.java)
        binding.lifecycleOwner = this
        arguments!!.let {
            val args = MessagesSendFragmentArgs.fromBundle(it)
            viewModel.otherUserId.postValue(args.otherUserİd)
            viewModel.currentUserId.postValue(args.currentUserİd)
            viewModel.getMessageList(args.currentUserİd, args.otherUserİd)
        }

        val adapter = MessageListAdapter()
        binding.rcMessagesSendList.adapter = adapter

        viewModel.messageListInfo.observe(this, Observer { result ->

            if (result.isNotEmpty()) {

                adapter.submitList(result)

            }

            binding.rcMessagesSendList.scrollToPosition(binding.rcMessagesSendList.adapter!!.itemCount - 1);


        })


        binding.btnMessageSend.setOnClickListener {
            viewModel.messageSendFunc()
            dismissKeyboard(requireActivity())
            binding.etMessageBox.text.clear()
            fragmentManager?.beginTransaction()?.detach(this)?.attach(this)?.commit();
        }





        binding.viewModel = viewModel

        return binding.root


    }


    fun dismissKeyboard(activity: Activity) {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (null != activity.currentFocus) imm.hideSoftInputFromWindow(
            activity.currentFocus!!.applicationWindowToken, 0
        )
    }




}
