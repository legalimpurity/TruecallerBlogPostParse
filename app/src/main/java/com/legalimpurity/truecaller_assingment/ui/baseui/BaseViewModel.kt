package com.legalimpurity.truecaller_assingment.ui.baseui

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import com.legalimpurity.truecaller_assingment.data.DataManager
import com.legalimpurity.truecaller_assingment.util.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference


abstract class BaseViewModel<N>(val theDataManager: DataManager,
                                val theSchedulerProvider: SchedulerProvider) : ViewModel() {

    val isLoading = ObservableBoolean(false)

    private val theCompositeDisposable: CompositeDisposable = CompositeDisposable()

    var mNavigator: WeakReference<N> ? = null

    override fun onCleared() {
        theCompositeDisposable.dispose()
        super.onCleared()
    }

    fun setIsLoading(isLoading: Boolean) {
        this.isLoading.set(isLoading)
    }

    fun setNavigator(nav : N)
    {
        mNavigator = WeakReference(nav)
    }

    fun getCompositeDisposable() = theCompositeDisposable
    fun getSchedulerProvider() = theSchedulerProvider
    fun getDataManager() = theDataManager
    fun getNavigator() = mNavigator?.get()
}
