package com.ardakazanci.samplesocialmediaapp.ui.main.ui.profile.followerlist

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.ardakazanci.samplesocialmediaapp.R
import com.ardakazanci.samplesocialmediaapp.databinding.FollowerListFragmentBinding

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

        binding.lifecycleOwner = this

        viewModel = ViewModelProviders.of(this).get(FollowerListViewModel::class.java)

        binding.followerListViewModel = viewModel

        return binding.root
    }


}
