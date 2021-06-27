package com.razit.moviecatalogue

import android.app.Application
import androidx.room.Room
import com.razit.moviecatalogue.data.local.DatabaseMovies
import com.razit.moviecatalogue.data.maping.NetworkMapperMovies
import com.razit.moviecatalogue.data.remote.ApiClient
import com.razit.moviecatalogue.data.remote.RemoteDataSourceImp
import com.razit.moviecatalogue.paging.MoviesPagingSource
import com.razit.moviecatalogue.repository.RepositoryMoviesImpl
import com.razit.moviecatalogue.viewmodel.FilmViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MyApp)
            modules(
                listOf(
                    retrofitModule,
                    remoteDataSourceModule,
                    mapperMoviesModule,
                    vieModelModule,
                    repositoryModule,
                    moviesPagingSourceModule,
                    databaseModule
                )
            )
        }
    }
}

val databaseModule = module {
    single { Room.databaseBuilder(androidContext(), DatabaseMovies::class.java, "movies_database")
        .fallbackToDestructiveMigration()
        .build()}

    factory {
        get<DatabaseMovies>().moviesDao
    }
//    single { provideMoviesDao(get()) }
}

val retrofitModule = module {
    single { ApiClient.getServices }
}

val mapperMoviesModule = module {
    factory { NetworkMapperMovies() }
}

val vieModelModule = module {
    viewModel { FilmViewModel(get(), get()) }
}

val remoteDataSourceModule = module {
    factory { RemoteDataSourceImp(get()) }
}

val repositoryModule = module {
    factory { RepositoryMoviesImpl(get(),get(),get(),get()) }
}

val moviesPagingSourceModule = module {
    factory { MoviesPagingSource(get(),get()) }
}
