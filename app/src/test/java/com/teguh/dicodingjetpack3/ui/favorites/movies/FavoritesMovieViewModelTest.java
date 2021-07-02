package com.teguh.dicodingjetpack3.ui.favorites.movies;


import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;

import com.teguh.dicodingjetpack3.data.source.MovieCatalogueRepository;
import com.teguh.dicodingjetpack3.data.source.local.entity.ItemEntity;
import com.teguh.dicodingjetpack3.utils.DataDummy;
import com.teguh.dicodingjetpack3.utils.FakeDataDummy;
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

public class FavoritesMovieViewModelTest {

	@Rule
	public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

	private FavoritesMovieViewModel favoritesMovieViewModel;
	private MovieCatalogueRepository movieCatalogueRepository;

	@Before
	public void setUp() {
		movieCatalogueRepository = mock(MovieCatalogueRepository.class);
		favoritesMovieViewModel = new FavoritesMovieViewModel(movieCatalogueRepository);
	}

	@Test
	public void getFavoritesMovie() {
		MutableLiveData<Resource<PagedList<ItemEntity>>> dummyFavoritesMovie = new MutableLiveData<>();

		//noinspection unchecked
		PagedList<ItemEntity> pagedList = mock(PagedList.class);

		dummyFavoritesMovie.setValue(Resource.success(pagedList));

		when(movieCatalogueRepository.getFavoritesPaged(TYPE_MOVIE)).thenReturn(dummyFavoritesMovie);
		//noinspection unchecked
		Observer<Resource<PagedList<ItemEntity>>> observer = mock(Observer.class);

		favoritesMovieViewModel.getFavoritesPaged().observeForever(observer);

		verify(movieCatalogueRepository).getFavoritesPaged(TYPE_MOVIE);

		verify(observer).onChanged(Resource.success(pagedList));
		assertNotNull(favoritesMovieViewModel.getFavoritesPaged().getValue());
		assertEquals(favoritesMovieViewModel.getFavoritesPaged().getValue().data.size(), dummyFavoritesMovie.getValue().data.size());

	}

}