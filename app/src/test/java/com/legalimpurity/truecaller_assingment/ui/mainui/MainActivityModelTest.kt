package com.legalimpurity.truecaller_assingment.ui.mainui

import android.databinding.Observable
import com.legalimpurity.truecaller_assingment.data.DataManager
import com.legalimpurity.truecaller_assingment.utils.rx.TestSchedulerProvider
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.TestScheduler
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import android.databinding.ObservableField
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*
import org.mockito.ArgumentCaptor






@RunWith(MockitoJUnitRunner::class)
class MainActivityModelTest {


    @Mock
    lateinit var mMockDataManager: DataManager

    @Mock
    lateinit var mMockPropertyChangedCallbackFor10thLetter: Observable.OnPropertyChangedCallback

    @Mock
    lateinit var mMockPropertyChangedCallbackForEvery10thLetter: Observable.OnPropertyChangedCallback

    @Mock
    lateinit var mMockPropertyChangedCallbackForWordFrequencyRequests: Observable.OnPropertyChangedCallback

    private var mMainActivityModel: MainActivityModel? = null
    private val mTestScheduler: TestScheduler = TestScheduler()
    private val compositeDisposable = CompositeDisposable()

    @Before
    @Throws(Exception::class)
    fun setUp() {
        val testSchedulerProvider = TestSchedulerProvider(mTestScheduler)
        mMainActivityModel = MainActivityModel(mMockDataManager, testSchedulerProvider,compositeDisposable)
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        compositeDisposable.dispose()
    }

    @Test
    fun testAllThreeRequestsEnd() {
        val testString = "Hello, my name is\nRajat Khanna.\tRajat Khanna is trying to\rwrite a\t\rtest."
        val test10thLetterAnswer = " "
        val testEvery10thLetterAnswer = " aahrrs"
        val testFrequency10thLetterAnswer = "a = 1\nHello, = 1\nKhanna. = 1\nis = 2\nmy = 1\ntest. = 1\ntrying = 1\nname = 1\nto = 1\nwrite = 1\nRajat = 2\nKhanna = 1\n"

        // mocking api to return test string
        whenever(mMockDataManager.getBlogPostResponse(com.nhaarman.mockito_kotlin.anyOrNull(),com.nhaarman.mockito_kotlin.anyOrNull(),com.nhaarman.mockito_kotlin.anyOrNull(),com.nhaarman.mockito_kotlin.anyOrNull())).thenReturn(Single.just(testString))

        mMainActivityModel?.t10thCharacterRequestAnswer?.addOnPropertyChangedCallback(mMockPropertyChangedCallbackFor10thLetter)
        mMainActivityModel?.tevery10thCharacterRequestAnswer?.addOnPropertyChangedCallback(mMockPropertyChangedCallbackForEvery10thLetter)
        mMainActivityModel?.tWordCounterRequestAnswer?.addOnPropertyChangedCallback(mMockPropertyChangedCallbackForWordFrequencyRequests)

        val argumentCaptureFor10thLetter = ArgumentCaptor.forClass(Observable::class.java)
        val argumentCaptureForEvery10thLetter = ArgumentCaptor.forClass(Observable::class.java)
        val argumentCaptureForWordFrequencyRequests = ArgumentCaptor.forClass(Observable::class.java)

        // call webpage
        mMainActivityModel?.getWebPage()
        mTestScheduler.triggerActions()


        verify(mMockPropertyChangedCallbackFor10thLetter, times(2)).onPropertyChanged(argumentCaptureFor10thLetter.capture(), ArgumentMatchers.anyInt())

        val valuesFor10thLetter = argumentCaptureFor10thLetter.allValues
        val mVal0valuesFor10thLetter = valuesFor10thLetter[0] as ObservableField<String>
        assert(mVal0valuesFor10thLetter.get().equals(test10thLetterAnswer))


        verify(mMockPropertyChangedCallbackForEvery10thLetter, times(2)).onPropertyChanged(argumentCaptureForEvery10thLetter.capture(), ArgumentMatchers.anyInt())

        val valuesForEvery10thLetter = argumentCaptureForEvery10thLetter.allValues
        val mVal0valuesForEvery10thLetter = valuesForEvery10thLetter[0] as ObservableField<String>
        assert(mVal0valuesForEvery10thLetter.get().equals(testEvery10thLetterAnswer))

        verify(mMockPropertyChangedCallbackForWordFrequencyRequests, times(2)).onPropertyChanged(argumentCaptureForWordFrequencyRequests.capture(), ArgumentMatchers.anyInt())

        val valuesForWordFrequencyRequests = argumentCaptureForWordFrequencyRequests.allValues
        val mVal0ForWordFrequencyRequests = valuesForWordFrequencyRequests[0] as ObservableField<String>
        assert(mVal0ForWordFrequencyRequests.get().equals(testFrequency10thLetterAnswer))
    }
}