package com.razit.moviecatalogue.services

import com.razit.moviecatalogue.data.FilmEntity
import com.razit.moviecatalogue.services.model.ResultsItem
import com.razit.moviecatalogue.services.model.ResultsTvShow

interface MappingMovies {
    fun moviesToFilmEntity(movies: ResultsItem): FilmEntity
    fun tvShowToFilmEntity(tvShow: ResultsTvShow): FilmEntity
}