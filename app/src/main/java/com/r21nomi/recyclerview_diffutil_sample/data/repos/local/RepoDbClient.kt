package com.r21nomi.recyclerview_diffutil_sample.data.repos.local

import android.arch.paging.DataSource
import android.arch.persistence.room.Room
import android.content.Context
import com.r21nomi.recyclerview_diffutil_sample.data.repos.entity.Repo

/**
 * Created by r21nomi on 2018/02/24.
 */
class RepoDbClient(private val context: Context) {

    private val db: RepoDb by lazy {
        Room.databaseBuilder(context, RepoDb::class.java, "repo-db").build()
    }

    fun findByFullName(): DataSource.Factory<Int, Repo> {
        return db.repoDao().findByFullName()
    }

    fun insert(repos: List<Repo>) {
        db.repoDao().insert(repos)
    }
}