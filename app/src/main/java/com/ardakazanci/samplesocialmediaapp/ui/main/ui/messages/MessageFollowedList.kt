package com.ardakazanci.samplesocialmediaapp.ui.main.ui.messages

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.findNavController

import com.ardakazanci.samplesocialmediaapp.R
import com.ardakazanci.samplesocialmediaapp.databinding.MessageFollowedListFragmentBinding
import com.ardakazanci.samplesocialmediaapp.ui.main.ui.profile.followedlist.FollowedListFragmentDirections
import com.ardakazanci.samplesocialmediaapp.ui.main.ui.profile.followedlist.adapter.FollowedListAdapter
import com.ardakazanci.samplesocialmediaapp.ui.main.ui.profile.followedlist.adapter.FollowedListClickListener
import com.ardakazanci.samplesocialmediaapp.utils.toast

class MessageFollowedList : Fragment() {

    private lateinit var binding: MessageFollowedListFragmentBinding
    private lateinit var viewModel: MessageFollowedListViewModel

    companion object {
        fun newInstance() = MessageFollowedList()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.message_followed_list_fragment,
            container,
            false
        )

        viewModel = ViewModelProviders.of(this).get(MessageFollowedListViewModel::class.java)


        binding.viewModel = viewModel


        val adapter = FollowedListAdapter(FollowedListClickListener { followedItemId ->

            // requireActivity().applicationContext.toast("İlgili ID Değeri : " + followedItemId)

            view?.let { view: View ->
                viewModel.currentUserId.value!!.let { currentUserId ->
                    view.findNavController()
                        .navigate(
                            MessageFollowedListDirections.actionMessageFollowedListToMessagesSendFragment(
                                followedItemId,
                                currentUserId
                            )
                        )
                    viewModel.cancelCoroutines()
                }
            }

        })

        binding.messagesUserList.adapter = adapter

        viewModel.followedListInfo.observe(this, Observer {

            adapter.submitList(it)

        })

        viewModel.followedListIsEmpty.observe(this, Observer {

            if (it) {
                binding.messagesUserList.visibility = View.INVISIBLE
                binding.lyFollowedlistEmptyMessage.visibility = View.VISIBLE
            } else {
                binding.messagesUserList.visibility = View.VISIBLE
                binding.lyFollowedlistEmptyMessage.visibility = View.INVISIBLE
            }

        })



        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.cancelCoroutines()
    }


}
