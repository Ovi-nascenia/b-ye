package biyeta.nas.biyeta;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.nascenia.biyeta.R;
import com.nascenia.biyeta.activity.Login;

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

/**
 * Created by god father on 4/17/2017.
 */

@RunWith(AndroidJUnit4.class)
public class LoginUnitTest {

    @Rule
    public ActivityTestRule<Login> mActivityResult = new ActivityTestRule<>(Login.class);

    @Test
    public void checksuccessfulLogin()
    {
        onView(withId(R.id.login_email)).perform(typeText("qamehedi@gmail.com"),closeSoftKeyboard());
        onView(withId(R.id.login_password)).perform(typeText("12345678"),closeSoftKeyboard());
        onView(withId(R.id.login_submit)).perform(click());


        //check the other screen text

        onView(withId(R.id.search_btn)).check(matches(withText("ইচ্ছে মত খুঁজুন")));

    }

    @Test
    public void checkLogin()
    {
        onView(withId(R.id.login_email)).perform(typeText("wqamehedi@gmail.com"),closeSoftKeyboard());
        onView(withId(R.id.login_password)).perform(typeText("12345678"),closeSoftKeyboard());
        onView(withId(R.id.login_submit)).perform(click());


        //check the other screen text

        onView(withId(R.id.search_btn)).check(matches(withText("ইচ্ছে মত খুঁজুন")));

    }

    @Test
    public void checkLogin1()
    {
        onView(withId(R.id.login_email)).perform(typeText("qamehedi@gmail.com"),closeSoftKeyboard());
        onView(withId(R.id.login_password)).perform(typeText("12345678"),closeSoftKeyboard());
        onView(withId(R.id.login_submit)).perform(click());


        //check the other screen text

        onView(withId(R.id.search_btn)).check(matches(withText("ইচ্ছে মত খুঁজুন")));

    }

    @Test
    public void checkLogin3()
    {
        onView(withId(R.id.login_email)).perform(typeText("@gmail.com"),closeSoftKeyboard());
        onView(withId(R.id.login_password)).perform(typeText("12345678"),closeSoftKeyboard());
        onView(withId(R.id.login_submit)).perform(click());


        //check the other screen text

        onView(withId(R.id.search_btn)).check(matches(withText("ইচ্ছে মত খুঁজুন")));

    }


}
