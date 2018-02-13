package com.r21nomi.recyclerview_diffutil_sample.data.anime

import com.r21nomi.recyclerview_diffutil_sample.data.anime.entity.Anime

/**
 * Created by r21nomi on 2018/02/11.
 */
object AnimeDataSetProvider {

    private val dataSet: List<Anime> = listOf(
            Anime(1, "宇宙よりも遠い場所", 80),
            Anime(2, "だがしかし2", 40),
            Anime(3, "ダーリン・イン・ザ・フランキス", 90),
            Anime(4, "BEATLESS", 30),
            Anime(5, "Fate/EXTRA Last Encore", 50),
            Anime(6, "刻刻", 30),
            Anime(7, "からかい上手の高木さん", 60),
            Anime(8, "ヴァイオレット・エヴァーガーデン", 90),
            Anime(9, "衛宮さんちの今日のごはん", 40),
            Anime(10, "ゆるキャン△", 60),
            Anime(11, "ラーメン大好き小泉さん", 80),
            Anime(12, "りゅうおうのおしごと！", 60),
            Anime(13, "恋は雨上がりのように", 50)
    )

    fun get(): List<Anime> {
        return dataSet
    }

    fun sortByTitle(oldDataSet: List<Anime>): List<Anime> = oldDataSet.sortedBy { it.title }

    fun sortByRating(oldDataSet: List<Anime>): List<Anime> = oldDataSet.sortedByDescending { it.rating }
}