package com.test

import android.os.Bundle
import com.example.androidassessment.R
import com.example.androidassessment.base.BaseActivity
import com.example.androidassessment.databinding.ActivityTestBinding
import dagger.android.AndroidInjection

class DummyActivity : BaseActivity<ActivityTestBinding>() {
    override fun getToolbarTitle(): Int {
        return R.string.app_name
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun getViewBinding(): ActivityTestBinding {
        return ActivityTestBinding.inflate(layoutInflater)
    }
}
