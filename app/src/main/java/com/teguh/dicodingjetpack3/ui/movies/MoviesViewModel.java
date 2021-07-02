package com.teguh.dicodingjetpack3.ui.movies;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.teguh.dicodingjetpack3.data.source.MovieCatalogueRepository;
import com.teguh.dicodingjetpack3.data.source.local.entity.ItemEntity;
import com.teguh.dicodingjetpack3.vo.Resource;

public class MoviesViewModel extends ViewModel {

	private MovieCatalogueRepository movieCatalogueRepository;
	private ItemEntity itemEntity;

	private MutableLiveData<Boolean> fetchNow = new MutableLiveData<>();

	public MoviesViewModel(MovieCatalogueRepository movieCatalogueRepository) {
		this.movieCatalogueRepository = movieCatalogueRepository;
	}

	LiveData<Resource<PagedList<ItemEntity>>> movies = Transformations.switchMap(
			fetchNow, input -> movieCatalogueRepository.getMovies(input)
	);

	LiveData<Resource<PagedList<ItemEntity>>> getAllNotes(String sort) {
		return movieCatalogueRepository.getAllMovies(sort);
	}
	void fetch(boolean fetchNow) {
		this.fetchNow.setValue(fetchNow);
	}
}