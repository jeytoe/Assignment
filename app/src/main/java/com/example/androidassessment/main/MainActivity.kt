package com.example.androidassessment.main

import android.content.Intent
import com.example.androidassessment.R
import com.example.androidassessment.base.BaseActivity
import com.example.androidassessment.databinding.ActivityMainBinding
import com.example.androidassessment.login.LoginActivity

class MainActivity : BaseActivity<ActivityMainBinding>(), LogoutAction {

    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun getToolbarTitle(): Int = R.string.main_screen

    override fun logout() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}

interface LogoutAction {
    fun logout()
}
