package com.legalimpurity.truecaller_assingment.data

import com.legalimpurity.truecaller_assingment.data.remote.ApiDataHelper
import io.reactivex.Single

interface DataManager : ApiDataHelper
{
    fun getBlogPostResponseAsCharArray(year: Int, month: Int, date: Int, title: String): Single<CharArray>
    fun getBlogPostResponseAsStringList(year: Int, month: Int, date: Int, title: String): Single<List<String>>
}
