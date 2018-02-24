package com.r21nomi.recyclerview_diffutil_sample.diff_util

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import com.r21nomi.recyclerview_diffutil_sample.R
import com.r21nomi.recyclerview_diffutil_sample.data.anime.AnimeDataSetProvider
import com.r21nomi.recyclerview_diffutil_sample.data.anime.entity.Anime
import kotlinx.android.synthetic.main.activity_diff_util.*

/**
 * Created by r21nomi on 2018/02/14.
 */
class DiffUtilActivity : AppCompatActivity() {

    private val itemClickFunc: (Anime, Int) -> Unit = { _, position ->
        val firstItemId = animeAdapter.getOriginalDataSet()[position].id
        val newList = animeAdapter.getDataSet().filter {
            it.id != firstItemId
        }
        animeAdapter.setDataSet(newList)
        animeAdapter.notifyDataSetChanged()
        animeAdapter.notifyItemRemoved(position)
        animeAdapter.notifyItemRangeChanged(position, animeAdapter.itemCount - position)
    }

    private val animeAdapter = AnimeAdapter(AnimeDataSetProvider.get().toMutableList(), itemClickFunc)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diff_util)

        recyclerView.run {
            layoutManager = LinearLayoutManager(this@DiffUtilActivity)
            adapter = animeAdapter
        }

        fab.setOnClickListener {
            val lm: LinearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
            val latItemPosition = lm.findLastVisibleItemPosition()
            val dataSet = animeAdapter.getDataSet().toMutableList()
            val newList = dataSet.apply {
                this.add(latItemPosition, Anime(dataSet.size + 1, "ポプテピピック", 40))
            }
            animeAdapter.updateOnMainThread(newList)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.sort_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sortByTitle -> {
                animeAdapter.updateOnBackgroundThread(AnimeDataSetProvider.sortByTitle(animeAdapter.getDataSet()))
                true
            }
            R.id.sortByRating -> {
                animeAdapter.updateOnBackgroundThread(AnimeDataSetProvider.sortByRating(animeAdapter.getDataSet()))
                true
            }
            R.id.updateItem -> {
                animeAdapter.updateOnBackgroundThread(animeAdapter.getDataSet().map { anime ->
                    if (anime.id == 1) {
                        Anime(
                                anime.id,
                                "citrus",
                                45
                        )
                    } else {
                        Anime(
                                anime.id,
                                anime.title,
                                anime.rating
                        )
                    }
                })
                true
            }
            R.id.removeFirstItem -> {
                val firstItemId = animeAdapter.getOriginalDataSet()[0].id
                val newList = animeAdapter.getDataSet().filter { anime ->
                    anime.id != firstItemId
                }
                animeAdapter.updateOnMainThread(newList)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}