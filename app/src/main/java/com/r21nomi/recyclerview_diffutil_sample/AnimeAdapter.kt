package com.r21nomi.recyclerview_diffutil_sample

import android.os.Handler
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.util.*

/**
 * Created by r21nomi on 2018/02/11.
 */
class AnimeAdapter(initialDataSet: MutableList<Anime>) : RecyclerView.Adapter<AnimeAdapter.ViewHolder>() {

    private val dataSet: MutableList<Anime> = initialDataSet
    private val pendingUpdates: ArrayDeque<List<Anime>> = ArrayDeque()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.anime_viewholder, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataSet[position]

        holder.run {
            title.text = "${data.id}: ${data.title}"
            rating.text = data.rating.toString()
        }

        Log.d(this.javaClass.name, "onBindViewHolder - position: $position, id: ${data.id}, title: ${data.title}")
    }

    fun getOriginalDataSet(): List<Anime> {
        return dataSet
    }

    fun getDataSet(): List<Anime> {
        return if (pendingUpdates.isNotEmpty()) pendingUpdates.peekLast() else dataSet
    }

    /**
     * This process might block main thread if the list is huge.
     */
    fun updateOnMainThread(animes: List<Anime>) {
        pendingUpdates.add(animes)
        if (pendingUpdates.size > 1) {
            return
        }

        updateInternal(animes, false)
    }

    /**
     * This process never block main thread even if it's a huge list.
     */
    fun updateOnBackgroundThread(animes: List<Anime>) {
        pendingUpdates.add(animes)
        if (pendingUpdates.size > 1) {
            return
        }

        updateInternal(animes, true)
    }

    private fun updateInternal(animes: List<Anime>, shouldEmulateHugeList: Boolean) {
        if (shouldEmulateHugeList) {
            val handler = Handler()
            Thread({
                val diffCallback = AnimeDiffCallback(this.dataSet, animes, true)
                // Calculate diff in background thread.
                val diffResult = DiffUtil.calculateDiff(diffCallback, true)

                handler.post {
                    // Apply diffResult in main thread.
                    dispatchUpdates(animes, diffResult)
                }
            }).start()
        } else {
            val diffCallback = AnimeDiffCallback(this.dataSet, animes)
            val diffResult = DiffUtil.calculateDiff(diffCallback, true)

            dispatchUpdates(animes, diffResult)
        }
    }

    private fun dispatchUpdates(newList: List<Anime>, diffResult: DiffUtil.DiffResult) {
        pendingUpdates.remove()

        // Apply diffResult in main thread.
        this.dataSet.clear()
        this.dataSet.addAll(newList)
        diffResult.dispatchUpdatesTo(this)

        if (pendingUpdates.isNotEmpty()) {
            if (pendingUpdates.size > 1) {
                val lastList = pendingUpdates.peek()
                pendingUpdates.clear()
                pendingUpdates.add(lastList)
            }
            updateInternal(pendingUpdates.peek(), false)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val rating: TextView = itemView.findViewById(R.id.rating)
    }
}