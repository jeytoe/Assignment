package com.example.androidassessment.main.userlist

import com.example.androidassessment.ActivityScenarioRule
import com.example.androidassessment.R
import com.example.androidassessment.login.LoginActivity
import com.test.DummyActivity
import org.junit.Rule
import org.junit.Test

class UserListFragmentTest {

    @get:Rule
    val activityRule = ActivityScenarioRule<DummyActivity>()

    private val userListFragmentRobot = UserListFragmentRobot()

    @Test
    fun userListFragment_verifyUserList() {
        launchActivity()

        userListFragmentRobot
            .waitsFor(2000)
            .seesUsernameAtPosition("Antonette", 1)
            .seesUsernameAtPosition("Samantha", 2)
            .seesUsernameAtPosition("Leopoldo_Corkery", 5)
            .seesUsernameAtPosition("Delphine", 8)
            .seesUsernameAtPosition("Moriah.Stanton", 9)

    }

    @Test
    fun userListFragment_verifyLogoutButton() {
        launchActivity()

        userListFragmentRobot
            .waitsFor(2000)
            .clicksLogout()

        activityRule.checkActivityLaunched(LoginActivity::class.java)
    }

    private fun launchActivity() {
        activityRule.launchActivity(DummyActivity::class.java)
        activityRule.scenario.onActivity {
            it.supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, UserListFragment())
                .commit()
        }
    }
}
