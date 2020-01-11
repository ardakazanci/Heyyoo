package com.ardakazanci.samplesocialmediaapp.ui.main.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ardakazanci.samplesocialmediaapp.R
import com.ardakazanci.samplesocialmediaapp.data.model.Doc
import com.ardakazanci.samplesocialmediaapp.databinding.ItemListHomeBinding
import com.ardakazanci.samplesocialmediaapp.ui.main.ui.home.util.DiffUtilCallBack
import com.ardakazanci.samplesocialmediaapp.utils.unixToDate
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.item_list_home.view.*

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

class HomeLikeClickListener(val clickListener: (doc: String) -> Unit) {
    fun onClick(doc: Doc) {
        clickListener(doc._id) // İlgili Doc a ait id.
    }
}