package com.legalimpurity.truecaller_assingment.ui.mainui

import android.arch.lifecycle.ViewModelProvider
import com.legalimpurity.truecaller_assingment.data.DataManager
import com.legalimpurity.truecaller_assingment.di.factories.ViewModelProviderFactory
import com.legalimpurity.truecaller_assingment.util.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class MainActivityModule
{
    @Provides
    fun provideMainViewModel(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable) = MainActivityModel(dataManager, schedulerProvider, compositeDisposable)

    @Provides
    fun provideMainViewModelFactory(mainViewModel: MainActivityModel) : ViewModelProvider.Factory = ViewModelProviderFactory<MainActivityModel>(mainViewModel)

}