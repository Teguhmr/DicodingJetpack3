package com.teguh.dicodingjetpack3.ui.favorites.tvshows;


import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;

import com.teguh.dicodingjetpack3.data.source.MovieCatalogueRepository;
import com.teguh.dicodingjetpack3.data.source.local.entity.ItemEntity;
import com.teguh.dicodingjetpack3.utils.DataDummy;
import com.teguh.dicodingjetpack3.vo.Resource;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static com.teguh.dicodingjetpack3.data.source.local.entity.ItemEntity.TYPE_MOVIE;
import static com.teguh.dicodingjetpack3.data.source.local.entity.ItemEntity.TYPE_TV_SHOW;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class FavoritesTvShowViewModelTest {

	@Rule
	public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

	private FavoritesTvShowViewModel favoritesTvShowViewModel;
	private MovieCatalogueRepository movieCatalogueRepository;

	@Before
	public void setUp() {
		movieCatalogueRepository = mock(MovieCatalogueRepository.class);
		favoritesTvShowViewModel = new FavoritesTvShowViewModel(movieCatalogueRepository);
	}

	@Test
	public void getFavoritesTvShow() {
		MutableLiveData<Resource<PagedList<ItemEntity>>> dummyTvShows = new MutableLiveData<>();

		//noinspection unchecked
		PagedList<ItemEntity> pagedList = mock(PagedList.class);

		when(movieCatalogueRepository.getFavoritesPaged(TYPE_TV_SHOW)).thenReturn(dummyTvShows);

		dummyTvShows.setValue(Resource.success(pagedList));

		//noinspection unchecked
		Observer<Resource<PagedList<ItemEntity>>> observer = mock(Observer.class);

		favoritesTvShowViewModel.getFavoritesPaged().observeForever(observer);
		verify(movieCatalogueRepository).getFavoritesPaged(TYPE_TV_SHOW);

		verify(observer).onChanged(Resource.success(pagedList));
		assertNotNull(favoritesTvShowViewModel.getFavoritesPaged().getValue());
		assertEquals(favoritesTvShowViewModel.getFavoritesPaged().getValue().data.size(), dummyTvShows.getValue().data.size());

	}

}