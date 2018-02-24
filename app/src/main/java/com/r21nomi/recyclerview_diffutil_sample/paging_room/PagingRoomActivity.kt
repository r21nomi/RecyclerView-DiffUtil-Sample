package com.r21nomi.recyclerview_diffutil_sample.paging_room

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.r21nomi.recyclerview_diffutil_sample.R
import com.r21nomi.recyclerview_diffutil_sample.data.anime.AnimeDataSetProvider
import com.r21nomi.recyclerview_diffutil_sample.data.anime.AnimeRepository
import kotlinx.android.synthetic.main.activity_paging_room.*
import java.util.concurrent.Executors


/**
 * Created by r21nomi on 2018/02/21.
 */
class PagingRoomActivity : AppCompatActivity() {

    private val animeRepository: AnimeRepository by lazy {
        AnimeRepository(this)
    }

    private val ioExecutor = Executors.newSingleThreadExecutor()

    private val animePagingAdapter: AnimePagingAdapter = AnimePagingAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_paging_room)

        recyclerView.run {
            layoutManager = LinearLayoutManager(this@PagingRoomActivity)
            adapter = animePagingAdapter
        }

        ioExecutor.execute {
            if (!animeRepository.hasItem()) {
                animeRepository.addAnimes(AnimeDataSetProvider.get())
            }
        }

        animeRepository.getAnimesByTitle()
                .observe(this, Observer {
                    animePagingAdapter.setList(it)
                })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.anime_sort_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sortByTitle -> {
                animeRepository.getAnimesByTitle()
                        .observe(this, Observer {
                            animePagingAdapter.setList(it)
                        })
                true
            }
            R.id.sortByRating -> {
//                ioExecutor.execute {
//                    animeRepository.addAnimes(listOf(
//                            Anime(
//                                    animePagingAdapter.itemCount + 1,
//                                    "ポプテピピック" + Math.random(),
//                                    10
//                            )
//                    ))
//                }
                animeRepository.getAnimesByRating()
                        .observe(this, Observer {
                            animePagingAdapter.setList(it)
                        })
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}