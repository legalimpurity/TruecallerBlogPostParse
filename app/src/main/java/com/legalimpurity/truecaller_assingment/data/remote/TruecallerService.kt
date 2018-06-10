package com.legalimpurity.truecaller_assingment.data.remote

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface TruecallerService {

    // https://blog.truecaller.com/2018/01/22/life-as-an-android-engineer/
//    @get:GET("{year}/{month}/{date}/{title}")
//    fun getResponse(@Path(value = "year", encoded = true) String year): Single<String>
    @get:GET("2018/01/22/life-as-an-android-engineer/")
    val getBlogPostResponse: Single<String>
}