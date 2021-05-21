package com.razit.moviecatalogue.services

import com.razit.moviecatalogue.data.FilmEntity
import com.razit.moviecatalogue.services.model.ResultsItem
import com.razit.moviecatalogue.services.model.ResultsTvShow

class NetworkMapperMovies : MappingMovies {

    override fun moviesToFilmEntity(movies: ResultsItem): FilmEntity {
        return FilmEntity(
            id = movies.id,
            title = movies.title,
            description = movies.overview,
            release = movies.releaseDate,
            imageUrl = movies.posterPath,
            director = null,
            genre = null
        )
    }

    override fun tvShowToFilmEntity(tvShow: ResultsTvShow): FilmEntity {
        return FilmEntity(
            id = tvShow.id,
            title = tvShow.name,
            description = tvShow.overview,
            release = tvShow.firstAirDate,
            imageUrl = tvShow.posterPath,
            director = null,
            genre = null
        )
    }

    fun fromMoviesToFilmEntity(initial: List<ResultsItem>): List<FilmEntity> {
        return initial.map { moviesToFilmEntity(it) }
    }

    fun fromTvShowToFilmEntity(initial: List<ResultsTvShow>): List<FilmEntity> {
        return initial.map { tvShowToFilmEntity(it) }
    }
}