package com.razit.moviecatalogue.data

data class FilmEntity(
    var id: Int,
    var title: String,
    var description: String,
    var imageUrl: Int,
    var release: String,
    var director: String,
    var genre:String
)