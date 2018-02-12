package com.r21nomi.recyclerview_diffutil_sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val animeAdapter = AnimeAdapter(DataSetProvider.get().toMutableList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.run {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = animeAdapter
        }

        fab.setOnClickListener {
            val lm: LinearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
            val latItemPosition = lm.findLastVisibleItemPosition()
            val dataSet = animeAdapter.dataSet.toMutableList()
            val newList = dataSet.apply {
                this.add(latItemPosition, Anime(dataSet.size + 1, "ポプテピピック", 40))
            }
            animeAdapter.swapOnMainThread(newList)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.sort_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sortByTitle -> {
                animeAdapter.swapOnBackgroundThread(DataSetProvider.sortByTitle(animeAdapter.dataSet))
                true
            }
            R.id.sortByRating -> {
                animeAdapter.swapOnBackgroundThread(DataSetProvider.sortByRating(animeAdapter.dataSet))
                true
            }
            R.id.updateItem -> {
                animeAdapter.swapOnBackgroundThread(animeAdapter.dataSet.map { anime ->
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
                val newList = animeAdapter.dataSet.filterIndexed { index, anime ->
                    index != 0
                }
                animeAdapter.swapOnMainThread(newList)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
