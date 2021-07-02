package com.teguh.dicodingjetpack3.ui.detail;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.teguh.dicodingjetpack3.data.source.MovieCatalogueRepository;
import com.teguh.dicodingjetpack3.data.source.local.entity.ItemEntity;
import com.teguh.dicodingjetpack3.vo.Resource;

public class DetailItemViewModel extends ViewModel {

	private MutableLiveData<Integer> itemId = new MutableLiveData<>();

	private MovieCatalogueRepository movieCatalogueRepository;

	public DetailItemViewModel(MovieCatalogueRepository movieCatalogueRepository) {
		this.movieCatalogueRepository = movieCatalogueRepository;
	}

	void setItemId(int itemId) {
		this.itemId.setValue(itemId);
	}
	LiveData<Resource<ItemEntity>> item = Transformations.switchMap(
			itemId, input -> movieCatalogueRepository.getItem(input)
	);

	void setFavorite() {
		if (item.getValue() != null) {
			ItemEntity selectedItem = item.getValue().data;
			if (selectedItem != null) {
				final boolean newState = !selectedItem.isFavorited();
				movieCatalogueRepository.setFavorite(selectedItem, newState);
			}
		}
	}
}
