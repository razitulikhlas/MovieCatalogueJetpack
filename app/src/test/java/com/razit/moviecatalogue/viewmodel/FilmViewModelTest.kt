package com.razit.moviecatalogue.viewmodel
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.razit.moviecatalogue.data.FilmEntity
import com.razit.moviecatalogue.data.maping.NetworkMapperMovies
import com.razit.moviecatalogue.data.remote.Resource
import com.razit.moviecatalogue.data.response.ResponseMovies
import com.razit.moviecatalogue.data.response.ResponseTvShow
import com.razit.moviecatalogue.repository.RepositoryMoviesImpl
import com.razit.moviecatalogue.utils.DataFilm
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner.Silent::class)
class FilmViewModelTest {

    private lateinit var viewModel: FilmViewModel

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val networkMapperMovies: NetworkMapperMovies = NetworkMapperMovies()

    @Mock
    private lateinit var filmRepository: RepositoryMoviesImpl

    @Mock
    private lateinit var observer: Observer<List<FilmEntity>>

    @Mock
    private lateinit var observerDetail: Observer<FilmEntity>

    @Before
    fun setUp() {
        viewModel = FilmViewModel(filmRepository, networkMapperMovies)

    }


    @ExperimentalCoroutinesApi
    @Test
    fun getMovies() = runBlockingTest {
        val dataFilm = DataFilm.generateDummyMovies()
        val movies = MutableLiveData<List<FilmEntity>>()
        movies.value = dataFilm
        `when`(filmRepository.getMovies()).thenReturn(
            Resource.Success(
                ResponseMovies(
                    page = 1,
                    totalPages = 2,
                    results = networkMapperMovies.fromFilmEntityToMovies(dataFilm),
                    10
                )
            )
        )
        GlobalScope.launch {
            viewModel.getMovies()
            val data = viewModel.film.value
            assertNotNull(data)
            assertEquals(dataFilm.size, data?.size)
            verify(filmRepository).getMovies()

            viewModel.film.observeForever(observer)
            verify(observer).onChanged(dataFilm)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getTvShow() = runBlockingTest {
        val dataTvShow = DataFilm.generateDummyTvShow()
        val tvShow = MutableLiveData<List<FilmEntity>>()
        tvShow.value = dataTvShow
        `when`(filmRepository.getTvShow()).thenReturn(
            Resource.Success(
                ResponseTvShow(
                    1,
                    1,
                    results = networkMapperMovies.fromFilmToTvShow(dataTvShow)
                )
            )
        )
        GlobalScope.launch {
            viewModel.getTvShow()
            val data = viewModel.film.value
            assertNotNull(data)
            assertEquals(dataTvShow.size, data?.size)
            verify(filmRepository).getMovies()
            viewModel.film.observeForever(observer)
            verify(observer).onChanged(dataTvShow)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getDetailTvShow() = runBlockingTest {
        val dataTvShow = DataFilm.generateDummyTvShow()[0]
        val tvShow = MutableLiveData<FilmEntity>()
        tvShow.value = dataTvShow
        GlobalScope.launch {
            `when`(filmRepository.getDetailTvShow(dataTvShow.id!!)).thenReturn(
                Resource.Success(
                    networkMapperMovies.fromFilmToTvShow(
                        dataTvShow
                    )
                )
            )
            viewModel.getDetailTvShow(dataTvShow.id!!)
            val data = viewModel.detailFilm.value
            assertNotNull(data)
            assertEquals(dataTvShow.id, data?.id)
            assertEquals(dataTvShow.title, data?.title)
            assertEquals(dataTvShow.description, data?.description)
            assertEquals(dataTvShow.genre, data?.genre)
            assertEquals(dataTvShow.imageUrl, data?.imageUrl)
            assertEquals(dataTvShow.release, data?.release)
            assertEquals(dataTvShow.type, data?.type)
            verify(filmRepository).getDetailTvShow(dataTvShow.id!!)
            viewModel.detailFilm.observeForever(observerDetail)
            verify(observerDetail).onChanged(dataTvShow)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun getDetailMovies() = runBlockingTest {
        val dataMovies = DataFilm.generateDummyMovies()[0]
        val movies = MutableLiveData<FilmEntity>()
        movies.value = dataMovies
        GlobalScope.launch {
            `when`(filmRepository.getDetailMovies(dataMovies.id!!)).thenReturn(
                Resource.Success(
                    networkMapperMovies.fromFilmToDetailMovies(
                        dataMovies
                    )
                )
            )
            viewModel.getDetailTvShow(dataMovies.id!!)
            val data = viewModel.detailFilm.value
            assertNotNull(data)
            assertEquals(dataMovies.id, data?.id)
            assertEquals(dataMovies.title, data?.title)
            assertEquals(dataMovies.description, data?.description)
            assertEquals(dataMovies.genre, data?.genre)
            assertEquals(dataMovies.imageUrl, data?.imageUrl)
            assertEquals(dataMovies.release, data?.release)
            assertEquals(dataMovies.type, data?.type)
            verify(filmRepository).getDetailTvShow(dataMovies.id!!)
            viewModel.detailFilm.observeForever(observerDetail)
            verify(observerDetail).onChanged(dataMovies)
        }
    }
}