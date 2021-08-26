package com.gureev.omdbapp.fragments.favorites

import androidx.lifecycle.ViewModel
import com.gureev.omdbapp.App
import com.gureev.omdbapp.repository.MainRepository
import javax.inject.Inject

class FavoriteMoviesViewModel : ViewModel() {

    init {
        App.appComponent.inject(this)
    }

    @Inject
    lateinit var repository: MainRepository

    fun getFromRoomListMovieShortObservable() =
        repository.diskDataSource.listOfFavoriteMovies
}
