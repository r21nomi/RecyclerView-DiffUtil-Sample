package com.r21nomi.recyclerview_diffutil_sample.data.anime.local

import android.arch.paging.DataSource
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.r21nomi.recyclerview_diffutil_sample.data.anime.entity.Anime

/**
 * Created by r21nomi on 2018/02/21.
 */
@Dao
interface AnimeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(animes: List<Anime>)

    @Query("SELECT * FROM Anime ORDER BY title COLLATE NOCASE ASC")
    fun findByTitle(): DataSource.Factory<Int, Anime>

    @Query("SELECT * FROM Anime ORDER BY rating COLLATE NOCASE ASC")
    fun findByRating(): DataSource.Factory<Int, Anime>

    @Query("SELECT COUNT(*) FROM Anime")
    fun size(): Int
}