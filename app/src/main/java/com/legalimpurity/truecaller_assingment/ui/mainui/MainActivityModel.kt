package com.legalimpurity.truecaller_assingment.ui.mainui

import android.databinding.ObservableField
import com.legalimpurity.truecaller_assingment.data.DataManager
import com.legalimpurity.truecaller_assingment.ui.baseui.BaseViewModel
import com.legalimpurity.truecaller_assingment.util.rx.SchedulerProvider
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observables.GroupedObservable
import io.reactivex.subjects.ReplaySubject
import java.util.concurrent.TimeUnit
import java.util.regex.Pattern

class MainActivityModel(dataManager: DataManager, schedulerProvider: SchedulerProvider, compositeDisposable: CompositeDisposable) : BaseViewModel<MainActivityNavigator>(dataManager,schedulerProvider,compositeDisposable)
{
    var t10thCharacterRequestAnswer = ObservableField<String>()
    var tevery10thCharacterRequestAnswer = ObservableField<String>()
    var tWordCounterRequestAnswer = ObservableField<String>()

    // Requests Count by default -1, follows requests
    var requestsCount = ObservableField<Int>()

    var textViewRefreshingFrequency = ObservableField<String>()

    var etYear = ObservableField<String>()
    var etMonth = ObservableField<String>()
    var etDate = ObservableField<String>()
    var etTitle = ObservableField<String>()

    init {
        etYear.set("2018")
        etMonth.set("1")
        etDate.set("22")
        etTitle.set("life-as-an-android-engineer")
        requestsCount.set(-1)
        textViewRefreshingFrequency.set("1")
    }

    fun getWebPage() {
        requestsCount.set(0)

        t10thCharacterRequestAnswer.set("")
        tevery10thCharacterRequestAnswer.set("")
        tWordCounterRequestAnswer.set("")

        val year =etYear.get()
        val month = etMonth.get()
        val date = etDate.get()
        val title = etTitle.get()

        val listOfChars = ReplaySubject.create<Char>()
        val apiResponse = getDataManager().getBlogPostResponse(year!!, month!!, date!!, title!!)

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
                    requestsCount.set(requestsCount.get()?.plus(1))
                },{
                    t10thCharacterRequestAnswer.set("Some error occured!")
                    requestsCount.set(requestsCount.get()?.plus(1))
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
                // had to be changed from single to observable to get on complete
                .toObservable()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe({
                    tevery10thCharacterRequestAnswer.set(it)
                },{
                    tevery10thCharacterRequestAnswer.set("Some error occurred!")
                    requestsCount.set(requestsCount.get()?.plus(1))
                },{
                    requestsCount.set(requestsCount.get()?.plus(1))
                }))

        // Another way we could do this is by using the stream of characters and then creating words by checking for whitespace characters
        // For that the kind of operator I needed was BufferUntil which is not available by default.
        // If that would have been possible, I would have not used String.split and made it more efficient.

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
//                .toList() // replaced by buffer so that we can start showing resuls right away.
                // Here we pool the broken string we get to get them to display to the UI. The refresh rate is decided by the user.
                .buffer(textViewRefreshingFrequency.get()?.toLongOrNull() ?: 1 , TimeUnit.SECONDS)
//                .buffer( 1 , TimeUnit.SECONDS)
                // String builder was used so that a lot of string allocations do not take place. This way only the strings that are supposed to be shown in the UI are created.
                .map {
                    val sb = StringBuilder()
                    it.forEach{
                        sb.append(it.first+" = "+it.second+"\n")
                    }
                    sb.toString()
                }
                .scan{ x,y -> x+y }
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe({
                    tWordCounterRequestAnswer.set(it)
//                    tWordCounterRequestAnswer.set(tWordCounterRequestAnswer.get()+it.first+" = "+it.second+"\n")
//                    tWordCounterRequestAnswer.set(tWordCounterRequestAnswer.get()+it)
                },{
                    tWordCounterRequestAnswer.set("Some error occurred!")
                    requestsCount.set(requestsCount.get()?.plus(1))
                }, {
                    requestsCount.set(requestsCount.get()?.plus(1))
                }
            )
        )
    }
}