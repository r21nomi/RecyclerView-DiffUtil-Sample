package com.r21nomi.recyclerview_diffutil_sample.data.repos

import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.r21nomi.recyclerview_diffutil_sample.data.repos.entity.Repo
import com.r21nomi.recyclerview_diffutil_sample.data.repos.remote.RepoApiClient
import java.util.concurrent.Executors

/**
 * Created by r21nomi on 2018/02/14.
 */
class RepoRepository(private val repoApiClient: RepoApiClient) {

    companion object {
        private val NETWORK_IO = Executors.newFixedThreadPool(5)
    }

    fun getRepos(limit: Int,
                 sort: RepoApiClient.Sort = RepoApiClient.Sort.FULL_NAME
    ): LiveData<PagedList<Repo>> {
        val sourceFactory = RepoDataFactory(repoApiClient, sort)

        val config = PagedList.Config.Builder()
                .setInitialLoadSizeHint(limit * 2)
                .setPageSize(limit)
//                .setPrefetchDistance(limit / 2)
                .build()

        return LivePagedListBuilder(sourceFactory, config)
                .setBackgroundThreadExecutor(NETWORK_IO)
                .build()
    }
}