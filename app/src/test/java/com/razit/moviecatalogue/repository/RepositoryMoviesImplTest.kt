package com.razit.moviecatalogue.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.verify
import com.razit.moviecatalogue.data.maping.NetworkMapperMovies
import com.razit.moviecatalogue.data.remote.RemoteDataSourceImp
import com.razit.moviecatalogue.data.remote.Resource
import com.razit.moviecatalogue.data.response.ResponseMovies
import com.razit.moviecatalogue.data.response.ResponseTvShow
import com.razit.moviecatalogue.utils.DataFilm
import junit.framework.TestCase.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock


class RepositoryMoviesImplTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val networkMapperMovies: NetworkMapperMovies = NetworkMapperMovies()

    private val remote = mock(RemoteDataSourceImp::class.java)

    private val moviesRepository = FakeRepository(remote)

    private val movies = DataFilm.generateDummyMovies()
    private val idMovies = movies[0].id
    private val detailMovies = movies[0]

    private val tvShow = DataFilm.generateDummyTvShow()
    private val idTvShow = tvShow[0].id
    private val detailTvShow = tvShow[0]

    @ExperimentalCoroutinesApi
    @Test
    fun getMovies() = runBlockingTest {
        `when`(remote.getMovies()).thenReturn(
            Resource.Success(
                ResponseMovies(
                    page = 1,
                    totalPages = 2,
                    results = networkMapperMovies.fromFilmEntityToMovies(movies),
                    10
                )
            )
        )
        when (val data = moviesRepository.getMovies()) {
            is Resource.Success -> {
                assertNotNull(data.value)
                assertEquals(movies.size.toLong(), data.value.results?.size?.toLong())
            }
            is Resource.Failure -> {
                assertNull(data.isNetworkError)
            }
        }
        verify(remote).getMovies()
    }

    @Test
    @ExperimentalCoroutinesApi
    fun getTvShow() = runBlockingTest {
        `when`(remote.getTvShow()).thenReturn(
            Resource.Success(
                ResponseTvShow(1, 1, results = networkMapperMovies.fromFilmToTvShow(tvShow))
            )
        )
        when (val data = moviesRepository.getTvShow()) {
            is Resource.Success -> {
                assertNotNull(data.value)
                assertEquals(tvShow.size.toLong(), data.value.results?.size?.toLong())
            }
            is Resource.Failure -> {
                assertNull(data.isNetworkError)
            }
        }
        verify(remote).getTvShow()
    }

    @Test
    @ExperimentalCoroutinesApi
    fun getDetailMovies() = runBlockingTest {
        `when`(remote.getDetailMovies(idMovies!!)).thenReturn(
            Resource.Success(
                networkMapperMovies.fromFilmToDetailMovies(
                    detailMovies
                )
            )
        )

        when (val data = moviesRepository.getDetailMovies(idMovies)) {
            is Resource.Success -> {
                assertNotNull(data.value)
                assertEquals(detailMovies.id, idMovies)
            }
            is Resource.Failure -> {
                assertNull(data.isNetworkError)
            }
        }
        verify(remote).getDetailMovies(idMovies)
    }

    @Test
    @ExperimentalCoroutinesApi
    fun getDetailTvShow() = runBlockingTest {
        `when`(remote.getDetailTvSHow(idTvShow!!)).thenReturn(
            Resource.Success(
                networkMapperMovies.fromFilmToTvShow(
                    detailTvShow
                )
            )
        )

        when (val data = moviesRepository.getDetailTvShow(idTvShow)) {
            is Resource.Success -> {
                assertNotNull(data.value)
                assertEquals(detailTvShow.id, idTvShow)
            }
            is Resource.Failure -> {
                assertNull(data.isNetworkError)
            }
        }
        verify(remote).getDetailTvSHow(idTvShow)
    }


}