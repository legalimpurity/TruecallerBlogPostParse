package com.legalimpurity.truecaller_assingment.data.remote

import io.reactivex.Single

interface ApiDataHelper
{
    fun getBlogPostResponse(year: String, month: String, date: String, title:String): Single<String>
}