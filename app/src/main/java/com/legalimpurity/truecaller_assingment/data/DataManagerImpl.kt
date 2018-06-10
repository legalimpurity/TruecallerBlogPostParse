package com.legalimpurity.truecaller_assingment.data

import com.legalimpurity.truecaller_assingment.data.remote.ApiDataHelper
import io.reactivex.Single
import javax.inject.Inject

class DataManagerImpl @Inject constructor(private val apiDataHelper: ApiDataHelper): DataManager {
    override fun getBlogPostResponse(year: Int, month: Int, date: Int, title: String) = apiDataHelper.getBlogPostResponse(year, month, date, title)

    override fun getBlogPostResponseAsCharArray(year: Int, month: Int, date: Int, title: String) = apiDataHelper
            .getBlogPostResponse(year, month, date, title)
            .map {
                it.toCharArray()
            }

    override fun getBlogPostResponseAsStringList(year: Int, month: Int, date: Int, title: String) = apiDataHelper
            .getBlogPostResponse(year, month, date, title)
            .map {
                it.split("\n")
            }
}