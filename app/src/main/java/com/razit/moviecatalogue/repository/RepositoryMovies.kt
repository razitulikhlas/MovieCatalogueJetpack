package com.razit.moviecatalogue.repository

import androidx.paging.PagingData
import com.razit.moviecatalogue.data.FilmEntity
import com.razit.moviecatalogue.data.remote.Resource
import com.razit.moviecatalogue.data.response.ResponseDetailMovies
import com.razit.moviecatalogue.data.response.ResponseDetailTvShow
import kotlinx.coroutines.flow.Flow

interface RepositoryMovies {
    fun getMoviesPage(): Flow<PagingData<FilmEntity>>
    fun getTvShowPage(): Flow<PagingData<FilmEntity>>
    suspend fun getDetailMovies(idMovies: Int): Resource<ResponseDetailMovies>
    suspend fun getDetailTvShow(idTvShow: Int): Resource<ResponseDetailTvShow>
}