package com.gureev.omdbapp.utils.di.modules

import android.content.Context
import androidx.room.Room
import com.gureev.omdbapp.repository.MainRepository
import com.gureev.omdbapp.repository.room.MovieDatabase
import com.gureev.omdbapp.repository.room.TypeConverterForRoom
import com.gureev.omdbapp.repository.source.DiskDataSource
import com.gureev.omdbapp.repository.source.NetworkDataSource
import com.gureev.omdbapp.utils.AppConfig
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule(private var applicationContext: Context) {

    @Provides
    @Singleton
    fun provideApplicationContext(): Context {
        return applicationContext
    }

    @Provides
    @Singleton
    fun provideMainRepository(
        diskDataSource: DiskDataSource,
        networkDataSource: NetworkDataSource
    ): MainRepository {
        return MainRepository(diskDataSource, networkDataSource)
    }

    @Provides
    fun provideMovieDatabase(): MovieDatabase {
        return Room.databaseBuilder(
            applicationContext,
            MovieDatabase::class.java, "movie-database"
        )
            .addTypeConverter(TypeConverterForRoom())
            .build()
    }

    @Provides
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AppConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
    }
}