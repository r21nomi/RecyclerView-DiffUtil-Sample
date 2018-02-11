package com.r21nomi.recyclerview_diffutil_sample

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by r21nomi on 2018/02/11.
 */
class AnimeAdapter(initialDataSet: List<Anime>) : RecyclerView.Adapter<AnimeAdapter.ViewHolder>() {

    val dataSet: MutableList<Anime> = initialDataSet.toMutableList()

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
            title.text = data.title
            rating.text = data.rating.toString()
        }
    }

    fun swap(actors: List<Anime>) {
        val diffCallback = AnimeDiffCallback(this.dataSet, actors)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.dataSet.clear()
        this.dataSet.addAll(actors)
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val rating: TextView = itemView.findViewById(R.id.rating)
    }
}