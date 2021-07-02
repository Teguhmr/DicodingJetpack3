package com.teguh.dicodingjetpack3.di;

import android.app.Application;

import com.teguh.dicodingjetpack3.data.source.MovieCatalogueRepository;
import com.teguh.dicodingjetpack3.data.source.local.LocalRepository;
import com.teguh.dicodingjetpack3.data.source.local.room.MovieCatalogueDatabase;
import com.teguh.dicodingjetpack3.data.source.remote.RemoteRepository;
import com.teguh.dicodingjetpack3.utils.AppExecutors;
import com.teguh.dicodingjetpack3.utils.JsonHelper;


public class Injection {

	public static MovieCatalogueRepository provideRepository(Application application) {
		MovieCatalogueDatabase database = MovieCatalogueDatabase.getInstance(application);

		LocalRepository localRepository = LocalRepository.getInstance(database.movieCatalogueDao());
		RemoteRepository remoteRepository = RemoteRepository.getInstance(new JsonHelper());
		AppExecutors appExecutors = new AppExecutors();

		return MovieCatalogueRepository.getInstance(remoteRepository, localRepository, appExecutors);
	}
}
