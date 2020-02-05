package com.ardakazanci.anlikmotivasyon.ui.main.ui.search


import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView

import com.algolia.instantsearch.core.Callback
import com.algolia.instantsearch.core.searchbox.SearchBoxView

public class SearchBoxNoEmptyQuery(
    public val searchView: SearchView,
    val recyclerview: RecyclerView,
    val linearlayout: LinearLayout
) : SearchBoxView {

    override fun setText(text: String?, submitQuery: Boolean) {
        searchView.setQuery(text, false)
    }

    override var onQueryChanged: Callback<String?>? = null
    override var onQuerySubmitted: Callback<String?>? = null

    init {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.isNotEmpty()?.let {
                    onQuerySubmitted?.invoke(query)
                }


                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                query?.isNotEmpty()?.let {

                    onQuerySubmitted?.invoke(query)
                    if (query.length >= 2) {
                        linearlayout.visibility = View.INVISIBLE
                        recyclerview.visibility = View.VISIBLE
                    } else if (query.isEmpty()) {
                        linearlayout.visibility = View.VISIBLE
                        recyclerview.visibility = View.GONE
                    }

                }


                return false
            }
        })
    }


}