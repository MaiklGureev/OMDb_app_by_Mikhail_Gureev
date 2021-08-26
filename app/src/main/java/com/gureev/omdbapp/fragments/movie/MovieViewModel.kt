package com.gureev.omdbapp.fragments.movie

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gureev.omdbapp.App
import com.gureev.omdbapp.repository.MainRepository
import com.gureev.omdbapp.repository.entities.MovieFull
import com.gureev.omdbapp.repository.entities.MovieShort
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

const val TAG = "MovieViewModel"

class MovieViewModel : ViewModel() {

    init {
        App.appComponent.inject(this)
    }

    @Inject
    lateinit var repository: MainRepository

    var movieFull: MutableLiveData<MovieFull> =
        MutableLiveData<MovieFull>(MovieFull())

    var isFavoriteMovie: MutableLiveData<Boolean> =
        MutableLiveData<Boolean>(false)

    private fun getMovieFullSingle(id: String) =
        repository.networkDataSource.getMovieById(id)

    private fun getCachedMovieFullSingle(id: String) =
        repository.diskDataSource.getMovieFullFromCache(id)

    private fun insertToCacheMovieFull(movieFull: MovieFull) =
        repository.diskDataSource.insertMovieFullToCache(movieFull)
            .subscribeOn(Schedulers.io())
            .subscribe()

    private fun getFavoriteMovie(id: String) = repository.diskDataSource.getFavoriteMovieShort(id)
        .onErrorReturn {
            MovieShort()
        }
        .doOnSuccess {
            if (it.imdbID.equals("")) {
                isFavoriteMovie.postValue(false)
            } else {
                isFavoriteMovie.postValue(true)
            }
        }

    fun checkIsFavoriteMovie(id: String): Completable =
        Completable.fromSingle(
            getFavoriteMovie(id)
        )
            .onErrorResumeNext {
                Log.d(TAG, "checkIsFavoriteMovie: onErrorResumeNext postValue false")

                Completable.complete()
            }
            .doOnComplete {
                Log.d(TAG, "checkIsFavoriteMovie: doOnComplete postValue true")
            }


    fun getFromMovieFullOmdbSingle(imdbID: String) = getMovieFullSingle(imdbID)
        .doOnError {
            Log.d(TAG, "getMovieFullSingle: doOnError ${it}")
        }
        .observeOn(AndroidSchedulers.mainThread())
        .onErrorReturn {
            Log.d(
                TAG,
                "getMovieFullSingle: onErrorReturn Can't load detail movie info from cache and network."
            )
            MovieFull()
        }
        .doOnSuccess { movieFull ->
            Log.d(TAG, "getMovieFullSingle: doOnSuccess read in omdb ")
            Log.d(TAG, "getMovieFullSingle: doOnSuccess $movieFull")
            Log.d(TAG, "getMovieFullSingle: doOnSuccess Success")
            this.movieFull.postValue(movieFull)
            insertToCacheMovieFull(movieFull)
            checkIsFavoriteMovie(movieFull.imdbID).subscribeOn(Schedulers.io()).subscribe()
        }
        .subscribeOn(Schedulers.io())

    fun movieFullSingle(imdbID: String) = getCachedMovieFullSingle(imdbID)
        .subscribeOn(Schedulers.io())
        .doOnSuccess { movieFull ->
            checkIsFavoriteMovie(movieFull.imdbID).subscribeOn(Schedulers.io()).subscribe()
            this.movieFull.postValue(movieFull)
        }
        .onErrorResumeNext {
            Log.d(
                TAG,
                "getCachedMovieFullSingle: onErrorResumeNext cant read in cache and load from omdb "
            )
            getFromMovieFullOmdbSingle(imdbID)
        }

    fun addOrDeleteMovieInFavorites() {
        movieFull.value?.let { movie ->

            isFavoriteMovie.value?.let { isFavoriteMovie ->

                if (isFavoriteMovie) {
                    repository.diskDataSource.insertMovieToFavorite(movie)
                        .subscribeOn(Schedulers.io())
                        .onErrorComplete {
                            Log.d(TAG, "insertMovieToFavorite onErrorComplete: $it")
                            true
                        }.subscribe {
                            Log.d(TAG, "insertMovieToFavorite onComplete")
                        }
                } else {
                    repository.diskDataSource.removeFavoriteMovie(movie.imdbID)
                        .subscribeOn(Schedulers.io())
                        .onErrorComplete {
                            Log.d(TAG, "removeFavoriteMovie onErrorComplete: $it")
                            true
                        }.subscribe {
                            Log.d(TAG, "removeFavoriteMovie onComplete")
                        }
                }
            }
        }
    }

    fun switchIsFavoriteMovieAndGetNewValue(): Boolean {
        val currentValue: Boolean = isFavoriteMovie.value ?: false
        isFavoriteMovie.postValue(!currentValue)
        return !currentValue
    }

    fun clearLiveData() {
        movieFull.postValue(MovieFull())
        isFavoriteMovie.postValue(false)
    }

}