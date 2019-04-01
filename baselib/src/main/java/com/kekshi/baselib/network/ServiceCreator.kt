package com.kekshi.baselib.network

import com.kekshi.baselib.network.convert.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

object ServiceCreator {

    private const val BASE_URL = "http://api.jiasu.hongxingdun.com/"

    private val httpClient = OkHttpClient
        .Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
//        .sslSocketFactory(TrustAllCerts.setCertificates(), TrustAllCerts())
//        .hostnameVerifier(TrustAllCerts.TrustAllHostnameVerifier())

    private val builder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient.build())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())

    private val retrofit = builder.build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

}