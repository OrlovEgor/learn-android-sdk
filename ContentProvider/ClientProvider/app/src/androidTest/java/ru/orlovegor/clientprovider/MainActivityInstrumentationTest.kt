package ru.orlovegor.clientprovider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest

class MainActivityInstrumentationTest {

    @get:Rule
    var activityTestRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)


    @Test
    fun checkStateDeleteOrGetButtons() {
        Espresso.onView(withId(R.id.id_edit_text))
            .perform(ViewActions.typeText("1"))
            .perform(ViewActions.closeSoftKeyboard())
        Espresso.onView(withId(R.id.delete_course_by_id))
            .check(ViewAssertions.matches(isEnabled()))
        Espresso.onView(withId(R.id.get_courses_by_id_button))
            .check(ViewAssertions.matches(isEnabled()))
        Espresso.onView(withId(R.id.get_all_courses_button))
            .check(ViewAssertions.matches(isEnabled()))
        Espresso.onView(withId(R.id.delete_all_courses))
            .check(ViewAssertions.matches(isEnabled()))
    }

    @Test
    fun checkStateAddAndUpdateButtons() {
        Espresso.onView(withId(R.id.id_edit_text))
            .perform(ViewActions.typeText("1"))
            .perform(ViewActions.closeSoftKeyboard())
        Espresso.onView(withId(R.id.tittle_edit_text))
            .perform(ViewActions.typeText("Android"))
            .perform(ViewActions.closeSoftKeyboard())
        Espresso.onView(withId(R.id.update_course_by_id))
            .check(ViewAssertions.matches(isEnabled()))
        Espresso.onView(withId(R.id.add_course_button))
            .check(ViewAssertions.matches(isEnabled()))
        Espresso.onView(withId(R.id.get_all_courses_button))
            .check(ViewAssertions.matches(isEnabled()))
        Espresso.onView(withId(R.id.delete_all_courses))
            .check(ViewAssertions.matches(isEnabled()))
    }
}