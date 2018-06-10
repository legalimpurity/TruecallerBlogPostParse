package com.legalimpurity.truecaller_assingment

import android.app.Activity
import android.app.Application
import com.legalimpurity.truecaller_assingment.di.AppComponent
import com.legalimpurity.truecaller_assingment.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class TrueCallerAssingmentApp : Application(), HasActivityInjector
{
    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    // Initialized by Lambda the first time it is supposed to be used.
    val appComponent: AppComponent by lazy {
        DaggerAppComponent
                .builder()
                .application(this)
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityDispatchingAndroidInjector

}