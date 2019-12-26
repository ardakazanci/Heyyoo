package com.ardakazanci.samplesocialmediaapp.ui.main.ui.profile.pager.shared

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.ardakazanci.samplesocialmediaapp.R

class ProfileSharedFragment : Fragment() {

    companion object {
        fun newInstance() =
            ProfileSharedFragment()
    }

    private lateinit var viewModel: ProfileSharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.profile_shared_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ProfileSharedViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
