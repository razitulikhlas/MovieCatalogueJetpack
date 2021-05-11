package com.razit.moviecatalogue.viewmodel

import androidx.lifecycle.ViewModel
import com.razit.moviecatalogue.data.FilmEntity
import com.razit.moviecatalogue.utils.DataFilm

class FilmViewModel : ViewModel() {
    fun getMovies():List<FilmEntity> = DataFilm.generateDummyMovies()
    fun getTvShow():List<FilmEntity> = DataFilm.generateDummyTvShow()

    fun getDetailMovies(idFilm :Int): FilmEntity {
        lateinit var film: FilmEntity
        val filmEntities = DataFilm.generateDummyMovies()
        for (filmEntity in filmEntities) {
            if (filmEntity.id == idFilm) {
                film = filmEntity
            }
        }
        return film
    }

    fun getDetailTvShow(idTv :Int): FilmEntity {
        lateinit var tvShow: FilmEntity
        val tvShowEntities = DataFilm.generateDummyTvShow()
        for (tvShowEntity in tvShowEntities) {
            if (tvShowEntity.id == idTv) {
                tvShow = tvShowEntity
            }
        }
        return tvShow
    }


}