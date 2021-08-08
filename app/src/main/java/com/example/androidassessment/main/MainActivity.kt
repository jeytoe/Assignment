package com.example.androidassessment.main

import com.example.androidassessment.R
import com.example.androidassessment.base.BaseActivity
import com.example.androidassessment.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun getToolbarTitle(): Int = R.string.main_screen
}
