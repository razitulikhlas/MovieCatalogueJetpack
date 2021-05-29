package com.razit.moviecatalogue

import android.app.Application
import com.razit.moviecatalogue.data.maping.NetworkMapperMovies
import com.razit.moviecatalogue.data.remote.ApiClient
import com.razit.moviecatalogue.data.remote.RemoteDataSourceImp
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
                    repositoryModule
                )
            )
        }
    }
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
    factory { RepositoryMoviesImpl(get()) }
}