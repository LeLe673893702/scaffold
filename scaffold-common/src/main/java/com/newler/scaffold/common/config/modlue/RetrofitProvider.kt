package com.newler.scaffold.common.config.modlue

import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
 *
 * @what
 * @author 17173
 * @date 2020/1/14
 *
 */
class RetrofitProvider {
   lateinit var okHttpClient:OkHttpClient
   lateinit var retrofit: Retrofit
   lateinit var gson: Gson
    companion object {
        @JvmStatic
        val instance by lazy {
            RetrofitProvider()
        }
    }

    fun inject(okHttpClient:OkHttpClient, retrofit: Retrofit, gson: Gson) {
        this.gson = gson
        this.okHttpClient = okHttpClient
        this.retrofit = retrofit
    }
}