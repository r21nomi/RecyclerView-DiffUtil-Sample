package com.r21nomi.recyclerview_diffutil_sample.data.repos.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.squareup.moshi.Json

/**
 * Created by r21nomi on 2018/02/13.
 */
@Entity
data class Repo(
        @PrimaryKey(autoGenerate = false)
        var id: Long = 0,

        var name: String = "",

        @ColumnInfo(name = "full_name")
        @Json(name = "full_name")
        var fullName: String = "",

        @ColumnInfo(name = "stargazers_count")
        @Json(name = "stargazers_count")
        var stargazersCount: Int = 0,

        var description: String? = null
)