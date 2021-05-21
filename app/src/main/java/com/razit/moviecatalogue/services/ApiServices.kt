package com.razit.moviecatalogue.services


import com.razit.moviecatalogue.services.model.ResponseMovies
import com.razit.moviecatalogue.services.model.ResponseTvShow
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {

    @GET("movie/popular")
    fun getMovie(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Call<ResponseMovies>

    @GET("tv/popular")
    fun getTvShow(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Call<ResponseTvShow>
}