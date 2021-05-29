package com.razit.moviecatalogue.data.remote

class RemoteDataSourceImp(private var filmService: FilmService) : BaseApi() {
    suspend fun getMovies() = safeApiCall {
        filmService.getMovies()
    }

    suspend fun getTvShow() = safeApiCall {
        filmService.getTvShow()
    }

    suspend fun getDetailMovies(id: Int) = safeApiCall {
        filmService.getDetailMovies(id)
    }

    suspend fun getDetailTvSHow(id: Int) = safeApiCall {
        filmService.getDetailTvShow(id)
    }

}