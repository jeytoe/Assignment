package com.example.androidassessment.login

import android.os.Build
import androidx.annotation.StringRes
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.androidassessment.R
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.not

class LoginActivityRobot {

    fun typesUsername(username: String): LoginActivityRobot {
        onView(withId(R.id.username))
            .perform(ViewActions.replaceText(username))
        return this
    }

    fun typesPassword(password: String): LoginActivityRobot {
        onView(withId(R.id.password))
            .perform(ViewActions.replaceText(password))
        return this
    }

    fun seesLoginButtonState(isEnabled: Boolean): LoginActivityRobot {
        onView(withId(R.id.login))
            .check(matches(if (isEnabled) isEnabled() else (not(isEnabled()))))
        return this
    }

    fun clicksLoginButton(): LoginActivityRobot {
        onView(withId(R.id.login))
            .check(matches(isEnabled()))
            .perform(ViewActions.click())
        return this
    }

    fun seesDialogWithMessage(@StringRes messageId: Int): LoginActivityRobot {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Thread.sleep(200)
        }
        onView(withId(android.R.id.message))
            .inRoot(isDialog())
            .check(
                matches(
                    allOf(
                        isDisplayed(),
                        withText(messageId)
                    )
                )
            )
        return this
    }

    fun clicksOkayButton(): LoginActivityRobot {
        onView(withText(R.string.okay)).perform(ViewActions.click())
        return this
    }
}
