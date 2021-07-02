package com.teguh.dicodingjetpack3.ui;


import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;

import com.teguh.dicodingjetpack3.R;
import com.teguh.dicodingjetpack3.ui.home.HomeActivity;
import com.teguh.dicodingjetpack3.utils.EspressoIdlingResource;
import com.teguh.dicodingjetpack3.utils.RecyclerViewItemCountAssertion;
import com.teguh.dicodingjetpack3.utils.TabLayoutViewAction;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MovieCatalogueTest {

	@Rule
	public ActivityTestRule<HomeActivity> activityRule = new ActivityTestRule<>(HomeActivity.class);

	@Before
	public void setUp() {
		IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource());
	}

	@After
	public void tearDown() {
		IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource());
	}

	@Test
	public void A_toDetailMovieActivity() {
		onView(withId(R.id.navigation_movies)).perform(click());
		onView(withId(R.id.rv_movies)).check(matches(isDisplayed()));
		onView(withId(R.id.rv_movies)).check(new RecyclerViewItemCountAssertion(20));
		onView(withId(R.id.rv_movies)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

		onView(withId(R.id.mbackdrop)).check(matches(isDisplayed()));
		onView(withId(R.id.img_item_photo)).check(matches(isDisplayed()));
		onView(withId(R.id.tv_item_language)).perform(scrollTo());
		onView(withId(R.id.tv_item_title)).check(matches(isDisplayed()));
		onView(withId(R.id.tv_item_rating)).check(matches(isDisplayed()));
		onView(withId(R.id.genres)).perform(scrollTo());
		onView(withId(R.id.genres)).check(matches(isDisplayed()));
		onView(withId(R.id.release)).perform(scrollTo());
		onView(withId(R.id.release)).check(matches(isDisplayed()));
		onView(withId(R.id.tv_item_language)).check(matches(isDisplayed()));
		onView(withId(R.id.description)).perform(scrollTo());
		onView(withId(R.id.description)).check(matches(isDisplayed()));

		onView(withId(R.id.favourite_button)).perform(scrollTo());
		onView(withId(R.id.favourite_button)).perform(click());
	}

	@Test
	public void B_toDetailTvShowActivity() {
		onView(withId(R.id.navigation_tv_shows)).perform(click());
		onView(withId(R.id.rv_tvshows)).check(matches(isDisplayed()));
		onView(withId(R.id.rv_tvshows)).check(new RecyclerViewItemCountAssertion(20));
		onView(withId(R.id.rv_tvshows)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

		onView(withId(R.id.mbackdrop)).check(matches(isDisplayed()));
		onView(withId(R.id.img_item_photo)).check(matches(isDisplayed()));
		onView(withId(R.id.tv_item_language)).perform(scrollTo());
		onView(withId(R.id.tv_item_title)).check(matches(isDisplayed()));
		onView(withId(R.id.tv_item_rating)).check(matches(isDisplayed()));
		onView(withId(R.id.genres)).perform(scrollTo());
		onView(withId(R.id.genres)).check(matches(isDisplayed()));
		onView(withId(R.id.release)).perform(scrollTo());
		onView(withId(R.id.release)).check(matches(isDisplayed()));
		onView(withId(R.id.tv_item_language)).check(matches(isDisplayed()));
		onView(withId(R.id.description)).perform(scrollTo());
		onView(withId(R.id.description)).check(matches(isDisplayed()));

		onView(withId(R.id.favourite_button)).perform(scrollTo());
		onView(withId(R.id.favourite_button)).perform(click());
	}

	@Test
	public void C_toFavoritesMovieFragment() {
		onView(withId(R.id.navigation_favorites)).perform(click());
		onView(withId(R.id.tab_layout)).perform(new TabLayoutViewAction(0));
		onView(withId(R.id.action_clear)).perform(click());
		onView(withText("OKE")).perform(click());
		onView(allOf(withId(R.id.tv_favorites_empty), isDisplayed())).check(matches(isDisplayed()));
	}

	@Test
	public void D_toFavoritesTvShowFragment() {
		onView(withId(R.id.navigation_favorites)).perform(click());
		onView(withId(R.id.tab_layout)).perform(new TabLayoutViewAction(1));
		onView(withId(R.id.action_clear)).perform(click());
		onView(withText("OKE")).perform(click());
		onView(allOf(withId(R.id.tv_favorites_empty), isDisplayed())).check(matches(isDisplayed()));
	}
}
