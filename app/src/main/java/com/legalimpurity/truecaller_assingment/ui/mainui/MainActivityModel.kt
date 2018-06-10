package com.legalimpurity.truecaller_assingment.ui.mainui

import com.legalimpurity.truecaller_assingment.data.DataManager
import com.legalimpurity.truecaller_assingment.ui.baseui.BaseViewModel
import com.legalimpurity.truecaller_assingment.util.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class MainActivityModel(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable) : BaseViewModel<SplashActivityNavigator>(dataManager,schedulerProvider)
{
    fun getWebPage() {
        getCompositeDisposable().add(getDataManager()
                    .getBlogPostResponse()
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe({ theResponse ->
                    }, { throwable ->
                        getNavigator()?.errorOccured(throwable = throwable)
                    }))
    }
}