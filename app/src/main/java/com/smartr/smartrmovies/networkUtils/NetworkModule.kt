package com.smartr.smartrmovies.networkUtils

import androidx.room.Room
import com.smartr.smartrmovies.MovieApplication
import com.smartr.smartrmovies.constant.Constant
import com.smartr.smartrmovies.repository.MoviesDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideClient() : OkHttpClient {

        val loginInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient
            .Builder()
            .addInterceptor(Interceptor { chain ->
             val builder = chain.request().newBuilder()
                HttpLoggingInterceptor.Level.BODY
                builder.header("Authorization", "Bearer " +
                        "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxYzkxN2ZjMWIxMTgxNTY4NDJiMzYy" +
                        "N2ZmODEwY2Q4YyIsInN1YiI6IjVhYTAxZWQzMGUwYTI2MzI3YTAxMGEzMiIsInNj" +
                        "b3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.W5PptCGyk0S9lHOADzCZR2" +
                        "dD1ZrKHspNlSLrmvsexGE")
                return@Interceptor chain.proceed(builder.build())
            })
            .addInterceptor(loginInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun providesRetrofit() : Retrofit{
        return Retrofit.Builder().baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideClient())
            .build()
    }

    @Singleton
    @Provides
    fun movieApi(retrofit: Retrofit) : ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }

    fun provideDatabase(movieApplication: MovieApplication) : MoviesDataBase =
        Room.databaseBuilder(movieApplication, MoviesDataBase::class.java, "movies_database").build()

}