package com.ardakazanci.anlikmotivasyon.ui.main.ui.profile.pager.shared.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ardakazanci.anlikmotivasyon.data.model.DataModel
import com.ardakazanci.anlikmotivasyon.databinding.ItemListSharedBinding

class ProfileSharedAdapter() :
    ListAdapter<DataModel.ContentDataModel, ProfileSharedAdapter.ViewHolder>(
        ProfileSharedDiffCallback()
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ViewHolder private constructor(val binding: ItemListSharedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DataModel.ContentDataModel) {
            binding.contentDataModel = item
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemListSharedBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }


}

class ProfileSharedDiffCallback : DiffUtil.ItemCallback<DataModel.ContentDataModel>() {

    override fun areItemsTheSame(
        oldItem: DataModel.ContentDataModel,
        newItem: DataModel.ContentDataModel
    ): Boolean {
        return oldItem._id == newItem._id
    }


    override fun areContentsTheSame(
        oldItem: DataModel.ContentDataModel,
        newItem: DataModel.ContentDataModel
    ): Boolean {
        return oldItem == newItem
    }


}