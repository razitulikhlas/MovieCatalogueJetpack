package com.razit.moviecatalogue.repository

import com.razit.moviecatalogue.data.remote.Resource
import com.razit.moviecatalogue.data.response.ResponseDetailMovies
import com.razit.moviecatalogue.data.response.ResponseDetailTvShow
import com.razit.moviecatalogue.data.response.ResponseMovies
import com.razit.moviecatalogue.data.response.ResponseTvShow

interface RepositoryMovies {
    suspend fun getMovies(): Resource<ResponseMovies>
    suspend fun getTvShow(): Resource<ResponseTvShow>
    suspend fun getDetailMovies(idMovies: Int): Resource<ResponseDetailMovies>
    suspend fun getDetailTvShow(idTvShow: Int): Resource<ResponseDetailTvShow>
}