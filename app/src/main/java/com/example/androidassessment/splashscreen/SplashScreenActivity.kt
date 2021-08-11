package com.example.androidassessment.splashscreen

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.animation.LinearInterpolator
import com.example.androidassessment.base.BaseActivity
import com.example.androidassessment.databinding.ActivitySplashScreenBinding
import com.example.androidassessment.login.LoginActivity
import com.example.androidassessment.main.MainActivity
import dagger.android.AndroidInjection
import javax.inject.Inject


class SplashScreenActivity : BaseActivity<ActivitySplashScreenBinding>() {

    @Inject
    lateinit var splashScreenViewModel: SplashScreenViewModel

    override fun getViewBinding(): ActivitySplashScreenBinding {
        return ActivitySplashScreenBinding.inflate(layoutInflater)
    }

    override fun getToolbarTitle(): Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        splashScreenViewModel.initUserDatabase()
    }

    override fun onResume() {
        super.onResume()
        if (splashScreenViewModel.getRememberLoginValue())
            displayLogoAndNavigateTo(MainActivity::class.java) else
            displayLogoAndNavigateTo(LoginActivity::class.java)
    }

    private fun <T : Activity> displayLogoAndNavigateTo(clazz: Class<T>) {
        binding.imgLogo.animate()
            .alpha(1f)
            .setDuration(1500)
            .setInterpolator(LinearInterpolator())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    startActivity(Intent(this@SplashScreenActivity, clazz))
                    finish()
                }
            })
            .start()
    }

}
