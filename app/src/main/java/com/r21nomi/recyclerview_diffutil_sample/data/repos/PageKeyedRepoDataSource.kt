package com.r21nomi.recyclerview_diffutil_sample.data.repos

import android.arch.paging.PageKeyedDataSource
import com.r21nomi.recyclerview_diffutil_sample.data.repos.entity.Repo
import com.r21nomi.recyclerview_diffutil_sample.data.repos.remote.RepoApiClient

/**
 * Created by r21nomi on 2018/02/14.
 */
class PageKeyedRepoDataSource(
        private val repoApiClient: RepoApiClient,
        private val sort: RepoApiClient.Sort
) : PageKeyedDataSource<Int, Repo>() {

    companion object {
        private const val USER_NAME = "r21nomi"
    }

    override fun loadInitial(params: LoadInitialParams<Int>,
                             callback: LoadInitialCallback<Int, Repo>) {
        val response = repoApiClient
                .getRepos(USER_NAME, 1, params.requestedLoadSize, sort)
                // Do not use enqueue() here since all items are redrawn after sorting.
                // This is because LoadInitialCallback.onResult is called on main thread if using enqueue().
                .execute()
        response.body()?.let {
            callback.onResult(it, null, 2)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Repo>) {
        // no-op
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Repo>) {
        val response = repoApiClient
                .getRepos(USER_NAME, params.key, params.requestedLoadSize, sort)
                .execute()
        response.body()?.let {
            callback.onResult(it, params.key + 1)
        }
    }
}