package com.ardakazanci.samplesocialmediaapp.ui.main.ui.profile.pager.shared

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil

import com.ardakazanci.samplesocialmediaapp.R
import com.ardakazanci.samplesocialmediaapp.databinding.ProfileSharedFragmentBinding

class ProfileSharedFragment : Fragment() {

    private lateinit var viewModel: ProfileSharedViewModel
    private lateinit var binding: ProfileSharedFragmentBinding


    companion object {
        fun newInstance() =
            ProfileSharedFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.profile_shared_fragment, container, false)


        viewModel = ViewModelProviders.of(this).get(ProfileSharedViewModel::class.java)

        binding.lifecycleOwner = this




        binding.viewmodel = viewModel




        return binding.root

    }


}
