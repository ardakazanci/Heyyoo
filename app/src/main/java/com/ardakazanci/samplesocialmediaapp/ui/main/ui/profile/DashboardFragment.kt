package com.ardakazanci.samplesocialmediaapp.ui.main.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
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

        tabLayoutProfile = binding.tabLayoutProfile
        viewPagerProfile = binding.viewPagerProfile
        fragmentAttachToTabLayout()

        binding.dashboardViewModel = viewModel


        binding.tvFollowedList.setOnClickListener { view: View ->

            view.findNavController()
                .navigate(R.id.action_navigation_dashboard_to_followedListFragment)
            viewModel.cancelCoroutines()
        }

        binding.tvFollowerList.setOnClickListener { view: View ->
            Log.e("DashboardFragment", "Follower List Fragment a geçiş sağlandı")
            view.findNavController()
                .navigate(R.id.action_navigation_dashboard_to_followerListFragment)
            viewModel.cancelCoroutines()
        }


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