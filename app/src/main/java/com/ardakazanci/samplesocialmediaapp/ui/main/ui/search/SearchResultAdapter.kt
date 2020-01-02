package com.ardakazanci.samplesocialmediaapp.ui.main.ui.search

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ardakazanci.samplesocialmediaapp.R
import com.ardakazanci.samplesocialmediaapp.data.model.DataModel
import com.ardakazanci.samplesocialmediaapp.databinding.ItemSearchResultBinding
import com.ardakazanci.samplesocialmediaapp.utils.getDecodeBase64toBitmap

class SearchResultAdapter(val clickListener: SearchResultClickListener) :
    PagedListAdapter<DataModel.AlgoliaUserResponseModel, SearchResultAdapter.SearchResultViewHolder>(
        SearchResultAdapterDiffCallback()
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchResultAdapter.SearchResultViewHolder {
        return SearchResultViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
        val user = getItem(position)
        if (user != null) holder.bind(user, clickListener)
    }


    class SearchResultViewHolder private constructor(val binding: ItemSearchResultBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            user: DataModel.AlgoliaUserResponseModel,
            clickListener: SearchResultClickListener
        ) {

            binding.user = user
            binding.clicklistener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): SearchResultViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemSearchResultBinding.inflate(layoutInflater, parent, false)
                return SearchResultViewHolder(binding)
            }
        }


    }


}

class SearchResultAdapterDiffCallback :
    DiffUtil.ItemCallback<DataModel.AlgoliaUserResponseModel>() {

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

class SearchResultClickListener(val clickListener: (userid: String) -> Unit) {

    fun onClick(userid: DataModel.AlgoliaUserResponseModel) {
        clickListener(userid._id)
    }

}