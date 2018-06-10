package com.legalimpurity.truecaller_assingment.data.remote

import io.reactivex.Single

interface ApiDataHelper
{
    fun getBlogPostResponse(year: Int, month: Int, date: Int, title:String): Single<String>
}