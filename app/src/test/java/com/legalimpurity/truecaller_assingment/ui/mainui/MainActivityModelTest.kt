package com.legalimpurity.truecaller_assingment.ui.mainui

import com.legalimpurity.truecaller_assingment.data.DataManager
import com.legalimpurity.truecaller_assingment.utils.rx.TestSchedulerProvider
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.TestScheduler
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class MainActivityModelTest {

    @Mock
    lateinit var mMainActivityCallback: MainActivityNavigator

    @Mock
    lateinit var mMockDataManager: DataManager

    private var mMainActivityModel: MainActivityModel? = null
    private val mTestScheduler: TestScheduler = TestScheduler()
    private val compositeDisposable = CompositeDisposable()


    @Before
    @Throws(Exception::class)
    fun setUp() {
        val testSchedulerProvider = TestSchedulerProvider(mTestScheduler)
        mMainActivityModel = MainActivityModel(mMockDataManager, testSchedulerProvider,compositeDisposable)
        mMainActivityModel?.setNavigator(mMainActivityCallback)
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        compositeDisposable.dispose()
    }

    @Test
    fun testAllThreeRequestsEnd() {
        val testString = "Hello, my name is\nRajat Khanna.\tI am trying to\rwrite a\t\rtest."

        // mocking api to return test string
        whenever(mMockDataManager.getBlogPostResponse(com.nhaarman.mockito_kotlin.anyOrNull(),com.nhaarman.mockito_kotlin.anyOrNull(),com.nhaarman.mockito_kotlin.anyOrNull(),com.nhaarman.mockito_kotlin.anyOrNull())).thenReturn(Single.just(testString))

        // call webpage
        mMainActivityModel?.getWebPage(0,0,0,"")
        mTestScheduler.triggerActions()

        // verifying that user is logged in
        verify<MainActivityNavigator>(mMainActivityCallback).setNumberOfDoneRequests(1)
        verify<MainActivityNavigator>(mMainActivityCallback).setNumberOfDoneRequests(2)
        verify<MainActivityNavigator>(mMainActivityCallback).setNumberOfDoneRequests(3)
    }
}