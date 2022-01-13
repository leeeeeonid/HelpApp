package com.lealpy.simbirsoft_training

import android.app.Application
import com.lealpy.simbirsoft_training.data.EventApi
import com.lealpy.simbirsoft_training.data.HelpApi
import com.lealpy.simbirsoft_training.data.NewsApi
import com.lealpy.simbirsoft_training.data.NkoApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class HelpApp : Application() {

    lateinit var helpApi : HelpApi
    lateinit var newsApi : NewsApi
    lateinit var eventApi : EventApi
    lateinit var nkoApi : NkoApi

    init {
        configureRetrofit()
    }

    private fun configureRetrofit() {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHtpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(RETROFIT_BASE_URL)
            .client(okHtpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        helpApi = retrofit.create(HelpApi::class.java)
        newsApi = retrofit.create(NewsApi::class.java)
        eventApi = retrofit.create(EventApi::class.java)
        nkoApi = retrofit.create(NkoApi::class.java)
    }

    companion object {
        private const val RETROFIT_BASE_URL = "https://help-app.getsandbox.com"
    }
}