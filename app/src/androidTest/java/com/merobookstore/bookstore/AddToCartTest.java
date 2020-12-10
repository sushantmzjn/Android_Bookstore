package com.merobookstore.bookstore;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class AddToCartTest {
    @Rule
    public ActivityTestRule<BookdetailsActivity> testRule = new ActivityTestRule<>(BookdetailsActivity.class);
    @Test
    public void CartTest() throws Exception{
        onView(withId(R.id.cart)).perform(click());
    }
}
