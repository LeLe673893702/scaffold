package com.newler.scaffold.common.config.modlue

import android.app.Application
import android.content.Context
import androidx.annotation.Nullable
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 *
 * @what
 * @author newler
 * @date 2020/1/8
 *
 */

class NetWorkModule {
    fun provideRetrofit(@Nullable application: Application,
                        @Nullable retrofitConfiguration: RetrofitConfiguration?,
                        @Nullable retrofitBuilder: Retrofit.Builder,
                        @Nullable okHttpClient: OkHttpClient,
                        @Nullable baseUrl: HttpUrl?,
                        gson: Gson) : Retrofit {
        baseUrl.run {
            this?.let {
                return@run  retrofitBuilder.baseUrl(it)
            }
            return@run retrofitBuilder
        }.client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
        retrofitConfiguration?.config(application, retrofitBuilder)
        return retrofitBuilder.build()
    }

    fun provideOkHttpClient(@Nullable application: Application,
                            @Nullable okHttpClientConfiguration: OkHttpClientConfiguration?,
                            @Nullable okHttpClientBuilder: OkHttpClient.Builder): OkHttpClient {
        okHttpClientConfiguration?.config(application, okHttpClientBuilder)
        return okHttpClientBuilder.build()
    }


    fun provideGson(@Nullable application: Application, gsonConfiguration: GsonConfiguration?) : Gson{
        val gsonBuilder = GsonBuilder()
        gsonConfiguration?.config(application, gsonBuilder)
        return gsonBuilder.create()
    }

    interface RetrofitConfiguration {
        fun config(@Nullable context:Context, @Nullable builder:Retrofit.Builder)
    }

    interface OkHttpClientConfiguration{
        fun config(@Nullable context:Context, @Nullable builder: OkHttpClient.Builder)
    }

    interface GsonConfiguration {
        fun config(context:Context, gsonBuilder: GsonBuilder)
    }
}