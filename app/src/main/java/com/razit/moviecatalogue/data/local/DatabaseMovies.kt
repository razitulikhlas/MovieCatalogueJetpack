package com.razit.moviecatalogue.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.razit.moviecatalogue.data.FilmEntity
import com.razit.moviecatalogue.data.RemoteKey


@Database(entities = [FilmEntity::class,RemoteKey::class], version = 1,exportSchema = false)
abstract class DatabaseMovies : RoomDatabase() {
    abstract val moviesDao: MoviesDao
}
