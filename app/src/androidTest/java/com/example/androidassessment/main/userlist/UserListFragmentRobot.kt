package com.example.androidassessment.main.userlist

import android.os.SystemClock
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.androidassessment.R
import com.example.androidassessment.util.RecyclerViewMatcher
import org.hamcrest.CoreMatchers.allOf

class UserListFragmentRobot {

    fun seesUsernameAtPosition(message: String, position: Int): UserListFragmentRobot {
        onView(withId(R.id.user_list))
            .perform(scrollToPosition<RecyclerView.ViewHolder>(position))
        onView(
            RecyclerViewMatcher(R.id.user_list)
                .atPositionOnView(position, R.id.tv_username)
        ).check(matches(allOf(isDisplayed(), withText(message))))

        return this
    }

    fun waitsFor(delayInMilliseconds: Long): UserListFragmentRobot {
        SystemClock.sleep(delayInMilliseconds)
        return this
    }
}
