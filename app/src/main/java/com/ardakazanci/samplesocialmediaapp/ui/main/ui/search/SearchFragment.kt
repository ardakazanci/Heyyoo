package com.ardakazanci.samplesocialmediaapp.ui.main.ui.search

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import com.algolia.instantsearch.core.connection.ConnectionHandler
import androidx.recyclerview.widget.LinearLayoutManager
import com.algolia.instantsearch.core.Callback
import com.algolia.instantsearch.helper.android.item.StatsTextView
import com.algolia.instantsearch.helper.android.list.autoScrollToStart
import com.algolia.instantsearch.helper.android.searchbox.SearchBoxViewAppCompat
import com.algolia.instantsearch.helper.android.searchbox.connectView
import com.algolia.instantsearch.helper.stats.StatsPresenterImpl
import com.algolia.instantsearch.helper.stats.connectView

import com.ardakazanci.samplesocialmediaapp.R
import kotlinx.android.synthetic.main.search_fragment.*

class SearchFragment : Fragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private lateinit var viewModel: SearchViewModel
    private val connection = ConnectionHandler()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModel = ViewModelProviders.of(requireActivity())[SearchViewModel::class.java]

        viewModel.users.observe(
            requireActivity(),
            Observer { hits -> viewModel.adapterProduct.submitList(hits) })
        userList.let {
            it.visibility = View.GONE
            it.itemAnimator = null
            it.adapter = viewModel.adapterProduct
            it.layoutManager = LinearLayoutManager(requireContext())
            it.autoScrollToStart(viewModel.adapterProduct)
        }




        connection += viewModel.searchBox.connectView(SearchBoxNoEmptyQuery(searchView, userList))


    }

    override fun onDestroyView() {
        super.onDestroyView()
        connection.disconnect()
    }

}
