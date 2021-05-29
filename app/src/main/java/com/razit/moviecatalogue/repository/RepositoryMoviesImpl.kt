package com.razit.moviecatalogue.repository

import com.razit.moviecatalogue.data.remote.RemoteDataSourceImp
import com.razit.moviecatalogue.data.remote.Resource
import com.razit.moviecatalogue.data.response.ResponseDetailMovies
import com.razit.moviecatalogue.data.response.ResponseDetailTvShow
import com.razit.moviecatalogue.data.response.ResponseMovies
import com.razit.moviecatalogue.data.response.ResponseTvShow



class RepositoryMoviesImpl(private var remoteDataSource: RemoteDataSourceImp) : RepositoryMovies {
    override suspend fun getMovies(): Resource<ResponseMovies> {
        return remoteDataSource.getMovies()
    }

    override suspend fun getTvShow(): Resource<ResponseTvShow> {
        return remoteDataSource.getTvShow()
    }

    override suspend fun getDetailMovies(idMovies:Int): Resource<ResponseDetailMovies> {
       return remoteDataSource.getDetailMovies(idMovies)
    }

    override suspend fun getDetailTvShow(idTvShow:Int): Resource<ResponseDetailTvShow> {
        return  remoteDataSource.getDetailTvSHow(idTvShow)
    }

}