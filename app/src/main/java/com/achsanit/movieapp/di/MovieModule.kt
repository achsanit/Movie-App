package com.achsanit.movieapp.di

import com.achsanit.movieapp.BuildConfig
import com.achsanit.movieapp.data.MovieRepository
import com.achsanit.movieapp.data.service.MovieService
import com.achsanit.movieapp.ui.fragment.detailcast.DetailCastViewModel
import com.achsanit.movieapp.ui.fragment.detailmovie.DetailMovieViewModel
import com.achsanit.movieapp.ui.fragment.home.HomeViewModel
import com.achsanit.movieapp.utils.CustomInterceptor
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val mainModule = module {
    single { CustomInterceptor() }

    single {
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(get<CustomInterceptor>())
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)

        // add chucker interceptor if apps is debug variant
        if (BuildConfig.DEBUG) {
            client.addInterceptor(
                ChuckerInterceptor.Builder(androidContext())
                    .collector(ChuckerCollector(androidContext()))
                    .maxContentLength(250000L)
                    .redactHeaders(emptySet())
                    .alwaysReadResponseBody(true)
                    .build()
            )
        }

        client.build()
    }

    single {
        // instance the service
        val baseUrl = "${BuildConfig.BASE_TMDB_API}${BuildConfig.API_VERSION}/"
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()

        retrofit.create(MovieService::class.java)
    }

    single { MovieRepository(get()) }

    viewModel { HomeViewModel(get()) }
    viewModel { DetailMovieViewModel(get()) }
    viewModel { DetailCastViewModel(get()) }
}