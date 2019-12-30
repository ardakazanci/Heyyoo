package com.ardakazanci.samplesocialmediaapp.ui.main.ui.profile.followedlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ardakazanci.samplesocialmediaapp.data.model.DataModel
import com.ardakazanci.samplesocialmediaapp.databinding.ItemListFollowedBinding

class FollowedListAdapter(val clickListener: FollowedListClickListener) :
    ListAdapter<DataModel.FollowedListModel, FollowedListAdapter.ViewHolder>(
        FollowedListDiffCallback()
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FollowedListAdapter.ViewHolder {

        return ViewHolder.from(parent)


    }

    override fun onBindViewHolder(holder: FollowedListAdapter.ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }


    class ViewHolder private constructor(val binding: ItemListFollowedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DataModel.FollowedListModel, clickListener: FollowedListClickListener) {
            binding.followed = item
            binding.clicklistener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemListFollowedBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }


}

class FollowedListDiffCallback : DiffUtil.ItemCallback<DataModel.FollowedListModel>() {

    override fun areItemsTheSame(
        oldItem: DataModel.FollowedListModel,
        newItem: DataModel.FollowedListModel
    ): Boolean {
        return oldItem._id == newItem._id
    }


    override fun areContentsTheSame(
        oldItem: DataModel.FollowedListModel,
        newItem: DataModel.FollowedListModel
    ): Boolean {
        return oldItem == newItem
    }


}


class FollowedListClickListener(val clickListener: (followedItemId: String) -> Unit) {

    fun onClick(followed: DataModel.FollowedListModel) = clickListener(followed._id)

}