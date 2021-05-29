package com.razit.moviecatalogue.data.maping

import com.razit.moviecatalogue.BuildConfig
import com.razit.moviecatalogue.data.FilmEntity
import com.razit.moviecatalogue.data.response.ResponseDetailMovies
import com.razit.moviecatalogue.data.response.ResponseDetailTvShow
import com.razit.moviecatalogue.data.response.ResultsItem
import com.razit.moviecatalogue.data.response.ResultsTvShow

class NetworkMapperMovies : MappingMovies {

    override fun moviesToFilmEntity(movies: ResultsItem): FilmEntity {
        return FilmEntity(
            id = movies.id,
            title = movies.title,
            description = movies.overview,
            release = movies.releaseDate,
            imageUrl = movies.posterPath,
            language = null,
            genre = null,
            rating = movies.voteAverage,
            type = BuildConfig.MOVIES
        )
    }

    override fun filmToMovies(filmEntity: FilmEntity): ResultsItem {
        return ResultsItem(
            overview = filmEntity.description,
            originalLanguage = filmEntity.language,
            originalTitle = filmEntity.title,
            video = false,
            title = filmEntity.title,
            genreIds = null,
            posterPath = null,
            backdropPath = null,
            releaseDate = filmEntity.release,
            popularity = filmEntity.rating,
            voteAverage = 400.0,
            id = filmEntity.id,
            adult = false,
            voteCount = 800
        )
    }

    override fun tvShowToFilmEntity(tvShow: ResultsTvShow): FilmEntity {
        return FilmEntity(
            id = tvShow.id,
            title = tvShow.name,
            description = tvShow.overview,
            release = tvShow.firstAirDate,
            imageUrl = tvShow.posterPath,
            language = null,
            genre = null,
            rating = tvShow.voteAverage,
            type = BuildConfig.TVSHOW
        )
    }

    override fun filmToTvShow(filmEntity: FilmEntity): ResultsTvShow {
        return ResultsTvShow(
            overview = filmEntity.description,
            voteCount = 700,
            voteAverage = filmEntity.rating,
            id = filmEntity.id,
            popularity = filmEntity.rating,
            backdropPath = filmEntity.imageUrl,
            posterPath = filmEntity.imageUrl,
            genreIds = null,
            originalLanguage = filmEntity.language,
            name = filmEntity.title,
            firstAirDate = filmEntity.release,
            originalName = filmEntity.title,
            originCountry = null
        )
    }

    override fun detailTvShowToFilmEntity(tvShow: ResponseDetailTvShow): FilmEntity {
        return FilmEntity(
            id = tvShow.id,
            title = tvShow.name,
            description = tvShow.overview,
            release = tvShow.firstAirDate,
            imageUrl = tvShow.posterPath,
            language = tvShow.spokenLanguages?.get(0)?.englishName,
            genre = tvShow.genres?.get(0)?.name,
            rating = tvShow.voteAverage,
        )
    }

    override fun filmEntityToDetailTvShow(filmEntity: FilmEntity): ResponseDetailTvShow {
        return ResponseDetailTvShow(
            id = filmEntity.id,
            name = filmEntity.title,
            createdBy = null,
            originCountry = null,
            firstAirDate = filmEntity.release,
            originalName = filmEntity.title,
            originalLanguage = filmEntity.language,
            posterPath = filmEntity.imageUrl,
            backdropPath = filmEntity.imageUrl,
            popularity = filmEntity.rating,
            voteAverage = filmEntity.rating,
            voteCount = 700,
            overview = filmEntity.description,
            type = null
        )
    }


    override fun detailMoviesToFilmEntity(movies: ResponseDetailMovies): FilmEntity {
        return FilmEntity(
            id = movies.id,
            title = movies.title,
            description = movies.overview,
            release = movies.releaseDate,
            imageUrl = movies.posterPath,
            language = movies.spokenLanguages?.get(0)?.englishName,
            genre = movies.genres?.get(0)?.name,
            rating = movies.voteAverage,
        )
    }

    override fun filmEntityToDetailMovies(filmEntity: FilmEntity): ResponseDetailMovies {
        return ResponseDetailMovies(
            id = filmEntity.id,
            title = filmEntity.title,
            releaseDate = filmEntity.release,
            popularity = filmEntity.rating,
            overview = filmEntity.description,
            posterPath = filmEntity.imageUrl,
            backdropPath = filmEntity.imageUrl,
            originalTitle = filmEntity.title,
            originalLanguage = filmEntity.language
        )
    }


    fun fromMoviesToFilmEntity(initial: List<ResultsItem>): List<FilmEntity> {
        return initial.map { moviesToFilmEntity(it) }
    }
    fun fromFilmEntityToMovies(initial: List<FilmEntity>): List<ResultsItem> {
        return initial.map { filmToMovies(it) }
    }

    fun fromTvShowToFilmEntity(initial: List<ResultsTvShow>): List<FilmEntity> {
        return initial.map { tvShowToFilmEntity(it) }
    }

    fun fromFilmToTvShow(initial: List<FilmEntity>): List<ResultsTvShow> {
        return initial.map { filmToTvShow(it) }
    }

    fun fromFilmToDetailMovies(initial: FilmEntity): ResponseDetailMovies {
        return filmEntityToDetailMovies(initial)
    }

    fun fromFilmToTvShow(initial: FilmEntity): ResponseDetailTvShow {
        return filmEntityToDetailTvShow(initial)
    }


    fun fromDetailMoviesToFilmEntity(initial: ResponseDetailMovies): FilmEntity {
        return detailMoviesToFilmEntity(initial)
    }
}