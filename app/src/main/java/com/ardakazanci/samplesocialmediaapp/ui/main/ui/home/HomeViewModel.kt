package com.ardakazanci.samplesocialmediaapp.ui.main.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.ardakazanci.samplesocialmediaapp.data.model.DataModel
import com.ardakazanci.samplesocialmediaapp.data.model.Doc
import com.ardakazanci.samplesocialmediaapp.ui.main.ui.home.datasource.ContentDataSource
import kotlinx.coroutines.Dispatchers

class HomeViewModel : ViewModel() {

    var postsLiveData: LiveData<PagedList<Doc>>

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(5)
            .setEnablePlaceholders(false)
            .build()
        postsLiveData = initializedPagedListBuilder(config).build()
    }

    fun getPosts(): LiveData<PagedList<Doc>> = postsLiveData

    private fun initializedPagedListBuilder(config: PagedList.Config):
            LivePagedListBuilder<Int, Doc> {

        val dataSourceFactory = object : DataSource.Factory<Int, Doc>() {
            override fun create(): DataSource<Int, Doc> {
                return ContentDataSource(Dispatchers.Default)
            }
        }
        return LivePagedListBuilder<Int, Doc>(dataSourceFactory, config)
    }
}

