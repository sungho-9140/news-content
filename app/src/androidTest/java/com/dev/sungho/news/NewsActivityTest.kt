package com.dev.sungho.news

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dev.sungho.news.util.RecyclerViewMatcher
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matchers.not
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class NewsActivityTest: BaseUITest() {
    /**
     * General list item verifications and perform action to next page and returns
     */
    @Test
    fun testEachListItemHasCorrectTextAndClickToNextPage() {
        // Verify if first index has header item including display name
        onView(RecyclerViewMatcher(R.id.recyclerView)
            .atPositionOnView(0, R.id.displayName))
            .check(matches(withText("AFR iPad Editor's Choice")))

        // Check header has title
        onView(RecyclerViewMatcher(R.id.recyclerView)
            .atPositionOnView(1, R.id.title))
            .check(matches(withText("The Hyundai electric car with surprising star power")))

        // Verify if an article doesn't have related image then thumbnail layout should be gone.
        onView(RecyclerViewMatcher(R.id.recyclerView)
            .atPositionOnView(1, R.id.imgThumbnail))
            .check(matches(not(isDisplayed())))

        // Check header has title
        onView(RecyclerViewMatcher(R.id.recyclerView)
            .atPositionOnView(2, R.id.title))
            .check(matches(withText("Dancing is back: what you can do in NSW from Monday")))

        // Check header has author
        onView(RecyclerViewMatcher(R.id.recyclerView)
            .atPositionOnView(2, R.id.subtitle))
            .check(matches(withText("")))

        onView(isRoot()).perform(waitFor(500))

        // Check header has description and click this item to redirect to next page
        onView(RecyclerViewMatcher(R.id.recyclerView)
            .atPositionOnView(2, R.id.description))
            .check(matches(withText("Fully vaccinated Sydneysiders will have most of their freedoms back from Monday, November 8 after the state government brought forward its reopening road map")))
            .perform(ViewActions.click())

        onView(isRoot()).perform(waitFor(3000))

        // Click back button to back to main page from previous page
        pressBack()
    }

    /**
     * Performing screen rotation test
     */
    @Test
    fun testRotationPage() {
        onView(isRoot()).perform(waitFor(500))
        rotateScreen()
        onView(isRoot()).perform(waitFor(1000))
        rotateScreen()
        onView(isRoot()).perform(waitFor(1000))
    }
}