package com.r21nomi.recyclerview_diffutil_sample.data.anime

import android.arch.lifecycle.LiveData
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.content.Context
import com.r21nomi.recyclerview_diffutil_sample.data.anime.entity.Anime
import com.r21nomi.recyclerview_diffutil_sample.data.anime.local.AnimeDbClient

/**
 * Created by r21nomi on 2018/02/21.
 */
class AnimeRepository(context: Context) {

    companion object {
        private const val LIMIT = 10
    }

    private val dbClient: AnimeDbClient = AnimeDbClient(context)

    private val config = PagedList.Config.Builder()
            .setPageSize(LIMIT)
            .setEnablePlaceholders(true)
            .setPrefetchDistance(10)
            .build()

    fun hasItem(): Boolean = dbClient.hasItem()

    fun getAnimesByTitle(): LiveData<PagedList<Anime>> {
        return LivePagedListBuilder(dbClient.findByTitle(), config).build()
    }

    fun getAnimesByRating(): LiveData<PagedList<Anime>> {
        return LivePagedListBuilder(dbClient.findByRating(), config).build()
    }

    fun addAnimes(animes: List<Anime>) {
        dbClient.insert(animes)
    }
}