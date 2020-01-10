package com.ardakazanci.samplesocialmediaapp.ui.main.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.algolia.instantsearch.core.connection.ConnectionHandler
import com.algolia.instantsearch.helper.android.list.SearcherSingleIndexDataSource
import com.algolia.instantsearch.helper.android.searchbox.SearchBoxConnectorPagedList
import com.algolia.instantsearch.helper.searcher.SearcherSingleIndex
import com.algolia.instantsearch.helper.stats.StatsConnector
import com.algolia.search.client.ClientSearch
import com.algolia.search.model.APIKey
import com.algolia.search.model.ApplicationID
import com.algolia.search.model.IndexName
import com.ardakazanci.samplesocialmediaapp.data.model.DataModel
import com.ardakazanci.samplesocialmediaapp.utils.Constants
import io.ktor.client.features.logging.LogLevel

class SearchViewModel : ViewModel() {

    // Algolia yı kullanabilmek için gerekli kurumluklar.
    val client = ClientSearch(
        ApplicationID(
            Constants.ALGOLIA_APPLICATION_ID
        ),
        APIKey(Constants.ALGOLIA_ADMIN_API_KEY),
        LogLevel.ALL
    )
    val index = client.initIndex(IndexName(Constants.ALGOLIA_INDEX_NAME))
    // Arama sonuçlarını elde eden kısım burasıdır.
    val searcher = SearcherSingleIndex(index)

    val dataSourceFactory = SearcherSingleIndexDataSource.Factory(searcher) { hit ->

        DataModel.AlgoliaUserResponseModel(
            hit.json.getPrimitive("objectID").content,
            hit.json.getPrimitive("userFullName").content,
            hit.json.getPrimitive("userImageBase64").content
        )
    }

    val pagedListConfig = PagedList.Config.Builder().setPageSize(20).build()
    val users: LiveData<PagedList<DataModel.AlgoliaUserResponseModel>> =
        LivePagedListBuilder(dataSourceFactory, pagedListConfig).build()


    // Arama Kısmı
    val searchBox = SearchBoxConnectorPagedList(searcher, listOf(users))

    val connection = ConnectionHandler()

    init {

        connection += searchBox

    }


    override fun onCleared() {
        super.onCleared()
        searcher.cancel()
        connection.disconnect()

    }


}
