package com.r21nomi.recyclerview_diffutil_sample.data.anime.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.r21nomi.recyclerview_diffutil_sample.data.anime.entity.Anime

/**
 * Created by r21nomi on 2018/02/21.
 */
@Database(entities = arrayOf(Anime::class), version = 1)
abstract class AnimeDb: RoomDatabase() {
    abstract fun animeDao(): AnimeDao
}