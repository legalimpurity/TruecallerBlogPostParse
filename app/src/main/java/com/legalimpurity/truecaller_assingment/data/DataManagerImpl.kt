package com.legalimpurity.truecaller_assingment.data

import com.legalimpurity.truecaller_assingment.data.remote.ApiDataHelper
import io.reactivex.Single
import javax.inject.Inject

class DataManagerImpl @Inject constructor(private val apiDataHelper: ApiDataHelper): DataManager {
    override fun getBlogPostResponse(year: String, month: String, date: String, title: String) = apiDataHelper.getBlogPostResponse(year, month, date, title)
}