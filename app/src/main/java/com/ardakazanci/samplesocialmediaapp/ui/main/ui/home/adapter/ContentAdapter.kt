package com.ardakazanci.samplesocialmediaapp.ui.main.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ardakazanci.samplesocialmediaapp.R
import com.ardakazanci.samplesocialmediaapp.data.model.Doc
import com.ardakazanci.samplesocialmediaapp.ui.main.ui.home.util.DiffUtilCallBack
import com.ardakazanci.samplesocialmediaapp.utils.unixToDate
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.item_list_home.view.*

class ContentAdapter :
    PagedListAdapter<Doc, ContentAdapter.MyViewHolder>(DiffUtilCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_home, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bindPost(it)
        }

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val contentTextValue: AppCompatTextView = itemView.item_content_text
        private val contentLocationValue: Chip = itemView.item_content_location
        private val contentLikeCountValue: AppCompatTextView = itemView.item_content_like_count
        private val contentDislikeCountValue: AppCompatTextView =
            itemView.item_content_dislike_count
        private val contentDateValue: AppCompatTextView = itemView.item_content_date


        fun bindPost(content: Doc) {
            with(content) {
                contentTextValue.text = contentText
                contentLocationValue.text = sharingUserLocation
                contentLikeCountValue.text = contentLikedCount.toString()
                contentDislikeCountValue.text = contentNotLikeCount.toString()
                contentDateValue.text = unixToDate(sharingDate)

            }
        }
    }
}