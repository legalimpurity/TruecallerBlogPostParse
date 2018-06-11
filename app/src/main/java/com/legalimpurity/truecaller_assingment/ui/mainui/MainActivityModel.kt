package com.legalimpurity.truecaller_assingment.ui.mainui

import android.databinding.ObservableField
import com.legalimpurity.truecaller_assingment.data.DataManager
import com.legalimpurity.truecaller_assingment.ui.baseui.BaseViewModel
import com.legalimpurity.truecaller_assingment.util.rx.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observables.GroupedObservable
import io.reactivex.subjects.ReplaySubject
import java.util.regex.Pattern

class MainActivityModel(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable) : BaseViewModel<MainActivityNavigator>(dataManager,schedulerProvider,compositeDisposable)
{
    var t10thCharacterRequestAnswer = ObservableField<String>()
    var tevery10thCharacterRequestAnswer = ObservableField<String>()
    var tWordCounterRequest = ObservableField<String>()

    fun getWebPage(year: Int, month: Int, date: Int, title:String) {

        val listOfChars = ReplaySubject.create<Char>()
        val apiResponse = getDataManager().getBlogPostResponse(year, month, date, title)

        apiResponse
                .map {
                    it.toList()
                }
                .toObservable()
                .flatMap {
                    Observable.fromIterable<Char>(it)
                }
                .subscribeOn(getSchedulerProvider().io())
                .subscribe(listOfChars)


        val listOf10thChars = listOfChars
                .skip(9)
//                .window(10,1)
                .buffer(1,10)

        getCompositeDisposable().add(listOf10thChars
                .firstElement()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe({
                    t10thCharacterRequestAnswer.set(it[0].toString())
                },{
                    t10thCharacterRequestAnswer.set("Some error occured!")
                }))

        getCompositeDisposable().add(listOf10thChars

                // Getting every character on callback was coming out to be very resource intensive on the UI thread as the TextView was updated to frequently.
                // Therefore I decided that it was best to create the whole string on background thread itself and just provide the outout string to the textview directly.

                .toList()
                .map {
                    var sb = StringBuilder()
                    it.forEach{
                        sb.append(it[0].toString())
                    }
                    sb.toString()
                }

                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe({
                    tevery10thCharacterRequestAnswer.set(it)
                },{
                    tevery10thCharacterRequestAnswer.set("Some error occurred!")
                }))

        // Another way we could do this is by using the stream of characters and then creating words by checking for whitespace characters
        // For that the kind of operator I needed was BufferUntil which is not available by default.

        getCompositeDisposable().add(apiResponse
                .map {
                    it.split(Pattern.compile("\\s+"))
                }
                .toObservable()
                .flatMap {
                    Observable.fromIterable<String>(it)
                }
                .groupBy{it}
                .flatMap { groupObservableOfKeyword: GroupedObservable<String, String> ->
                    groupObservableOfKeyword.map {
                        Pair(groupObservableOfKeyword.key,1)
                    }
                    .reduce { t1: Pair<String?, Int>, t2: Pair<String?, Int> ->
                        Pair(t1.first,t1.second+t2.second)
                    }
                            .toObservable()
                }
                .toList()
                .map {
                    var sb = StringBuilder()
                    it.forEach{
                        sb.append(it.first+" = "+it.second+"\n")
                    }
                    sb.toString()
                }
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe({
                    tWordCounterRequest.set(it)
                },{
                    tWordCounterRequest.set("Some error occurred!")
                }
            )
        )
    }
}