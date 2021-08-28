package com.gureev.omdbapp.fragments.search

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gureev.omdbapp.App
import com.gureev.omdbapp.repository.MainRepository
import com.gureev.omdbapp.repository.entities.MovieShort
import com.gureev.omdbapp.repository.entities.SearchResult
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

const val TAG = "SearchMoviesViewModel"

class SearchMoviesViewModel : ViewModel() {

    init {
        App.appComponent.inject(this)
    }

    @Inject
    lateinit var repository: MainRepository

    private var currentQuery: String = ""
    private var currentMaxPage: Int = 1
    private var currentPage: Int = 1

    var movieShortListLiveData: MutableLiveData<List<MovieShort>> =
        MutableLiveData<List<MovieShort>>(emptyList())

    private fun getSearchResultByQuerySingle(query: String, page: Int) =
        repository.networkDataSource.getSearchResultsByQuery(
            query,
            page
        ).onErrorReturn {
            SearchResult(emptyList(), 0, false, "Error!")
        }.doOnSuccess { searchResult ->
            Log.d(TAG, "doOnSuccess: $searchResult")
            setValueToMovieShortListSubject(searchResult)
        }.subscribeOn(Schedulers.io())


    fun search(query: String, page: Int) {
        Log.d(TAG, "search: ${query}, ${page}")

        if (page > currentMaxPage) return

        if (!currentQuery.equals(query)) {
            currentPage = 1
        }

        currentQuery = query
        getSearchResultByQuerySingle(currentQuery, currentPage).subscribe()
    }

    fun nextPage() {
        currentPage++
        search(currentQuery, currentPage)
    }

    fun prevPage() {
        if (currentPage == 1) return
        currentPage--
        search(currentQuery, currentPage)
    }

    private fun setValueToMovieShortListSubject(searchResult: SearchResult) {
        if (searchResult.response) {
            movieShortListLiveData.postValue(searchResult.movieShortResponseList)
            currentMaxPage = searchResult.totalResults
        } else {
            movieShortListLiveData.postValue(emptyList())
            Log.d(TAG, "search: $searchResult")
        }
    }

}