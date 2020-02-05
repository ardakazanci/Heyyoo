package com.ardakazanci.anlikmotivasyon.ui.main.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ardakazanci.anlikmotivasyon.data.model.Doc
import com.ardakazanci.anlikmotivasyon.databinding.ItemListHomeBinding
import com.ardakazanci.anlikmotivasyon.ui.main.ui.home.util.DiffUtilCallBack

class ContentAdapter(val clickListener: HomeLikeClickListener) :
    PagedListAdapter<Doc, ContentAdapter.MyViewHolder>(DiffUtilCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val doc = getItem(position)
        if (doc != null) holder.bindPost(doc, clickListener)

    }

    class MyViewHolder private constructor(val binding: ItemListHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bindPost(content: Doc, clickListener: HomeLikeClickListener) {
            with(content) {

                binding.docModel = content
                binding.clickListener = clickListener
                binding.executePendingBindings()

            }
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemListHomeBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }


    }
}

class HomeLikeClickListener(val clickListener: (doc: Doc, value: Int) -> Unit) {
    fun onClick(doc: Doc, value: Int) {
        clickListener(doc, value) // İlgili Doc a ait id.
    }
}