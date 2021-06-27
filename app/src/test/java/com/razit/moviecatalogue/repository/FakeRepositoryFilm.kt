package com.razit.moviecatalogue.repository

import com.razit.moviecatalogue.data.remote.RemoteDataSourceImp

class FakeRepositoryFilm constructor(
    private val remoteDataSource: RemoteDataSourceImp,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors)
    : AcademyDataSource { {
}