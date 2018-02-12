package com.r21nomi.recyclerview_diffutil_sample

import android.os.Handler
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by r21nomi on 2018/02/11.
 */
class AnimeAdapter(initialDataSet: MutableList<Anime>) : RecyclerView.Adapter<AnimeAdapter.ViewHolder>() {

    val dataSet: MutableList<Anime> = initialDataSet

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

    /**
     * This process might block main thread if the list is huge.
     */
    fun swapOnMainThread(animes: List<Anime>) {
        val diffCallback = AnimeDiffCallback(this.dataSet, animes)
        val diffResult = DiffUtil.calculateDiff(diffCallback, true)

        this.dataSet.clear()
        this.dataSet.addAll(animes)
        diffResult.dispatchUpdatesTo(this)
    }

    /**
     * This process never block main thread even if it's a huge list.
     */
    fun swapOnBackgroundThread(animes: List<Anime>) {
        val handler = Handler()
        Thread({
            val diffCallback = AnimeDiffCallback(this.dataSet, animes)
            // Calculate diff in background thread.
            val diffResult = DiffUtil.calculateDiff(diffCallback, true)

            handler.post {
                // Apply diffResult in main thread.
                this.dataSet.clear()
                this.dataSet.addAll(animes)
                diffResult.dispatchUpdatesTo(this)
            }
        }).start()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val rating: TextView = itemView.findViewById(R.id.rating)
    }
}