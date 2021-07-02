package com.teguh.dicodingjetpack3.data.source.remote;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.teguh.dicodingjetpack3.data.source.remote.response.MovieResponse;
import com.teguh.dicodingjetpack3.utils.EspressoIdlingResource;
import com.teguh.dicodingjetpack3.utils.JsonHelper;

import java.util.List;

public class RemoteRepository {

	private static RemoteRepository INSTANCE;
	private JsonHelper jsonHelper;

	private RemoteRepository(JsonHelper jsonHelper) {
		this.jsonHelper = jsonHelper;
	}

	public static RemoteRepository getInstance(JsonHelper jsonHelper) {
		if (INSTANCE == null)
			INSTANCE = new RemoteRepository(jsonHelper);
		return INSTANCE;
	}

	public LiveData<ApiResponse<List<MovieResponse>>> getMovies() {
		EspressoIdlingResource.increment();

		MutableLiveData<ApiResponse<List<MovieResponse>>> resultMovies = new MutableLiveData<>();

		Observer<List<MovieResponse>> resultMoviesObserver = new Observer<List<MovieResponse>>() {
			@Override
			public void onChanged(List<MovieResponse> MovieResponses) {
				if (MovieResponses.size() != 0)
					resultMovies.setValue(ApiResponse.success(MovieResponses));
				else
					resultMovies.setValue(ApiResponse.error("Movies request failed", MovieResponses));

				if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow())
					EspressoIdlingResource.decrement();

				jsonHelper.loadMovies().removeObserver(this);
			}
		};

		jsonHelper.loadMovies().observeForever(resultMoviesObserver);

		return resultMovies;
	}

	public LiveData<ApiResponse<List<MovieResponse>>> getTvShows() {
		EspressoIdlingResource.increment();

		MutableLiveData<ApiResponse<List<MovieResponse>>> resultTvShows = new MutableLiveData<>();

		Observer<List<MovieResponse>> resultTvShowsObserver = new Observer<List<MovieResponse>>() {
			@Override
			public void onChanged(List<MovieResponse> MovieResponses) {
				if (MovieResponses.size() != 0)
					resultTvShows.setValue(ApiResponse.success(MovieResponses));
				else
					resultTvShows.setValue(ApiResponse.error("Tv Shows request failed", MovieResponses));

				if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow())
					EspressoIdlingResource.decrement();

				jsonHelper.loadMovies().removeObserver(this);
			}
		};

		jsonHelper.loadTvShows().observeForever(resultTvShowsObserver);

		return resultTvShows;
	}
}
