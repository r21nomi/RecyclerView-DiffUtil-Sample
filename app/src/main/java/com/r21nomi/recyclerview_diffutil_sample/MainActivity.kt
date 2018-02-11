package com.r21nomi.recyclerview_diffutil_sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem

class MainActivity : AppCompatActivity() {

    private val animeAdapter = AnimeAdapter(DataSetProvider.get())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<RecyclerView>(R.id.recyclerView).run {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = animeAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.sort_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.sortByTitle -> {
                animeAdapter.swap(DataSetProvider.sortByTitle(animeAdapter.dataSet))
                true
            }
            R.id.sortByRating -> {
                animeAdapter.swap(DataSetProvider.sortByRating(animeAdapter.dataSet))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
