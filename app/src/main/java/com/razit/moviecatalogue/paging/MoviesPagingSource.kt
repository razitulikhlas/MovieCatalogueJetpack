package com.razit.moviecatalogue.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.razit.moviecatalogue.data.FilmEntity
import com.razit.moviecatalogue.data.local.MoviesDao
import com.razit.moviecatalogue.data.maping.NetworkMapperMovies
import com.razit.moviecatalogue.data.remote.FilmService
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MoviesPagingSource(
    private val service: FilmService,
    private val networkMapperMovies: NetworkMapperMovies,
) : PagingSource<Int, FilmEntity>() {
    override fun getRefreshKey(state: PagingState<Int, FilmEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FilmEntity> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = service.getMoviesPage(page = nextPageNumber)
            val mapMoviesToFilmEntity = response.results?.let {
                networkMapperMovies.fromMoviesToFilmEntity(it)
            }

            LoadResult.Page(
                data = mapMoviesToFilmEntity!!,
                prevKey = null,
                nextKey = response.page!! + 1
            )
        } catch (e: IOException) {
            // IOException for network failures.
            LoadResult.Error(e)
        } catch (e: HttpException) {
            // HttpException for any non-2xx HTTP status codes.
            LoadResult.Error(e)
        }
    }

}