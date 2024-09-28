package com.mohamed.weatherforecastapp

import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.idling.CountingIdlingResource
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.anything
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)
    private val idlingResource = CountingIdlingResource("MyIdlingResource")

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(idlingResource)
    }

    fun onAsyncOperationStarted() {
        idlingResource.increment()
    }


    fun onAsyncOperationFinished() {
        idlingResource.decrement()
    }

    @Test
    fun mainActivityTest() {
        try {
            val appCompatSpinner = onView(
                allOf(
                    withId(R.id.spinnerCities),
                    isDisplayed()
                )
            )
            Thread.sleep(1000)
            appCompatSpinner.perform(click())

            Thread.sleep(1000)

            onData(anything()).inAdapterView(withId(android.R.id.list)).atPosition(2)
                .perform(click())

            Thread.sleep(1000)

            onAsyncOperationFinished()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
