package com.merobookstore.bookstore;

import org.junit.Rule;

import androidx.test.espresso.action.ViewActions;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginTest {
    @Rule
    public ActivityTestRule<LoginActivity> testRule = new ActivityTestRule<>(LoginActivity.class);
    private String uname = "sushantmzjn123";
    private String password = "1234";

    @Test
    public void LoginTest() throws Exception{
        onView(withId(R.id.etUsername)).perform(typeText(uname), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.etPassword)).perform(typeText(password), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.btnLogin)).perform(click());
    }

}
