package com.teguh.dicodingjetpack3.data.source;


import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.teguh.dicodingjetpack3.data.source.local.entity.ItemEntity;
import com.teguh.dicodingjetpack3.vo.Resource;

public interface MovieCatalogueDataSource {

	LiveData<Resource<PagedList<ItemEntity>>> getMovies(boolean fetchNow);

	LiveData<Resource<PagedList<ItemEntity>>> getTvShows(boolean fetchNow);

	LiveData<Resource<PagedList<ItemEntity>>> getFavoritesPaged(String itemType);

	LiveData<Resource<PagedList<ItemEntity>>> getAllMovies(String sort);

	LiveData<Resource<ItemEntity>> getItem(int itemId);

	void setFavorite(ItemEntity item, boolean newState);

	void clearFavorites(String itemType);
}
