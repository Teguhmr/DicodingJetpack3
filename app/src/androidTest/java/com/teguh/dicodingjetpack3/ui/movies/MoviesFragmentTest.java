package com.teguh.dicodingjetpack3.ui.movies;


import android.view.View;

import androidx.appcompat.widget.MenuPopupWindow;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.rule.ActivityTestRule;

import com.teguh.dicodingjetpack3.R;
import com.teguh.dicodingjetpack3.ui.home.HomeActivity;
import com.teguh.dicodingjetpack3.utils.EspressoIdlingResource;
import com.teguh.dicodingjetpack3.utils.RecyclerViewItemCountAssertion;

import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openContextualActionModeOverflowMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class MoviesFragmentTest {

	@Rule
	public ActivityTestRule<HomeActivity> activityRule = new ActivityTestRule<>(HomeActivity.class);
	private HomeActivity activity;
	private MoviesFragment moviesFragment;

	@Before
	public void setUp() {
		IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource());
		activity = activityRule.getActivity();
		moviesFragment = new MoviesFragment();
	}

	@After
	public void tearDown() {
		IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource());
	}

	@Test
	public void loadMovies() {
		openContextualActionModeOverflowMenu();
		onData(CoreMatchers.anything())
				.inRoot(RootMatchers.isPlatformPopup()) // isPlatformPopup() == is in PopupWindow
				.inAdapterView(CoreMatchers.<View>instanceOf(MenuPopupWindow.MenuDropDownListView.class))
				.atPosition(0) // for the first submenu item, here: add_sound
				.perform(click());
		openContextualActionModeOverflowMenu();
		onData(CoreMatchers.anything())
				.inRoot(RootMatchers.isPlatformPopup()) // isPlatformPopup() == is in PopupWindow
				.inAdapterView(CoreMatchers.<View>instanceOf(MenuPopupWindow.MenuDropDownListView.class))
				.atPosition(1) // for the first submenu item, here: add_sound
				.perform(click());
		openContextualActionModeOverflowMenu();
		onData(CoreMatchers.anything())
				.inRoot(RootMatchers.isPlatformPopup()) // isPlatformPopup() == is in PopupWindow
				.inAdapterView(CoreMatchers.<View>instanceOf(MenuPopupWindow.MenuDropDownListView.class))
				.atPosition(2) // for the first submenu item, here: add_sound
				.perform(click());
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
		onView(withId(R.id.rv_movies)).check(matches(isDisplayed()));
		onView(withId(R.id.rv_movies)).check(new RecyclerViewItemCountAssertion(40));
	}
}