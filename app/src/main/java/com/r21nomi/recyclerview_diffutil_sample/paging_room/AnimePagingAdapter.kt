package com.r21nomi.recyclerview_diffutil_sample.paging_room

import android.arch.paging.PagedListAdapter
import android.support.v7.recyclerview.extensions.DiffCallback
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.r21nomi.recyclerview_diffutil_sample.R
import com.r21nomi.recyclerview_diffutil_sample.data.anime.entity.Anime

/**
 * Created by r21nomi on 2018/02/14.
 */
class AnimePagingAdapter : PagedListAdapter<Anime, AnimePagingAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffCallback<Anime>() {
            override fun areItemsTheSame(oldItem: Anime, newItem: Anime): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Anime, newItem: Anime): Boolean {
                val isSame = oldItem == newItem
                return isSame
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.anime_viewholder, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = getItem(position)?.title

        val anime = getItem(position)

        holder.run {
            title.text = anime?.title
            rating.text = anime?.rating.toString()
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val rating: TextView = itemView.findViewById(R.id.rating)
    }
}