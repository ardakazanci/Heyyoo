package com.ardakazanci.samplesocialmediaapp.ui.main.ui.profile.followerlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ardakazanci.samplesocialmediaapp.data.model.DataModel
import com.ardakazanci.samplesocialmediaapp.databinding.ItemListFollowerBinding

class FollowerListAdapter(val clickListener: FollowerListClickListener) :
    ListAdapter<DataModel.FollowerListModel, FollowerListAdapter.ViewHolder>(
        FollowerListDiffCallback()
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FollowerListAdapter.ViewHolder {

        return ViewHolder.from(parent)


    }

    override fun onBindViewHolder(holder: FollowerListAdapter.ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }


    class ViewHolder private constructor(val binding: ItemListFollowerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DataModel.FollowerListModel, clickListener: FollowerListClickListener) {
            binding.follower = item
            binding.clicklistener = clickListener
            binding.executePendingBindings()

        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemListFollowerBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }


}

class FollowerListDiffCallback : DiffUtil.ItemCallback<DataModel.FollowerListModel>() {

    override fun areItemsTheSame(
        oldItem: DataModel.FollowerListModel,
        newItem: DataModel.FollowerListModel
    ): Boolean {
        return oldItem._id == newItem._id
    }


    override fun areContentsTheSame(
        oldItem: DataModel.FollowerListModel,
        newItem: DataModel.FollowerListModel
    ): Boolean {
        return oldItem == newItem
    }


}


class FollowerListClickListener(val clickListener: (followerItemId: String) -> Unit) {

    fun onClick(follower: DataModel.FollowerListModel) = clickListener(follower._id)

}