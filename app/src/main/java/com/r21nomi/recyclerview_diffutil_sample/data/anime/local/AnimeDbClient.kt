package com.r21nomi.recyclerview_diffutil_sample.data.anime.local

import android.arch.paging.DataSource
import android.arch.persistence.room.Room
import android.content.Context
import com.r21nomi.recyclerview_diffutil_sample.data.anime.entity.Anime

/**
 * Created by r21nomi on 2018/02/21.
 */
class AnimeDbClient(private val context: Context) {

    companion object {
        private const val LIMIT = 2
    }

    private val db: AnimeDb by lazy {
        Room.databaseBuilder(context, AnimeDb::class.java, "anime-db")
                .build()
    }

    fun findByTitle(): DataSource.Factory<Int, Anime> {
        return db.animeDao().findByTitle()
    }

    fun findByRating(): DataSource.Factory<Int, Anime> = db.animeDao().findByRating()

    fun insert(animes: List<Anime>) = db.animeDao().insert(animes)

    fun hasItem(): Boolean = db.animeDao().size() > 0
}