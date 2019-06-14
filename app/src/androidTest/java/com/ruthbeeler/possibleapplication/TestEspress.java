package com.ruthbeeler.possibleapplication;

import android.support.test.espresso.ViewAction;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;



@RunWith(AndroidJUnit4.class)
@LargeTest
public class TestEspress {
    private String emailInput;
    private String passwordInput;

    /*@Rule
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class);
    */
    @Before
    public void initValidInput() {
        emailInput = "name";
        passwordInput = "something";
    }

    @Test
    public void changeLoginButton() {
        onView(withId(R.id.editText2)).perform(typeText(emailInput), closeSoftKeyboard());
        onView(withId(R.id.editText)).perform((ViewAction) click());
        onView(withId(R.id.editText3)).perform((ViewAction) typeText(passwordInput)).check(matches(withText(passwordInput)));
    }
}
