package com.ardakazanci.samplesocialmediaapp.ui.main.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.ardakazanci.samplesocialmediaapp.R
import com.ardakazanci.samplesocialmediaapp.data.model.DataModel

class SearchResultAdapter :
    PagedListAdapter<DataModel.AlgoliaUserResponseModel, SearchResultViewHolder>(SearchResultAdapter) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_search_result, parent, false)
        return SearchResultViewHolder(view)
    }


    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {

        val user = getItem(position)

        if (user != null) holder.bind(user)

    }

    companion object : DiffUtil.ItemCallback<DataModel.AlgoliaUserResponseModel>() {
        override fun areItemsTheSame(
            oldItem: DataModel.AlgoliaUserResponseModel,
            newItem: DataModel.AlgoliaUserResponseModel
        ): Boolean {
            return oldItem::class == newItem::class
        }

        override fun areContentsTheSame(
            oldItem: DataModel.AlgoliaUserResponseModel,
            newItem: DataModel.AlgoliaUserResponseModel
        ): Boolean {
            return oldItem.userFullName == newItem.userFullName
        }


    }


}