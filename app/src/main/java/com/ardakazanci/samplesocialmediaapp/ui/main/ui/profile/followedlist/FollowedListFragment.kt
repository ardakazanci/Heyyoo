package com.ardakazanci.samplesocialmediaapp.ui.main.ui.profile.followedlist

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ardakazanci.samplesocialmediaapp.R

class FollowedListFragment : Fragment() {

    companion object {
        fun newInstance() = FollowedListFragment()
    }

    private lateinit var viewModel: FollowedListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.followed_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FollowedListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
