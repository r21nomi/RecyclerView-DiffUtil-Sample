package com.r21nomi.recyclerview_diffutil_sample.data.repos.local

import android.arch.paging.DataSource
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.r21nomi.recyclerview_diffutil_sample.data.repos.entity.Repo

/**
 * Created by r21nomi on 2018/02/24.
 */
@Dao
interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(repos: List<Repo>)

    @Query("SELECT * FROM Repo ORDER BY full_name COLLATE NOCASE ASC")
    fun findByFullName(): DataSource.Factory<Int, Repo>
}