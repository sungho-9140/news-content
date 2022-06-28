package com.dev.sungho.news

import android.app.Activity
import android.content.pm.ActivityInfo
import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.rule.ActivityTestRule
import dagger.hilt.android.testing.HiltAndroidRule
import org.hamcrest.Matcher
import org.junit.Rule

open class BaseUITest {
    @get:Rule(order = 0)
    var mHiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var mActivityRule: ActivityScenarioRule<NewsActivity> =
        ActivityScenarioRule(NewsActivity::class.java)

    @get:Rule(order = 2)
    var mActivityTestRule: ActivityTestRule<NewsActivity> =
        ActivityTestRule(NewsActivity::class.java)

    protected fun rotateScreen() {
        val currentActivity = (mActivityTestRule.activity as Activity)
        val rotation = currentActivity.requestedOrientation
        if (rotation == ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
            || rotation == ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE
            || rotation == ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR
            || rotation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        ) {
            currentActivity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        } else {
            currentActivity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
    }

    /**
     * Perform action of waiting for a specific time.
     */
    protected fun waitFor(millis: Long): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> {
                return ViewMatchers.isRoot()
            }

            override fun getDescription(): String {
                return "Wait for $millis milliseconds."
            }

            override fun perform(
                uiController: UiController,
                view: View
            ) {
                uiController.loopMainThreadForAtLeast(millis)
            }
        }
    }
}