package com.legalimpurity.truecaller_assingment.ui.mainui

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.legalimpurity.truecaller_assingment.R
import com.legalimpurity.truecaller_assingment.BR
import com.legalimpurity.truecaller_assingment.databinding.ActivityMainBinding
import com.legalimpurity.truecaller_assingment.ui.baseui.BaseActivity
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding, MainActivityModel>(), SplashActivityNavigator {

    @Inject
    lateinit var mViewModelFactory: ViewModelProvider.Factory

    private var mMainActivityModel: MainActivityModel? = null

    private var mActivityMainBinding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mMainActivityModel?.setNavigator(this)
        mActivityMainBinding = getViewDataBinding()
        mMainActivityModel?.getWebPage()
    }

    // Functions to be implemented by every Activity
    override fun getViewModel(): MainActivityModel {
        mMainActivityModel = ViewModelProviders.of(this, mViewModelFactory).get(MainActivityModel::class.java)
        return mMainActivityModel as MainActivityModel
    }

    override fun getBindingVariable() = BR.viewModel

    override fun getLayoutId() = R.layout.activity_main

    // Navigator Functions

    override fun errorOccured(throwable: Throwable) {

    }

}