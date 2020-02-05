package com.ardakazanci.anlikmotivasyon.ui.main.ui.messages.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ardakazanci.anlikmotivasyon.data.model.DataModel
import com.ardakazanci.anlikmotivasyon.databinding.ItemListConversationsUserBinding

class MessagesAdapter(val clickListener: MessagesClickListener) :
    ListAdapter<DataModel.MessageConversationUserModel, MessagesAdapter.ViewHolder>(
        MessagesListDiffCallback()
    ) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MessagesAdapter.ViewHolder {


        return ViewHolder.from(parent)


    }

    override fun onBindViewHolder(holder: MessagesAdapter.ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }


    class ViewHolder private constructor(val binding: ItemListConversationsUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: DataModel.MessageConversationUserModel,
            clickListener: MessagesClickListener
        ) {
            binding.conversationUserInfo = item
            binding.clicklistener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    ItemListConversationsUserBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }

    }


}

class MessagesListDiffCallback : DiffUtil.ItemCallback<DataModel.MessageConversationUserModel>() {

    override fun areItemsTheSame(
        oldItem: DataModel.MessageConversationUserModel,
        newItem: DataModel.MessageConversationUserModel
    ): Boolean {
        return oldItem._id == newItem._id
    }


    override fun areContentsTheSame(
        oldItem: DataModel.MessageConversationUserModel,
        newItem: DataModel.MessageConversationUserModel
    ): Boolean {
        return oldItem == newItem
    }


}


class MessagesClickListener(val clickListener: (messageUserId: String) -> Unit) {

    fun onClick(messageUserId: DataModel.MessageConversationUserModel) =
        clickListener(messageUserId._id)

}