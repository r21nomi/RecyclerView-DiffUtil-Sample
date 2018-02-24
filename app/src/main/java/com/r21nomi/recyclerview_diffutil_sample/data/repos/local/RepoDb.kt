package com.r21nomi.recyclerview_diffutil_sample.data.repos.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.r21nomi.recyclerview_diffutil_sample.data.repos.entity.Repo

/**
 * Created by r21nomi on 2018/02/24.
 */
@Database(entities = arrayOf(Repo::class), version = 1)
abstract class RepoDb : RoomDatabase() {
    abstract fun repoDao(): RepoDao
}