package com.ardakazanci.anlikmotivasyon.ui.main.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ardakazanci.anlikmotivasyon.R
import com.ardakazanci.anlikmotivasyon.databinding.FragmentHomeBinding
import com.ardakazanci.anlikmotivasyon.ui.main.ui.home.adapter.ContentAdapter
import com.ardakazanci.anlikmotivasyon.ui.main.ui.home.adapter.HomeLikeClickListener


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel


    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        val binding = DataBindingUtil.inflate<FragmentHomeBinding>(
            inflater,
            R.layout.fragment_home,
            container,
            false
        )

        binding.lifecycleOwner = this

        binding.viewmodel = homeViewModel

        val adapter = ContentAdapter(HomeLikeClickListener { hit, value ->

            if (value == 1) {

                homeViewModel.likeOperation(hit._id, hit.contentLikedCount)


            } else if (value == -1) {
                homeViewModel.dislikeOperation(hit._id)

            }


        })


        homeViewModel.getPosts().observe(this, Observer {
            adapter.submitList(it)
        })

        binding.rcHomeSharedList.adapter = adapter

        return binding.root


    }
}