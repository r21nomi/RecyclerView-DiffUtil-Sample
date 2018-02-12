package com.r21nomi.recyclerview_diffutil_sample

import android.support.v7.util.DiffUtil

class AnimeDiffCallback(
        private val oldList: List<Anime>,
        private val newList: List<Anime>
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize(): Int {
        // Emulate huge process.
//        try {
//            Thread.sleep(Random().nextInt(3000).toLong())
//        } catch (e: InterruptedException) {
//            Log.e(this.javaClass.name, e.message)
//        }
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
}