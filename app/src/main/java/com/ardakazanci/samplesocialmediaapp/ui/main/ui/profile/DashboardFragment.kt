package com.ardakazanci.samplesocialmediaapp.ui.main.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.ardakazanci.samplesocialmediaapp.R
import com.ardakazanci.samplesocialmediaapp.databinding.FragmentDashboardBinding
import com.ardakazanci.samplesocialmediaapp.ui.main.ui.profile.pager.adapter.ViewPagerProfileAdapter
import com.ardakazanci.samplesocialmediaapp.ui.main.ui.profile.pager.liked.ProfileLikedFragment
import com.ardakazanci.samplesocialmediaapp.ui.main.ui.profile.pager.shared.ProfileSharedFragment
import com.google.android.material.tabs.TabLayout


class DashboardFragment : Fragment() {

    private lateinit var viewModel: DashboardViewModel
    private lateinit var tabLayoutProfile: TabLayout
    private lateinit var viewPagerProfile: ViewPager
    private lateinit var pagerAdapter: ViewPagerProfileAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val binding = DataBindingUtil.inflate<FragmentDashboardBinding>(
            inflater,
            R.layout.fragment_dashboard,
            container,
            false
        )
        binding.lifecycleOwner = this

        viewModel = ViewModelProviders.of(this).get(DashboardViewModel::class.java)

        //binding.textView.text = dashboardViewModel.userFullName.value.
        tabLayoutProfile = binding.tabLayoutProfile
        viewPagerProfile = binding.viewPagerProfile
        fragmentAttachToTabLayout()

        binding.dashboardViewModel = viewModel



        return binding.root
    }

    private fun fragmentAttachToTabLayout() {

        fragmentManager!!.let {
            pagerAdapter = ViewPagerProfileAdapter(fragmentManager!!)
        }

        pagerAdapter.addFragment(ProfileSharedFragment(), "Paylaşımlarım")
        pagerAdapter.addFragment(ProfileLikedFragment(), "Beğenilerim")
        viewPagerProfile.adapter = pagerAdapter
        tabLayoutProfile.setupWithViewPager(viewPagerProfile)

    }

}