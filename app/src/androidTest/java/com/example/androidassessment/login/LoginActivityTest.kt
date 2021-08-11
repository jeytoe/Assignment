package com.example.androidassessment.login

import android.content.Intent
import androidx.test.platform.app.InstrumentationRegistry
import com.example.androidassessment.ActivityScenarioRule
import com.example.androidassessment.R
import com.example.androidassessment.UserDbRule
import com.example.androidassessment.main.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule<LoginActivity>()

    @get:Rule
    val userDbRule = UserDbRule()

    @Before
    fun setUp() {
        userDbRule.saveUser("username")
    }

    private val loginActivityRobot = LoginActivityRobot()

    @Test
    fun test() {
        launchActivity()

        loginActivityRobot
            .seesLoginButtonState(false)
            .typesUsername("123")
            .seesLoginButtonState(false)
            .typesPassword("124124")
            .seesLoginButtonState(true)
            .clicksLoginButton()
            .seesDialogWithMessage(R.string.wrong_credentials)
            .clicksOkayButton()
            .typesUsername("username")
            .typesPassword("username")
            .clicksLoginButton()

        activityRule.checkActivityLaunched(MainActivity::class.java)
    }

    private fun launchActivity() {
        val targetContext = InstrumentationRegistry.getInstrumentation().targetContext
        val intent = Intent(targetContext, LoginActivity::class.java)
        activityRule.launchActivity(intent)
    }
}
