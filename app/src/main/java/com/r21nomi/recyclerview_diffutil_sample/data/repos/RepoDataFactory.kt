package com.r21nomi.recyclerview_diffutil_sample.data.repos

import android.arch.paging.DataSource
import com.r21nomi.recyclerview_diffutil_sample.data.repos.entity.Repo
import com.r21nomi.recyclerview_diffutil_sample.data.repos.remote.RepoApiClient

/**
 * Created by r21nomi on 2018/02/14.
 */
class RepoDataFactory(
        private val repoApiClient: RepoApiClient,
        private val sort: RepoApiClient.Sort
) : DataSource.Factory<Int, Repo> {

    override fun create(): DataSource<Int, Repo> {
        return PageKeyedRepoDataSource(repoApiClient, sort)
    }
}