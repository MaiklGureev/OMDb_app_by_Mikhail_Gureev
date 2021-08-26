package com.gureev.omdbapp.repository.source

import android.util.Log
import com.gureev.omdbapp.repository.entities.MovieFull
import com.gureev.omdbapp.repository.entities.SearchResult
import com.gureev.omdbapp.repository.retrofit.MovieMapperForRetrofitResponse
import com.gureev.omdbapp.repository.retrofit.MovieOMDbDao
import io.reactivex.Single
import javax.inject.Inject

const val TAG = "NetworkDataSource"

class NetworkDataSource @Inject constructor(
    private val mapper: MovieMapperForRetrofitResponse,
    private val oMDbDaoMovie: MovieOMDbDao
) {

    fun getMovieById(id: String): Single<MovieFull> = oMDbDaoMovie.getByIMDbID(id)
        .map { mapper.fromRetrofitResponseToMovie(it) }

    fun getSearchResultsByQuery(query: String, page: Int): Single<SearchResult> =
        oMDbDaoMovie.getBySearchQuery(query, page.toString()).map {
            Log.d(TAG, "getSearchResultsByQuery: $it")
            mapper.fromSearchResponseToSearchResult(it)
        }

}

