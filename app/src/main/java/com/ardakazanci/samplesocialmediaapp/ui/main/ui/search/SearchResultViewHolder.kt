package com.ardakazanci.samplesocialmediaapp.ui.main.ui.search

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ardakazanci.samplesocialmediaapp.data.model.DataModel
import com.ardakazanci.samplesocialmediaapp.utils.getDecodeBase64toBitmap
import kotlinx.android.synthetic.main.item_search_result.view.*

class SearchResultViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    fun bind(user: DataModel.AlgoliaUserResponseModel) {
        view.itemUserFullName.text = user.userFullName
        view.itemUserImage.setImageBitmap(getDecodeBase64toBitmap(user.userImageBase64))
    }

}