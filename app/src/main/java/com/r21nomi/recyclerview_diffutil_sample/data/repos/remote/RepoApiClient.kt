package com.r21nomi.recyclerview_diffutil_sample.data.repos.remote

import com.r21nomi.recyclerview_diffutil_sample.BuildConfig
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

    enum class Sort(val value: String) {
        FULL_NAME("full_name"),
        CREATED("created"),
        UPDATED("updated"),
        PUSHED("pushed")
    }

    constructor(retrofit: Retrofit) : this(retrofit.create(Service::class.java))

    fun getRepos(user: String,
                 page: Int,
                 limit: Int,
                 sort: Sort = Sort.FULL_NAME): Call<List<Repo>> {
        return service.getRepos(
                user,
                BuildConfig.GITHUB_CLIENT_ID,
                BuildConfig.GITHUB_CLIENT_SECRET,
                page,
                limit,
                sort.value
        )
    }

    internal interface Service {
        @GET("/users/{user}/repos")
        fun getRepos(
                @Path("user") user: String,
                @Query("client_id") clientId: String,
                @Query("client_secret") clientSecret: String,
                @Query("page") page: Int,
                @Query("per_page") prePage: Int,
                @Query("sort") sort: String
        ): Call<List<Repo>>
    }
}