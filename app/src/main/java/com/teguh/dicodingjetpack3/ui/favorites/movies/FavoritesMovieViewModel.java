package com.teguh.dicodingjetpack3.ui.favorites.movies;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.teguh.dicodingjetpack3.data.source.MovieCatalogueRepository;
import com.teguh.dicodingjetpack3.data.source.local.entity.ItemEntity;
import com.teguh.dicodingjetpack3.vo.Resource;

import static com.teguh.dicodingjetpack3.data.source.local.entity.ItemEntity.TYPE_MOVIE;

public class FavoritesMovieViewModel extends ViewModel {

	private MovieCatalogueRepository movieCatalogueRepository;

	public FavoritesMovieViewModel(MovieCatalogueRepository movieCatalogueRepository) {
		this.movieCatalogueRepository = movieCatalogueRepository;
	}

	LiveData<Resource<PagedList<ItemEntity>>> getFavoritesPaged() {
		return movieCatalogueRepository.getFavoritesPaged(TYPE_MOVIE);
	}

	void setFavorite(ItemEntity movie) {
		final boolean newState = !movie.isFavorited();
		movieCatalogueRepository.setFavorite(movie, newState);
	}

	void clearFavorites() {
		movieCatalogueRepository.clearFavorites(TYPE_MOVIE);
	}
}
