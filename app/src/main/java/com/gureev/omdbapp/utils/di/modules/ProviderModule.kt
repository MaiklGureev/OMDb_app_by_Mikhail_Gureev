package com.gureev.omdbapp.utils.di.modules

import com.gureev.omdbapp.repository.retrofit.MovieMapperForRetrofitResponse
import com.gureev.omdbapp.repository.retrofit.MovieOMDbDao
import com.gureev.omdbapp.repository.room.MovieDatabase
import com.gureev.omdbapp.repository.room.MovieMapperForRoom
import com.gureev.omdbapp.repository.source.DiskDataSource
import com.gureev.omdbapp.repository.source.NetworkDataSource
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ProviderModule() {

    @Provides
    @Singleton
    fun provideDiskDataSource(
        db: MovieDatabase,
        mapper: MovieMapperForRoom
    ): DiskDataSource {
        return DiskDataSource(db, mapper)
    }

    @Provides
    @Singleton
    fun provideNetworkDataSource(
        mapper: MovieMapperForRetrofitResponse,
        oMDbDaoMovie: MovieOMDbDao
    ): NetworkDataSource {
        return NetworkDataSource(mapper, oMDbDaoMovie)
    }

    @Provides
    fun provideOkHttpClient(logging: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(logging).build()
    }


    @Provides
    fun provideMovieOMDbDao(retrofit: Retrofit): MovieOMDbDao {
        return retrofit.create(MovieOMDbDao::class.java)
    }

    @Provides
    fun provideMovieMapperForRoom(): MovieMapperForRoom {
        return MovieMapperForRoom()
    }

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
    }

    @Provides
    fun provideMovieMapperForRetrofitResponse(): MovieMapperForRetrofitResponse {
        return MovieMapperForRetrofitResponse()
    }


}