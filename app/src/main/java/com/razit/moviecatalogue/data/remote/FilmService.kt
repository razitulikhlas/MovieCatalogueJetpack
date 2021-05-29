package com.razit.moviecatalogue.data.remote

import com.razit.moviecatalogue.BuildConfig
import com.razit.moviecatalogue.data.response.ResponseDetailMovies
import com.razit.moviecatalogue.data.response.ResponseDetailTvShow
import com.razit.moviecatalogue.data.response.ResponseMovies
import com.razit.moviecatalogue.data.response.ResponseTvShow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FilmService {
    @GET("movie/popular")
    suspend fun getMovies(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): ResponseMovies

    @GET("tv/popular")
    suspend fun getTvShow(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): ResponseTvShow

    @GET("tv/{id}")
    suspend fun getDetailTvShow(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): ResponseDetailTvShow

    @GET("movie/{id}")
    suspend fun getDetailMovies(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): ResponseDetailMovies
}