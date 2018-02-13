package com.r21nomi.recyclerview_diffutil_sample

import android.app.Application
import com.r21nomi.recyclerview_diffutil_sample.data.repos.RepoRepository
import com.r21nomi.recyclerview_diffutil_sample.data.repos.remote.RepoApiClient
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Created by r21nomi on 2018/02/13.
 */
class App : Application() {

    private val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    private val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

    private val repoApiClient = RepoApiClient(retrofit)

    val repoRepository = RepoRepository(repoApiClient)
}