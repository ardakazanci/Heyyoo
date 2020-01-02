package com.ardakazanci.samplesocialmediaapp.ui.main.ui.search


import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView

import com.algolia.instantsearch.core.Callback
import com.algolia.instantsearch.core.searchbox.SearchBoxView

public class SearchBoxNoEmptyQuery(
    public val searchView: SearchView,
    val recyclerview: RecyclerView
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

                Log.e("Çalıştı", "Burada")
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                query?.isNotEmpty()?.let {

                    onQuerySubmitted?.invoke(query)
                    if (query.length >= 2) {
                        recyclerview.visibility = View.VISIBLE
                    } else if (query.isEmpty()) {
                        recyclerview.visibility = View.GONE
                    }

                }


                return false
            }
        })
    }


}