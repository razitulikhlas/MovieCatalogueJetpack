package com.razit.moviecatalogue.repository

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.razit.moviecatalogue.BuildConfig
import com.razit.moviecatalogue.data.FilmEntity
import com.razit.moviecatalogue.data.local.MoviesDao
import com.razit.moviecatalogue.data.maping.NetworkMapperMovies
import com.razit.moviecatalogue.data.remote.FilmService
import com.razit.moviecatalogue.data.remote.RemoteDataSourceImp
import com.razit.moviecatalogue.data.remote.Resource
import com.razit.moviecatalogue.data.response.ResponseDetailMovies
import com.razit.moviecatalogue.data.response.ResponseDetailTvShow
import com.razit.moviecatalogue.paging.MoviesPagingSource
import com.razit.moviecatalogue.paging.MoviesRemoteMediator
import com.razit.moviecatalogue.paging.TvShowPagingSource
import kotlinx.coroutines.flow.Flow
import okhttp3.internal.wait
import java.util.concurrent.Callable
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class RepositoryMoviesImpl(
    private val remoteDataSource: RemoteDataSourceImp,
    private val filmService: FilmService,
    private val networkMapperMovies: NetworkMapperMovies,
    private val dao: MoviesDao
) : RepositoryMovies {
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    override fun getMoviesPage(): Flow<PagingData<FilmEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 20, maxSize = 200, enablePlaceholders = true),
            pagingSourceFactory = {
                MoviesPagingSource(filmService, networkMapperMovies)
            }).flow
    }

    override fun getTvShowPage(): Flow<PagingData<FilmEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 20, maxSize = 200, enablePlaceholders = true),
            pagingSourceFactory = {
                TvShowPagingSource(filmService, networkMapperMovies)
            }).flow
    }

    override suspend fun getDetailMovies(idMovies: Int): Resource<ResponseDetailMovies> {
        return remoteDataSource.getDetailMovies(idMovies)
    }

    override suspend fun getDetailTvShow(idTvShow: Int): Resource<ResponseDetailTvShow> {
        return remoteDataSource.getDetailTvSHow(idTvShow)
    }

    fun saveToFavorite(filmEntity: FilmEntity) : Long {
        val status = executorService.submit(Callable { dao.insert(filmEntity) })
        return status.get()
    }

    fun deleteFilmFavorite(id: Int, type: String) {
        executorService.execute {
            dao.deleteByIdAndType(id, type)
        }

    }

    fun delete(filmEntity: FilmEntity):Int{
        val status = executorService.submit(Callable { dao.delete(filmEntity)})
        return status.get()
    }

    suspend fun isSave(id: Int): Boolean {
        return dao.isSave(id)
    }

    fun getMoviesFromLocal(type: String): LiveData<PagingData<FilmEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 20, maxSize = 200, enablePlaceholders = true),
            pagingSourceFactory = {
                dao.getAllFilmByType(type)
            }).liveData
    }

    @ExperimentalPagingApi
    fun getMoviesWithRemote(){
        val pager = Pager(
            config = PagingConfig(pageSize = 50),
            remoteMediator = MoviesRemoteMediator(networkMapperMovies,dao,filmService,1)
        ) {
            dao.getAllFilmByType(BuildConfig.MOVIES)
        }
    }

}