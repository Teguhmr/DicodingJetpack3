package com.teguh.dicodingjetpack3;


import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PagedList;

import com.teguh.dicodingjetpack3.data.source.local.LocalRepository;
import com.teguh.dicodingjetpack3.data.source.local.entity.ItemEntity;
import com.teguh.dicodingjetpack3.data.source.remote.RemoteRepository;
import com.teguh.dicodingjetpack3.utils.FakeDataDummy;
import com.teguh.dicodingjetpack3.utils.InstantAppExecutors;
import com.teguh.dicodingjetpack3.utils.LiveDataTestUtil;
import com.teguh.dicodingjetpack3.utils.PagedListUtil;
import com.teguh.dicodingjetpack3.vo.Resource;

import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static com.teguh.dicodingjetpack3.data.source.local.entity.ItemEntity.TYPE_MOVIE;
import static com.teguh.dicodingjetpack3.data.source.local.entity.ItemEntity.TYPE_TV_SHOW;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MovieCatalogueRepositoryTest {

	@Rule
	public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

	private RemoteRepository remoteRepository = mock(RemoteRepository.class);
	private LocalRepository localRepository = mock(LocalRepository.class);
	private InstantAppExecutors instantAppExecutors = mock(InstantAppExecutors.class);
	private FakeMovieCatalogueRepository movieCatalogueRepository = new FakeMovieCatalogueRepository(remoteRepository, localRepository, instantAppExecutors);

	private List<ItemEntity> dummyMoviesEntities = FakeDataDummy.getDummyMovies();
	private List<ItemEntity> dummyTvShowsEntities = FakeDataDummy.getDummyTvShows();

	@Test
	public void getMovies() {
		//noinspection unchecked
		DataSource.Factory<Integer, ItemEntity> dataSourceFactory = mock(DataSource.Factory.class);

		when(localRepository.getItemsPaged(TYPE_MOVIE)).thenReturn(dataSourceFactory);

		movieCatalogueRepository.getMovies(false);
		verify(localRepository).getItemsPaged(TYPE_MOVIE);

		Resource<PagedList<ItemEntity>> movieResult = Resource.success(PagedListUtil.mockPagedList(dummyMoviesEntities));
		assertNotNull(movieResult.data);
		assertEquals(dummyMoviesEntities.size(), movieResult.data.size());
	}

	@Test
	public void getTvShows() {
		//noinspection unchecked
		DataSource.Factory<Integer, ItemEntity> dataSourceFactory = mock(DataSource.Factory.class);

		when(localRepository.getItemsPaged(TYPE_TV_SHOW)).thenReturn(dataSourceFactory);

		movieCatalogueRepository.getTvShows(false);
		verify(localRepository).getItemsPaged(TYPE_TV_SHOW);

		Resource<PagedList<ItemEntity>> tvShowResult = Resource.success(PagedListUtil.mockPagedList(dummyTvShowsEntities));
		assertNotNull(tvShowResult.data);
		assertEquals(dummyTvShowsEntities.size(), tvShowResult.data.size());
	}

	@Test
	public void getItemMovie() {
		// --------- Movie ---------
		ItemEntity dummyMovie = dummyMoviesEntities.get(0);
		int dummyMovieId = dummyMovie.getId();

		MutableLiveData<ItemEntity> movie = new MutableLiveData<>();
		movie.setValue(dummyMovie);

		when(localRepository.getItem(dummyMovieId)).thenReturn(movie);

		Resource<ItemEntity> movieResult = LiveDataTestUtil.getValue(movieCatalogueRepository.getItem(dummyMovieId));

		verify(localRepository).getItem(dummyMovieId);

		assertNotNull(movieResult);
		assertNotNull(movieResult.data);
		assertEquals(dummyMovieId, movieResult.data.getId());

	}
	@Test
	public void getItemTV() {

		// --------- Tv Show ---------
		ItemEntity dummyTvShow = dummyTvShowsEntities.get(0);
		int dummyTvShowId = dummyTvShow.getId();

		MutableLiveData<ItemEntity> tvShow = new MutableLiveData<>();
		tvShow.setValue(dummyTvShow);

		when(localRepository.getItem(dummyTvShowId)).thenReturn(tvShow);

		Resource<ItemEntity> tvShowResult = LiveDataTestUtil.getValue(movieCatalogueRepository.getItem(dummyTvShowId));

		verify(localRepository).getItem(dummyTvShowId);
		assertNotNull(tvShowResult);
		assertNotNull(tvShowResult.data);
		assertEquals(dummyTvShowId, tvShowResult.data.getId());

		// --------- Fav Movie ---------//
		DataSource.Factory<Integer, ItemEntity> dataSourceFactory = mock(DataSource.Factory.class) ;
		when(localRepository.getFavoritesItemPaged(TYPE_MOVIE)).thenReturn(dataSourceFactory);
		movieCatalogueRepository.getFavoritesPaged(TYPE_MOVIE);

		Resource<PagedList<ItemEntity>> favMovieResult = Resource.success(PagedListUtil.mockPagedList(dummyMoviesEntities));
		verify(localRepository).getFavoritesItemPaged(TYPE_MOVIE);
		assertNotNull(favMovieResult.data);
		assertEquals(dummyMoviesEntities.size(), favMovieResult.data.size());

		// --------- Fav Tv Show ---------//
		DataSource.Factory<Integer, ItemEntity> dataSourceFactoryTv = mock(DataSource.Factory.class);
		when(localRepository.getFavoritesItemPaged(TYPE_TV_SHOW)).thenReturn(dataSourceFactoryTv);
		movieCatalogueRepository.getFavoritesPaged(TYPE_TV_SHOW);

		Resource<PagedList<ItemEntity>> favTVResult = Resource.success(PagedListUtil.mockPagedList(dummyMoviesEntities));
		verify(localRepository).getFavoritesItemPaged(TYPE_TV_SHOW);
		assertNotNull(favTVResult.data);
		assertEquals(dummyMoviesEntities.size(), favTVResult.data.size());
	}

	@Test
	public void getItemFavMovie() {
		// --------- Fav Movie ---------//
		DataSource.Factory<Integer, ItemEntity> dataSourceFactory = mock(DataSource.Factory.class) ;
		when(localRepository.getFavoritesItemPaged(TYPE_MOVIE)).thenReturn(dataSourceFactory);
		movieCatalogueRepository.getFavoritesPaged(TYPE_MOVIE);

		Resource<PagedList<ItemEntity>> favMovieResult = Resource.success(PagedListUtil.mockPagedList(dummyMoviesEntities));
		verify(localRepository).getFavoritesItemPaged(TYPE_MOVIE);
		assertNotNull(favMovieResult.data);
		assertEquals(dummyMoviesEntities.size(), favMovieResult.data.size());

		// --------- Fav Tv Show ---------//
		DataSource.Factory<Integer, ItemEntity> dataSourceFactoryTv = mock(DataSource.Factory.class);
		when(localRepository.getFavoritesItemPaged(TYPE_TV_SHOW)).thenReturn(dataSourceFactoryTv);
		movieCatalogueRepository.getFavoritesPaged(TYPE_TV_SHOW);

		Resource<PagedList<ItemEntity>> favTVResult = Resource.success(PagedListUtil.mockPagedList(dummyMoviesEntities));
		verify(localRepository).getFavoritesItemPaged(TYPE_TV_SHOW);
		assertNotNull(favTVResult.data);
		assertEquals(dummyMoviesEntities.size(), favTVResult.data.size());
	}

	@Test
	public void getItemFavTV() {
		// --------- Fav Tv Show ---------//
		DataSource.Factory<Integer, ItemEntity> dataSourceFactoryTv = mock(DataSource.Factory.class);
		when(localRepository.getFavoritesItemPaged(TYPE_TV_SHOW)).thenReturn(dataSourceFactoryTv);
		movieCatalogueRepository.getFavoritesPaged(TYPE_TV_SHOW);

		Resource<PagedList<ItemEntity>> favTVResult = Resource.success(PagedListUtil.mockPagedList(dummyMoviesEntities));
		verify(localRepository).getFavoritesItemPaged(TYPE_TV_SHOW);
		assertNotNull(favTVResult.data);
		assertEquals(dummyMoviesEntities.size(), favTVResult.data.size());
	}
}