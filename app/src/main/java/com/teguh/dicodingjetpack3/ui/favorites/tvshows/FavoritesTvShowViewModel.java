package com.teguh.dicodingjetpack3.ui.favorites.tvshows;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.PagedList;

import com.teguh.dicodingjetpack3.data.source.MovieCatalogueRepository;
import com.teguh.dicodingjetpack3.data.source.local.entity.ItemEntity;
import com.teguh.dicodingjetpack3.vo.Resource;

import static com.teguh.dicodingjetpack3.data.source.local.entity.ItemEntity.TYPE_TV_SHOW;

public class FavoritesTvShowViewModel extends ViewModel {

	private MovieCatalogueRepository movieCatalogueRepository;

	public FavoritesTvShowViewModel(MovieCatalogueRepository movieCatalogueRepository) {
		this.movieCatalogueRepository = movieCatalogueRepository;
	}

	LiveData<Resource<PagedList<ItemEntity>>> getFavoritesPaged() {
		return movieCatalogueRepository.getFavoritesPaged(TYPE_TV_SHOW);
	}

	void setFavorite(ItemEntity tvShow) {
		final boolean newState = !tvShow.isFavorited();
		movieCatalogueRepository.setFavorite(tvShow, newState);
	}

	void clearFavorites() {
		movieCatalogueRepository.clearFavorites(TYPE_TV_SHOW);
	}
}
