package com.legalimpurity.truecaller_assingment.data.remote

import io.reactivex.Single
import retrofit2.Retrofit
import javax.inject.Inject

class ApiDataHelperImpl @Inject constructor(retrofit: Retrofit): ApiDataHelper
{

    private var truecallerService: TruecallerService? = null

    init {
        truecallerService = retrofit.create(TruecallerService::class.java)
    }

    override fun getBlogPostResponse(year: String, month: String, date: String, title:String): Single<String> {
        return truecallerService!!.getBlogPostResponse(year,month,date, title)
    }
}