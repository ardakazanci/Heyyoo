package com.ardakazanci.samplesocialmediaapp.ui.main.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.ardakazanci.samplesocialmediaapp.data.model.DataModel
import com.ardakazanci.samplesocialmediaapp.data.model.Doc
import com.ardakazanci.samplesocialmediaapp.data.network.ApiService
import com.ardakazanci.samplesocialmediaapp.data.network.IApiInterface
import com.ardakazanci.samplesocialmediaapp.repositories.DislikeRepository
import com.ardakazanci.samplesocialmediaapp.repositories.LikeRepository
import com.ardakazanci.samplesocialmediaapp.ui.main.ui.home.datasource.ContentDataSource
import com.ardakazanci.samplesocialmediaapp.utils.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class HomeViewModel(private val app: Application) : AndroidViewModel(app) {

    var postsLiveData: LiveData<PagedList<Doc>>


    private val likeRepository = LikeRepository(ApiService.mainApi)
    private val dislikeRepository = DislikeRepository(ApiService.mainApi)

    // <<< COROUTINES BAŞLANGIÇ >>>
    private val parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)
    // <<< COROUTINES SON >>>


    fun likeOperation(contentid: String, likecount: Int) {


        scope.launch {


            val control = likeRepository.getLikeResponse(contentid)

            control?.let {
                if (control) {
                    app.toast("Like Başarılı")
                } else {
                    app.toast("Like Başarısız")
                }
            }

        }

    }

    fun dislikeOperation(contentid: String) {


        scope.launch {


            val control = dislikeRepository.getDislikeResponse(contentid)

            control?.let {
                if (control) {
                    app.toast("Dislike Başarılı")
                } else {
                    app.toast("Dislike Başarısız")
                }
            }

        }
    }


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

