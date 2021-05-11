package com.razit.moviecatalogue.viewmodel

import junit.framework.TestCase.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test


class FilmViewModelTest {

    private lateinit var filmViewModel: FilmViewModel

    @Before
    fun setUp(){
        filmViewModel = FilmViewModel()
    }

    @Test
    fun testGetMovies() {
        val listMovies = filmViewModel.getMovies()
        assertNotNull(listMovies)
        assertEquals(10, listMovies.size)
    }


    @Test
    fun testGetTvShow() {
        val listTvShow = filmViewModel.getTvShow()
        assertNotNull(listTvShow)
        assertEquals(10, listTvShow.size)
    }

    @Test
    fun testGetDetailMovies() {
        val listMovies = filmViewModel.getMovies()
        val movies = filmViewModel.getDetailMovies(listMovies[0].id)
        assertNotNull(movies)
        assertEquals(listMovies[0].id, movies.id)
        assertEquals(listMovies[0].release, movies.release)
        assertEquals(listMovies[0].director, movies.director)
        assertEquals(listMovies[0].description, movies.description)
        assertEquals(listMovies[0].genre, movies.genre)
        assertEquals(listMovies[0].imageUrl, movies.imageUrl)

    }

    @Test
    fun testGetDetailTvShow() {
        val listTvShow = filmViewModel.getTvShow()
        val tvShow = filmViewModel.getDetailTvShow(listTvShow[0].id)
        assertNotNull(tvShow)
        assertEquals(listTvShow[0].id, tvShow.id)
        assertEquals(listTvShow[0].release, tvShow.release)
        assertEquals(listTvShow[0].director, tvShow.director)
        assertEquals(listTvShow[0].description, tvShow.description)
        assertEquals(listTvShow[0].genre, tvShow.genre)
        assertEquals(listTvShow[0].imageUrl, tvShow.imageUrl)
    }


}