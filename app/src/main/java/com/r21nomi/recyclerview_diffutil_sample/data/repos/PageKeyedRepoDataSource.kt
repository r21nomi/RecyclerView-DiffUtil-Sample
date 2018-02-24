package com.r21nomi.recyclerview_diffutil_sample.data.repos

import android.arch.paging.PageKeyedDataSource
import android.util.Log
import com.r21nomi.recyclerview_diffutil_sample.data.repos.entity.Repo
import com.r21nomi.recyclerview_diffutil_sample.data.repos.remote.RepoApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        repoApiClient
                .getRepos(USER_NAME, 1, params.requestedLoadSize, sort)
                .enqueue(object : Callback<List<Repo>> {
                    override fun onResponse(call: Call<List<Repo>>, response: Response<List<Repo>>) {
                        callback.onResult(response.body() ?: return, 1, 2)
                    }

                    override fun onFailure(call: Call<List<Repo>>, t: Throwable) {
                        Log.e(this.javaClass.name, t.message)
                    }
                })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Repo>) {
        // no-op
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Repo>) {
        repoApiClient
                .getRepos(USER_NAME, params.key, params.requestedLoadSize, sort)
                .enqueue(object : Callback<List<Repo>> {
                    override fun onResponse(call: Call<List<Repo>>, response: Response<List<Repo>>) {
                        callback.onResult(response.body() ?: return, params.key + 1)
                    }

                    override fun onFailure(call: Call<List<Repo>>, t: Throwable) {
                        Log.e(this.javaClass.name, t.message)
                    }
                })
    }
}