package com.legalimpurity.truecaller_assingment.ui.mainui

import android.databinding.ObservableField
import com.legalimpurity.truecaller_assingment.data.DataManager
import com.legalimpurity.truecaller_assingment.ui.baseui.BaseViewModel
import com.legalimpurity.truecaller_assingment.util.rx.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.ReplaySubject



class MainActivityModel(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable) : BaseViewModel<SplashActivityNavigator>(dataManager,schedulerProvider,compositeDisposable)
{
    var t10thCharacterRequestAnswer = ObservableField<String>()
    var tevery10thCharacterRequestAnswer = ObservableField<String>()

    var listOfChars = ReplaySubject.create<Char>()


    var year: Int = 2018
    var month: Int = 1
    var date: Int = 22
    var title:String = "life-as-an-android-engineer"

    init {
         getDataManager()
                .getBlogPostResponseAsCharArray(year, month, date, title)
                .map {
                    it.toList()
                }
                .toObservable()
                .flatMap {
                    Observable.fromIterable<Char>(it)
                }
                 .subscribeOn(getSchedulerProvider().io())
                 .subscribe(listOfChars)


    }

    fun getWebPage() {

        val listOf10thChars = listOfChars
                .skip(9)
                .buffer(1,10)

        getCompositeDisposable().add(listOf10thChars
                .firstElement()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe({
                    t10thCharacterRequestAnswer.set(it[0].toString())
                },{
                    tevery10thCharacterRequestAnswer.set("Some error occured!")
                }))

        // Getting every character on callback was coming out to be very resource intensive on the UI thread as the TextView was updated to frequently.
        // Therefore I decided that it was best to create the whole string on background thread itself and just provide the outout string to the textview directly.
        getCompositeDisposable().add(listOf10thChars
                .toList()
                .map {
                    var sb = StringBuilder()
                    it.forEach{
                        sb.append(it[0])
                    }
                    sb.toString()
                }
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe({
                    tevery10thCharacterRequestAnswer.set(it)
                },{
                    tevery10thCharacterRequestAnswer.set("Some error occured!")
                }))


    }
}