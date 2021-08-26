package com.gureev.omdbapp.repository.retrofit

import com.gureev.omdbapp.repository.retrofit.response.MovieFullResponse
import com.gureev.omdbapp.repository.retrofit.response.SearchResponse
import com.gureev.omdbapp.utils.AppConfig
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

// "s" - MovieFull title to aShorts for.
// "i" - A valid IMDb ID (e.g. tt1285016).
// "page " - Page number to return. Default Value: 1.
// "plot"(short, full - Return short or full plot. Default Value: shor.

interface MovieOMDbDao {
    @GET("/?apikey=${AppConfig.API_KEY}&")
    fun getByIMDbID(@Query("i") id: String): Single<MovieFullResponse>

    @GET("/?apikey=${AppConfig.API_KEY}&")
    fun getBySearchQuery(
        @Query("s") query: String,
        @Query("page") page: String
    ): Single<SearchResponse>
}