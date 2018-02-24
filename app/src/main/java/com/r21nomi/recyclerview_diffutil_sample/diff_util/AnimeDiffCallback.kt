package com.r21nomi.recyclerview_diffutil_sample.diff_util

import android.support.v7.util.DiffUtil
import android.util.Log
import com.r21nomi.recyclerview_diffutil_sample.data.anime.entity.Anime
import java.util.*

class AnimeDiffCallback(
        private val oldList: List<Anime>,
        private val newList: List<Anime>,
        private val shouldEmulateHugeList: Boolean = false
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize(): Int {
        if (shouldEmulateHugeList) {
            // Emulate huge process.
            try {
                Thread.sleep(Random().nextInt(3000).toLong())
            } catch (e: InterruptedException) {
                Log.e(this.javaClass.name, e.message)
            }
        }
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = oldList[oldItemPosition]
        val new = newList[newItemPosition]
        val isSame = old == new
        return isSame
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}