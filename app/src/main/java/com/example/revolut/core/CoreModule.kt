package com.example.revolut.core

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

const val baseUrl = "https://revolut.duckdns.org/latest"

val coreModule = module {
    single<Moshi> {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    single {
        MoshiConverterFactory.create(get())
    }

    single {
        OkHttpClient
            .Builder()
            .build()
    }

    single {
        Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(get<MoshiConverterFactory>())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(get())
            .build()
    }

    single<ApiServices> {
        get<Retrofit>().create(ApiServices::class.java)
    }

}