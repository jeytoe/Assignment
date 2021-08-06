package com.example.androidassessment.splashscreen

import android.view.animation.LinearInterpolator
import com.example.androidassessment.baseui.BaseActivity
import com.example.androidassessment.databinding.ActivitySplashScreenBinding


class SplashScreenActivity : BaseActivity<ActivitySplashScreenBinding>() {

    override fun getViewBinding(): ActivitySplashScreenBinding {
        return ActivitySplashScreenBinding.inflate(layoutInflater)
    }

    override fun getToolbarTitle(): Int = 0


    override fun onResume() {
        super.onResume()
        displayLogo()
    }

    private fun displayLogo() {
        binding.imgLogo.animate()
            .alpha(1f)
            .setDuration(2)
            .setInterpolator(LinearInterpolator())
            .start()
    }

}
