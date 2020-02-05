package com.ardakazanci.anlikmotivasyon.ui.main.ui.home.util

import androidx.recyclerview.widget.DiffUtil
import com.ardakazanci.anlikmotivasyon.data.model.Doc

class DiffUtilCallBack : DiffUtil.ItemCallback<Doc>() {
    override fun areItemsTheSame(oldItem: Doc, newItem: Doc): Boolean {
        return oldItem._id == newItem._id
    }

    override fun areContentsTheSame(oldItem: Doc, newItem: Doc): Boolean {
        return oldItem.contentText == newItem.contentText
                && oldItem.sharingUserLocation == newItem.sharingUserLocation

    }
}