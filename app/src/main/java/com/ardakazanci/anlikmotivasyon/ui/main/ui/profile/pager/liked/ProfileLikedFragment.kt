package com.ardakazanci.anlikmotivasyon.ui.main.ui.profile.pager.liked

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ardakazanci.anlikmotivasyon.R

class ProfileLikedFragment : Fragment() {

    companion object {
        fun newInstance() =
            ProfileLikedFragment()
    }

    private lateinit var viewModel: ProfileLikedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.profile_liked_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ProfileLikedViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
