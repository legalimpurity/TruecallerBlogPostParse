package com.legalimpurity.truecaller_assingment.ui.mainui

import android.util.Log
import com.legalimpurity.truecaller_assingment.data.DataManager
import com.legalimpurity.truecaller_assingment.ui.baseui.BaseViewModel
import com.legalimpurity.truecaller_assingment.util.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class MainActivityModel(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable) : BaseViewModel<SplashActivityNavigator>(dataManager,schedulerProvider,compositeDisposable)
{
    fun getWebPage(year: Int, month: Int, date: Int, title:String) {
        getCompositeDisposable().add(getDataManager()
                    .getBlogPostResponse(year, month, date, title)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe({ theResponse ->
                        Log.d("asd",theResponse)
                    }, { throwable ->
                        getNavigator()?.errorOccured(throwable = throwable)
                    }))
    }
}