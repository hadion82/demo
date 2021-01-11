package com.example.architecture

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.architecture.collection.ui.user.UserActivity
import com.sample.test.matcher.RecyclerViewMatcher.Companion.recyclerViewWithId
import com.sample.test.viewaction.SearchViewAction.Companion.typeSearchViewText
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class UserActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(UserActivity::class.java)

    @ExperimentalCoroutinesApi
    @Test
    fun testUserActivity() {
        val latch = CountDownLatch(1)
        onView(withId(R.id.search)).check(matches(isDisplayed()))
        onView(withId(R.id.search)).perform(typeSearchViewText("hhh"))

        latch.await(5, TimeUnit.SECONDS)
        onView(
            recyclerViewWithId(R.id.user_list)
                .viewHolderViewAtPosition(0, R.id.user_name)
        )
            .check(matches(withText("hhh")))
    }
}