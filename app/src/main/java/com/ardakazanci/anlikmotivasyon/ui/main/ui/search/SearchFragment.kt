package com.ardakazanci.anlikmotivasyon.ui.main.ui.search

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.algolia.instantsearch.core.connection.ConnectionHandler
import com.algolia.instantsearch.helper.android.list.autoScrollToStart
import com.algolia.instantsearch.helper.android.searchbox.connectView

import com.ardakazanci.anlikmotivasyon.R
import com.ardakazanci.anlikmotivasyon.databinding.SearchFragmentBinding

class SearchFragment : Fragment() {


    private val connection = ConnectionHandler()
    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<SearchFragmentBinding>(
            inflater,
            R.layout.search_fragment,
            container,
            false
        )

        viewModel = ViewModelProviders.of(requireActivity())[SearchViewModel::class.java]

        binding.searchViewModel = viewModel

        val adapter = SearchResultAdapter(SearchResultClickListener { hit ->


            view!!.let { view: View ->

                view.findNavController().navigate(
                    SearchFragmentDirections.actionNavigationSearchToOtherProfileFragment(hit)
                )

            }

        })


        viewModel.users.observe(
            requireActivity(),
            Observer { hits ->
                adapter.submitList(hits)
            })
        binding.userList.let {
            it.visibility = View.GONE
            it.itemAnimator = null
            it.adapter = adapter
            it.autoScrollToStart(adapter)
        }


        connection += viewModel.searchBox.connectView(
            SearchBoxNoEmptyQuery(
                binding.searchView,
                binding.userList,
                binding.lySearchEmptyMessage
            )
        )

        binding.lifecycleOwner = this

        return binding.root

    }


    override fun onDestroyView() {
        super.onDestroyView()
        connection.disconnect()
    }

}
