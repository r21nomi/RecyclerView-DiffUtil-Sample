package com.r21nomi.recyclerview_diffutil_sample.data.repos

import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.content.Context
import com.r21nomi.recyclerview_diffutil_sample.data.repos.entity.Repo
import com.r21nomi.recyclerview_diffutil_sample.data.repos.local.RepoDbClient
import com.r21nomi.recyclerview_diffutil_sample.data.repos.remote.RepoApiClient
import java.util.concurrent.Executors

/**
 * Created by r21nomi on 2018/02/14.
 */
class RepoRepository2(context: Context, private val repoApiClient: RepoApiClient) {

    companion object {
        private val NETWORK_IO = Executors.newFixedThreadPool(5)
    }

    private var page = 0
    private var limit = 0
    private var sort: RepoApiClient.Sort = RepoApiClient.Sort.FULL_NAME

    private val boundaryCallback = object : PagedList.BoundaryCallback<Repo>() {
        override fun onZeroItemsLoaded() {
            NETWORK_IO.execute {
                requestApi()
            }
        }

        override fun onItemAtEndLoaded(itemAtEnd: Repo) {
            NETWORK_IO.execute {
                requestApi()
            }
        }

        override fun onItemAtFrontLoaded(itemAtFront: Repo) {
            // no-op
        }
    }

    private val dbClient: RepoDbClient = RepoDbClient(context)

    fun getRepos(limit: Int, sort: RepoApiClient.Sort = RepoApiClient.Sort.FULL_NAME): LiveData<PagedList<Repo>> {
        this.limit = limit
        this.sort = sort

        val config = PagedList.Config.Builder()
//                .setInitialLoadSizeHint(limit * 2)
                .setPageSize(limit)
//                .setPrefetchDistance(limit / 2)
                .build()

        return LivePagedListBuilder(dbClient.findByFullName(), config)
                .setBackgroundThreadExecutor(NETWORK_IO)
                .setBoundaryCallback(boundaryCallback)
                .build()
    }

    private fun requestApi() {
        page++

        val response = repoApiClient
                .getRepos("r21nomi", page, limit, sort)
                .execute()

        response.body()?.let {
            dbClient.insert(it)
        }
    }
}