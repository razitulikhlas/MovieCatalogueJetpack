package com.razit.moviecatalogue.adapter

import com.razit.moviecatalogue.data.FilmEntity

interface MoviesCallback {
    fun onClick(movies: FilmEntity)

}