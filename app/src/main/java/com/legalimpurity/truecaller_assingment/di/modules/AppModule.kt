package com.legalimpurity.truecaller_assingment.di.modules

import android.app.Application
import android.content.Context
import com.legalimpurity.truecaller_assingment.data.DataManager
import com.legalimpurity.truecaller_assingment.data.DataManagerImpl
import com.legalimpurity.truecaller_assingment.data.remote.ApiDataHelper
import com.legalimpurity.truecaller_assingment.data.remote.ApiDataHelperImpl
import com.legalimpurity.truecaller_assingment.util.rx.SchedulerProvider
import com.legalimpurity.truecaller_assingment.util.rx.SchedulerProviderImpl
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideDataManager(appDataManager: DataManagerImpl): DataManager = appDataManager

    @Singleton
    @Provides
    fun provideScheduleProvider(): SchedulerProvider = SchedulerProviderImpl()

    @Singleton
    @Provides
    fun provideApiDataHelper(retrofit: Retrofit): ApiDataHelper = ApiDataHelperImpl(retrofit)

    @Singleton
    @Provides
    fun provideContext(app: Application): Context = app.baseContext

    @Singleton
    @Provides
    fun provideCompositeDisposable() = CompositeDisposable()
}