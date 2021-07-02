package com.teguh.dicodingjetpack3.ui.movies;


import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.paging.PagedList;

import com.teguh.dicodingjetpack3.data.source.MovieCatalogueRepository;
import com.teguh.dicodingjetpack3.data.source.local.entity.ItemEntity;
import com.teguh.dicodingjetpack3.vo.Resource;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static com.teguh.dicodingjetpack3.data.source.local.entity.ItemEntity.TYPE_TV_SHOW;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MoviesViewModelTest {

	@Rule
	public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

	private MoviesViewModel moviesViewModel;
	private MovieCatalogueRepository movieCatalogueRepository = mock(MovieCatalogueRepository.class);

	@Before
	public void setUp() {
		moviesViewModel = new MoviesViewModel(movieCatalogueRepository);
	}

	@Test
	public void getMovies() {
		MutableLiveData<Resource<PagedList<ItemEntity>>> dummyMovies = new MutableLiveData<>();

		//noinspection unchecked
		PagedList<ItemEntity> pagedList = mock(PagedList.class);

		dummyMovies.setValue(Resource.success(pagedList));

		when(movieCatalogueRepository.getMovies(false)).thenReturn(dummyMovies);

		//noinspection unchecked
		Observer<Resource<PagedList<ItemEntity>>> observer = mock(Observer.class);

		moviesViewModel.fetch(false);
		moviesViewModel.movies.observeForever(observer);
		verify(movieCatalogueRepository).getMovies(false);

		verify(observer).onChanged(Resource.success(pagedList));

		assertNotNull(moviesViewModel.movies.getValue());
		assertEquals(moviesViewModel.movies.getValue().data.size(), dummyMovies.getValue().data.size());
	}
}