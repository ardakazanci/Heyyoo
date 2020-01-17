package com.ardakazanci.samplesocialmediaapp.ui.main.ui.messages.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ardakazanci.samplesocialmediaapp.R
import com.ardakazanci.samplesocialmediaapp.data.model.DataModel
import com.ardakazanci.samplesocialmediaapp.databinding.ItemMessageBubbleBinding
import com.ardakazanci.samplesocialmediaapp.utils.colorList

class MessageListAdapter() :
    ListAdapter<DataModel.Message, MessageListAdapter.ViewHolder>(MessageListDiffCallback()) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MessageListAdapter.ViewHolder {

        return ViewHolder.from(parent)

    }

    override fun onBindViewHolder(holder: MessageListAdapter.ViewHolder, position: Int) {
        holder.setIsRecyclable(false)
        val item = getItem(position)
        holder.bind(item)

    }


    class ViewHolder private constructor(val binding: ItemMessageBubbleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DataModel.Message) {

            when {
                item.messageSenderCode == 2 -> {
                    binding.messageBubbleContainer.setCardBackgroundColor(
                        binding.root.context.colorList(
                            R.color.color_sign_button
                        )
                    )
                    binding.messageText.setTextColor(Color.WHITE)
                }
            }


            binding.messageModel = item

            binding.executePendingBindings()

        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)

                val binding = ItemMessageBubbleBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }


    }


}

class MessageListDiffCallback : DiffUtil.ItemCallback<DataModel.Message>() {

    override fun areItemsTheSame(
        oldItem: DataModel.Message,
        newItem: DataModel.Message
    ): Boolean {
        return oldItem._id == newItem._id
    }


    override fun areContentsTheSame(
        oldItem: DataModel.Message,
        newItem: DataModel.Message
    ): Boolean {
        return oldItem == newItem
    }


}
