package com.ardakazanci.samplesocialmediaapp.ui.main.ui.profile.followedlist

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.findNavController

import com.ardakazanci.samplesocialmediaapp.R
import com.ardakazanci.samplesocialmediaapp.databinding.FollowedListFragmentBinding
import com.ardakazanci.samplesocialmediaapp.ui.main.ui.profile.followedlist.adapter.FollowedListAdapter
import com.ardakazanci.samplesocialmediaapp.ui.main.ui.profile.followedlist.adapter.FollowedListClickListener

class FollowedListFragment : Fragment() {


    private lateinit var viewModel: FollowedListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FollowedListFragmentBinding>(
            inflater,
            R.layout.followed_list_fragment,
            container,
            false
        )


        viewModel = ViewModelProviders.of(this).get(FollowedListViewModel::class.java)

        binding.followedViewModel = viewModel

        val adapter = FollowedListAdapter(FollowedListClickListener { followedItemId ->
            //Toast.makeText(context, "${followedItemId}", Toast.LENGTH_LONG).show()

            view!!.let { view: View ->
                view.findNavController()
                    .navigate(
                        FollowedListFragmentDirections.actionFollowedListFragmentToOtherProfileFragment(
                            followedItemId
                        )
                    )
            }


        })

        binding.rcFollowedList.adapter = adapter

        viewModel.followedListInfo.observe(this, Observer {

            adapter.submitList(it)

        })

        binding.lifecycleOwner = this

        return binding.root
    }


}
