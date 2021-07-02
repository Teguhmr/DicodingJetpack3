package com.teguh.dicodingjetpack3.ui.detail;


import android.content.Context;
import android.content.Intent;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import com.teguh.dicodingjetpack3.R;
import com.teguh.dicodingjetpack3.data.source.local.entity.ItemEntity;
import com.teguh.dicodingjetpack3.utils.EspressoIdlingResource;
import com.teguh.dicodingjetpack3.utils.FakeDataDummy;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

public class DetailTvShowActivityTest {

	private ItemEntity dummyTvShow = FakeDataDummy.getDummyTvShow();

	@Rule
	public ActivityTestRule<DetailActivity> tvShowActivityTestRule = new ActivityTestRule<DetailActivity>(DetailActivity.class) {
		@Override
		protected Intent getActivityIntent() {
			Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
			Intent result = new Intent(targetContext, DetailActivity.class);
			result.putExtra(DetailActivity.ENTITY_ID, dummyTvShow.getId());
			return result;
		}
	};

	@Before
	public void setUp() {
		IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource());
	}

	@After
	public void tearDown() {
		IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource());
	}

	@Test
	public void loadTvShow() {
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
	}
}