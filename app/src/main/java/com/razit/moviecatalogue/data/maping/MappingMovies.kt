package com.razit.moviecatalogue.data.maping

import com.razit.moviecatalogue.data.FilmEntity
import com.razit.moviecatalogue.data.response.ResponseDetailMovies
import com.razit.moviecatalogue.data.response.ResponseDetailTvShow
import com.razit.moviecatalogue.data.response.ResultsItem
import com.razit.moviecatalogue.data.response.ResultsTvShow

interface MappingMovies {
    fun moviesToFilmEntity(movies: ResultsItem): FilmEntity
    fun filmToMovies(filmEntity: FilmEntity):ResultsItem
    fun tvShowToFilmEntity(tvShow: ResultsTvShow): FilmEntity
    fun filmToTvShow(filmEntity: FilmEntity):ResultsTvShow
    fun detailTvShowToFilmEntity(tvShow: ResponseDetailTvShow): FilmEntity
    fun filmEntityToDetailTvShow(filmEntity:  FilmEntity):ResponseDetailTvShow
    fun detailMoviesToFilmEntity(movies: ResponseDetailMovies): FilmEntity
    fun filmEntityToDetailMovies(filmEntity: FilmEntity): ResponseDetailMovies
}