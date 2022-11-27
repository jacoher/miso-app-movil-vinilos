package com.example.vynils


import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.CoreMatchers
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class AlbumAddTrackTest {
    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup(){
        scenario = launchActivity()
        scenario.moveToState(Lifecycle.State.RESUMED)

        //Validar que hay al menos un registro con los datos completos
        //Prerrequesitos: Se asume que existe al menos un registro en la lista de albums

        Thread.sleep(3000);

        //Valida si la lista de albumes esta renderizada
        onView(withId(R.id.albumsRv)).check(matches(ViewMatchers.isDisplayed()))
        onView(withId(R.id.albumsRv)).check(matches(UtilityTest.atPosition(0, hasDescendant(withText("Buscando América")))))

        onView(withId(R.id.albumsRv)).perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        Thread.sleep(1500);

        onView(withId(R.id.albumName)).check(matches(withText(CoreMatchers.containsString("Buscando América"))))
        onView(withId(R.id.albumPerformers)).check(matches(withText(CoreMatchers.containsString("Rubén Blades Bellido de Luna"))))

        onView(withId(R.id.fab)).perform(click())
        Thread.sleep(1000)
    }

    @Test
    fun testAddTrackToAlbum(){
        val trackName = "test1FromJUnit4"
        val trackMinutes = "5"
        val trackSeconds = "45"

        onView(withId(R.id.editTrackName)).perform(ViewActions.replaceText(trackName))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.editTrackMinutes)).perform(ViewActions.replaceText(trackMinutes))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.editTrackSeconds)).perform(ViewActions.replaceText(trackSeconds))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.button2)).perform(click())
    }

    @Test
    fun testCancelCreateTrack(){
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.button)).perform(click())
    }
}
