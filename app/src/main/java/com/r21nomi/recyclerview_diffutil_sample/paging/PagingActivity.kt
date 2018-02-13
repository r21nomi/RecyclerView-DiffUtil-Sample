package com.r21nomi.recyclerview_diffutil_sample.paging

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.r21nomi.recyclerview_diffutil_sample.App
import com.r21nomi.recyclerview_diffutil_sample.R
import com.r21nomi.recyclerview_diffutil_sample.data.repos.RepoRepository
import kotlinx.android.synthetic.main.activity_paging.*
import kotlin.properties.Delegates

/**
 * Created by r21nomi on 2018/02/14.
 */
class PagingActivity : AppCompatActivity() {

    companion object {
        private const val LIMIT = 2
    }

    private var repoRepository: RepoRepository by Delegates.notNull()
    private val repoAdapter: RepoAdapter = RepoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_paging)

        repoRepository = (application as App).repoRepository

        recyclerView.run {
            layoutManager = LinearLayoutManager(this@PagingActivity)
            adapter = repoAdapter
        }

        repoRepository.getRepos(LIMIT)
                .observe(this, Observer { pagedList ->
                    repoAdapter.setList(pagedList)
                })
    }
}