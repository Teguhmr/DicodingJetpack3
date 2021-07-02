package com.teguh.dicodingjetpack3.ui.tvshows;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.teguh.dicodingjetpack3.data.source.MovieCatalogueRepository;
import com.teguh.dicodingjetpack3.data.source.local.entity.ItemEntity;
import com.teguh.dicodingjetpack3.vo.Resource;

public class TvShowsViewModel extends ViewModel {

	private MovieCatalogueRepository movieCatalogueRepository;

	private MutableLiveData<Boolean> fetchNow = new MutableLiveData<>();

	public TvShowsViewModel(MovieCatalogueRepository movieCatalogueRepository) {
		this.movieCatalogueRepository = movieCatalogueRepository;
	}

	LiveData<Resource<PagedList<ItemEntity>>> tvShows = Transformations.switchMap(
			fetchNow, input -> movieCatalogueRepository.getTvShows(input)
	);

	void fetch(boolean fetchNow) {
		this.fetchNow.setValue(fetchNow);
	}
}