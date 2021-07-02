package com.teguh.dicodingjetpack3.ui.tvshows;


import androidx.test.espresso.IdlingRegistry;
import androidx.test.rule.ActivityTestRule;

import com.teguh.dicodingjetpack3.R;
import com.teguh.dicodingjetpack3.ui.home.HomeActivity;
import com.teguh.dicodingjetpack3.utils.EspressoIdlingResource;
import com.teguh.dicodingjetpack3.utils.RecyclerViewItemCountAssertion;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

public class TvShowsFragmentTest {

	@Rule
	public ActivityTestRule<HomeActivity> activityRule = new ActivityTestRule<>(HomeActivity.class);
	private HomeActivity activity;
	private TvShowsFragment tvShowsFragment;

	@Before
	public void setUp() {
		IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource());
		activity = activityRule.getActivity();
		tvShowsFragment = new TvShowsFragment();
	}

	@After
	public void tearDown() {
		IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource());
	}

	@Test
	public void	loadTvShows() {
		onView(withId(R.id.navigation_tv_shows)).perform(click());
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
		onView(withId(R.id.rv_tvshows)).check(matches(isDisplayed()));
		onView(withId(R.id.rv_tvshows)).check(new RecyclerViewItemCountAssertion(20));
	}
}