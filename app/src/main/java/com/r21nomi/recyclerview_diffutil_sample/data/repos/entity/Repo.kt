package com.r21nomi.recyclerview_diffutil_sample.data.repos.entity

import com.squareup.moshi.Json

/**
 * Created by r21nomi on 2018/02/13.
 */
data class Repo(
        var id: Long = 0,

        var name: String = "",

        @Json(name = "full_name")
        var fullName: String = "",

        @Json(name = "stargazers_count")
        var stargazersCount: Int = 0,

        var description: String? = null
)