package com.r21nomi.recyclerview_diffutil_sample.data.repos.remote

import com.r21nomi.recyclerview_diffutil_sample.data.repos.entity.Repo
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by r21nomi on 2018/02/13.
 */
class RepoApiClient internal constructor(private val service: Service) {

    constructor(retrofit: Retrofit) : this(retrofit.create(Service::class.java))

    fun getRepos(user: String, page: Int, limit: Int): Call<List<Repo>> {
        return service.getRepos(user, page, limit)
    }

    internal interface Service {
        @GET("/users/{user}/repos")
        fun getRepos(
                @Path("user") user: String,
                @Query("page") page: Int,
                @Query("per_page") prePage: Int
        ): Call<List<Repo>>
    }
}