package com.example.vynils

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.action.ViewActions
import net.datafaker.Faker



@LargeTest
@RunWith(AndroidJUnit4::class)
class CreateAlbumTest{
    private lateinit var mainActivity: ActivityScenario<MainActivity>
    private val faker = Faker()

    @Before
    fun setup(){
        mainActivity = launchActivity()
        mainActivity.moveToState(Lifecycle.State.RESUMED)
        onView(withId(R.id.fab)).perform(click())
    }

    @Test
    fun createAlbumTest(){
        val albumName = faker.name().title()
        val cover = faker.avatar().image()
        val releaseDate = "1999-01-01"
        val description = faker.lorem().sentence()
        val genre = "Salsa"
        val recordLabel = "Sony Music"

        onView(withId(R.id.editTextAlbumName)).perform(ViewActions.replaceText(albumName))
        closeSoftKeyboard()
        onView(withId(R.id.editTextAlbumDescripcion)).perform(ViewActions.replaceText(description))
        closeSoftKeyboard()
        onView(withId(R.id.editTextAlbumFecha)).perform(ViewActions.replaceText(releaseDate))
        closeSoftKeyboard()
        onView(withId(R.id.editTextAlbumPortada)).perform(ViewActions.replaceText(cover))
        closeSoftKeyboard()

        onView(withId(R.id.spinnerAlbumGenero)).perform(click())
        closeSoftKeyboard()
        onView(withText(genre)).perform(click());
        closeSoftKeyboard()
        onView(withId(R.id.spinnerAlbumDisquera)).perform(click())
        closeSoftKeyboard()
        onView(withText(recordLabel)).perform(click());
        onView(withId(R.id.button2)).perform(click())

    }

    @Test
    fun cancelCreateAlbumTest(){
        closeSoftKeyboard()
        onView(withId(R.id.button)).perform(click())
    }
}