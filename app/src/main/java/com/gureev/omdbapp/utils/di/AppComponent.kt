package com.gureev.omdbapp.utils.di


import com.gureev.omdbapp.MainActivity
import com.gureev.omdbapp.fragments.favorites.FavoriteMoviesViewModel
import com.gureev.omdbapp.fragments.movie.MovieViewModel
import com.gureev.omdbapp.fragments.search.SearchMoviesViewModel
import com.gureev.omdbapp.repository.MainRepository
import com.gureev.omdbapp.repository.source.DiskDataSource
import com.gureev.omdbapp.repository.source.NetworkDataSource
import com.gureev.omdbapp.utils.di.modules.AppModule
import com.gureev.omdbapp.utils.di.modules.ProviderModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, ProviderModule::class])
@Singleton
interface AppComponent {
    fun inject(activity: MainActivity)
    fun inject(mainRepository: MainRepository)
    fun inject(diskDataSource: DiskDataSource)
    fun inject(networkDataSource: NetworkDataSource)
    fun inject(favoriteMoviesViewModel: FavoriteMoviesViewModel)
    fun inject(movieViewModel: MovieViewModel)
    fun inject(searchMoviesViewModel: SearchMoviesViewModel)
}