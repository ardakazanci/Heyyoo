package com.ardakazanci.samplesocialmediaapp.ui.main.ui.profile.followerlist

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
import com.ardakazanci.samplesocialmediaapp.databinding.FollowerListFragmentBinding
import com.ardakazanci.samplesocialmediaapp.ui.main.ui.profile.followerlist.adapter.FollowerListAdapter
import com.ardakazanci.samplesocialmediaapp.ui.main.ui.profile.followerlist.adapter.FollowerListClickListener
import kotlinx.android.synthetic.main.followed_list_fragment.*

class FollowerListFragment : Fragment() {


    private lateinit var viewModel: FollowerListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FollowerListFragmentBinding>(
            inflater,
            R.layout.follower_list_fragment,
            container,
            false
        )

        viewModel = ViewModelProviders.of(this).get(FollowerListViewModel::class.java)

        binding.followerListViewModel = viewModel

        val adapter = FollowerListAdapter(FollowerListClickListener { followerItemId ->

            view!!.let { view: View ->
                view.findNavController()
                    .navigate(
                        FollowerListFragmentDirections.actionFollowerListFragmentToOtherProfileFragment(
                            followerItemId
                        )
                    )

                viewModel.cancelCoroutines()
            }

        })






        binding.rcFollowerList.adapter = adapter

        viewModel.followerListInfo.observe(this, Observer {

            adapter.submitList(it)

        })


        viewModel.followerListIsEmpty.observe(this, Observer {

            if (it) {
                binding.lyFollowerlistEmptyMessage.visibility = View.VISIBLE
                binding.rcFollowerList.visibility = View.INVISIBLE
            } else {
                binding.lyFollowerlistEmptyMessage.visibility = View.INVISIBLE
                binding.rcFollowerList.visibility = View.VISIBLE
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
