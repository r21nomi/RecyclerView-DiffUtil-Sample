package com.r21nomi.recyclerview_diffutil_sample.data.anime.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by r21nomi on 2018/02/11.
 */
@Entity
data class Anime(
        @PrimaryKey(autoGenerate = false)
        val id: Int,

        @ColumnInfo(name = "title")
        val title: String,

        @ColumnInfo(name = "rating")
        val rating: Long
)