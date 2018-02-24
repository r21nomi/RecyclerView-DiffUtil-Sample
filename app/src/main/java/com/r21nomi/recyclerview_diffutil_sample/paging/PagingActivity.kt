package com.r21nomi.recyclerview_diffutil_sample.paging

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.r21nomi.recyclerview_diffutil_sample.App
import com.r21nomi.recyclerview_diffutil_sample.R
import com.r21nomi.recyclerview_diffutil_sample.data.repos.RepoRepository
import com.r21nomi.recyclerview_diffutil_sample.data.repos.remote.RepoApiClient
import kotlinx.android.synthetic.main.activity_paging.*
import kotlin.properties.Delegates

/**
 * Created by r21nomi on 2018/02/14.
 */
class PagingActivity : AppCompatActivity() {

    companion object {
        private const val LIMIT = 4
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.repo_sort_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sortByFullName -> {
                repoAdapter.setList(null)
                repoRepository.getRepos(LIMIT, RepoApiClient.Sort.FULL_NAME)
                        .observe(this, Observer { pagedList ->
                            repoAdapter.setList(pagedList)
                        })
                true
            }
            R.id.sortByCreated -> {
                repoAdapter.setList(null)
                repoRepository.getRepos(LIMIT, RepoApiClient.Sort.CREATED)
                        .observe(this, Observer { pagedList ->
                            repoAdapter.setList(pagedList)
                        })
                true
            }
            R.id.sortByUpdated -> {
                repoAdapter.setList(null)
                repoRepository.getRepos(LIMIT, RepoApiClient.Sort.UPDATED)
                        .observe(this, Observer { pagedList ->
                            repoAdapter.setList(pagedList)
                        })
                true
            }
            R.id.sortByPushed -> {
                repoAdapter.setList(null)
                repoRepository.getRepos(LIMIT, RepoApiClient.Sort.PUSHED)
                        .observe(this, Observer { pagedList ->
                            repoAdapter.setList(pagedList)
                        })
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}