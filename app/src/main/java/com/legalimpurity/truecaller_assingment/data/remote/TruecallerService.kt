package com.legalimpurity.truecaller_assingment.data.remote

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface TruecallerService {

    @GET("{year}/{month}/{date}/{title}")
    fun getBlogPostResponse(@Path(value = "year", encoded = true)year: Int, @Path(value = "month", encoded = true)month: Int, @Path(value = "date", encoded = true) date: Int, @Path(value = "title", encoded = true) title: String): Single<String>
}