package com.teguh.dicodingjetpack3.utils;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.teguh.dicodingjetpack3.BuildConfig;
import com.teguh.dicodingjetpack3.data.source.remote.response.MovieResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static com.teguh.dicodingjetpack3.data.source.local.entity.ItemEntity.TYPE_MOVIE;
import static com.teguh.dicodingjetpack3.data.source.local.entity.ItemEntity.TYPE_TV_SHOW;
import static com.teguh.dicodingjetpack3.utils.Constant.DISCOVER_MOVIE_URL;
import static com.teguh.dicodingjetpack3.utils.Constant.DISCOVER_TV_URL;
import static com.teguh.dicodingjetpack3.utils.Constant.IMAGE_URL;

public class JsonHelper {

	public LiveData<List<MovieResponse>> loadMovies() {
		MutableLiveData<List<MovieResponse>> movies = new MutableLiveData<>();
		String url = DISCOVER_MOVIE_URL + BuildConfig.API_KEY + "&language=en-US&page=1";

		new AsyncHttpClient().get(url, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				List<MovieResponse> movieResponses = parseJsonToArrayList(new String(responseBody), TYPE_MOVIE);
				movies.postValue(movieResponses);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				Log.e(this.getClass().getSimpleName(), "onFailure: request movies failed", error);
				movies.postValue(new ArrayList<>());
			}
		});

		return movies;
	}

	public LiveData<List<MovieResponse>> loadTvShows() {
		MutableLiveData<List<MovieResponse>> tvShows = new MutableLiveData<>();
		String url = DISCOVER_TV_URL + BuildConfig.API_KEY + "&language=en-US&page=1";

		new AsyncHttpClient().get(url, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				List<MovieResponse> tvShowResponses = parseJsonToArrayList(new String(responseBody), TYPE_TV_SHOW);
				tvShows.postValue(tvShowResponses);
			}

			@Override
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
				Log.e(this.getClass().getSimpleName(), "onFailure: request movies failed", error);
				tvShows.postValue(new ArrayList<>());
			}
		});

		return tvShows;
	}

	private List<MovieResponse> parseJsonToArrayList(String responseJson, String itemsType) {
		List<MovieResponse> MovieResponses = new ArrayList<>();

		try {
			JSONObject responseObject = new JSONObject(responseJson);
			JSONArray responseArray = responseObject.getJSONArray("results");

			for (int i = 0; i < responseArray.length(); ++i) {
				JSONObject response = responseArray.getJSONObject(i);

				// ID
				int id = response.getInt("id");

				// Name
				String name = itemsType.equals(TYPE_MOVIE) ? response.getString("title") : response.getString("name");

//				// Relase year
				String releaseDate = itemsType.equals(TYPE_MOVIE) ? response.getString("release_date") : response.getString("first_air_date");

				// Poster path
				String imgPosterPath = IMAGE_URL + response.getString("poster_path");
				String backDrop = IMAGE_URL + response.getString("backdrop_path");

				// Genres
				JSONArray idGenres = response.getJSONArray("genre_ids");
				HashMap<Integer, String> dataGenres = DataGenres.getGenres();
				StringBuilder stringBuilder = new StringBuilder();

				for (int j = 0; j < idGenres.length(); ++j) {
					int idGenre = idGenres.getInt(j);
					String valueGenre = dataGenres.get(idGenre);
					stringBuilder.append(valueGenre);
					if (j != (idGenres.length() - 1))
						stringBuilder.append(", ");
				}

				String genres = stringBuilder.toString();

				// Descripton
				String description = response.getString("overview");

				// Language
				String language = response.getString("original_language").toUpperCase();

				// Rating
				float rating = (float) response.getDouble("vote_average");
				int voteCount = (int) response.getDouble("vote_count");

				MovieResponse MovieResponse = new MovieResponse (id, name, releaseDate, description, language, imgPosterPath, voteCount, backDrop, rating, genres, itemsType);
				MovieResponses.add(MovieResponse);
			}
		} catch (JSONException e) {
			Log.e(this.getClass().getSimpleName(), "parseJsonToArrayList: " + e.getMessage());
		}

		return MovieResponses;
	}
}
