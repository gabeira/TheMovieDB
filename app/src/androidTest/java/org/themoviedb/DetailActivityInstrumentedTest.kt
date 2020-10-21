package org.themoviedb

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import org.hamcrest.Matchers
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.themoviedb.ui.home.MovieListAdapter

@LargeTest
@RunWith(AndroidJUnit4::class)
class DetailActivityInstrumentedTest {

    @get:Rule
    val intentsTestRule = IntentsTestRule(MainActivity::class.java)

    @Before
    fun launchActivity() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun mediaItemClickAndDetailScreenIsDisplayed() {
        Thread.sleep(200)
        InstrumentationRegistry.getInstrumentation().waitForIdleSync()

        onView(withId(R.id.swipeRefreshLayout)).check(matches(isDisplayed()))

        onView(withId(R.id.nowPlayingRecyclerView))
            .check(matches(isDisplayed()))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<MovieListAdapter.ViewHolder>(0, click())
            )

        Intents.intended(
            allOf(
                IntentMatchers.hasAction(Matchers.equalTo(Intent.ACTION_MAIN)),
                IntentMatchers.toPackage("org.themoviedb.test"),
                IntentMatchers.hasCategories(setOf(Intent.CATEGORY_LAUNCHER))
            )
        )
        Thread.sleep(200)

        InstrumentationRegistry.getInstrumentation().waitForIdleSync()

        onView(withId(R.id.mediaTitle)).check(matches(isDisplayed()))

        onView(withId(R.id.mediaImage)).check(matches(isDisplayed()))

        onView(withId(R.id.genre)).check(matches(isDisplayed()))

        onView(withId(R.id.runningTime)).check(matches(isDisplayed()))

        onView(withId(R.id.vote)).check(matches(isDisplayed()))

        onView(withId(R.id.close)).check(matches(isDisplayed())).perform(click())

        Thread.sleep(200)
        InstrumentationRegistry.getInstrumentation().waitForIdleSync()

        onView(withId(R.id.swipeRefreshLayout)).check(matches(isDisplayed()))
    }
}