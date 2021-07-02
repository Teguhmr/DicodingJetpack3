package com.teguh.dicodingjetpack3.ui.movies;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.teguh.dicodingjetpack3.R;
import com.teguh.dicodingjetpack3.data.source.local.entity.ItemEntity;
import com.teguh.dicodingjetpack3.databinding.FragmentMoviesBinding;
import com.teguh.dicodingjetpack3.ui.adapter.ItemsPagedAdapter;
import com.teguh.dicodingjetpack3.utils.SortUtils;
import com.teguh.dicodingjetpack3.viewmodel.ViewModelFactory;
import com.teguh.dicodingjetpack3.vo.Resource;


public class MoviesFragment extends Fragment {
    private FragmentMoviesBinding fragmentMoviesBinding;
    private RecyclerView rvMovies;
    private ProgressBar progressBar;
    private MoviesViewModel moviesViewModel;
    private ItemsPagedAdapter moviesAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentMoviesBinding = FragmentMoviesBinding.inflate(inflater, container, false);
        return fragmentMoviesBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvMovies = view.findViewById(R.id.rv_movies);
        progressBar = view.findViewById(R.id.progress_bar);
        if (getActivity() != null) {
            moviesViewModel = obtainViewModel(getActivity(), this);
            moviesAdapter = new ItemsPagedAdapter();

            moviesViewModel.fetch(false);
//            moviesViewModel.getAllNotes(SortUtils.NEWEST).observe(getViewLifecycleOwner(), noteObserver);


            moviesViewModel.movies.observe(getViewLifecycleOwner(), pagedListResource -> {
                if (pagedListResource != null) {
                    switch (pagedListResource.status) {
                        case LOADING:
                            rvMovies.setVisibility(View.GONE);
                            progressBar.setVisibility(View.VISIBLE);
                            break;
                        case SUCCESS:
                            rvMovies.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            moviesAdapter.submitList(pagedListResource.data);
                            moviesAdapter.notifyDataSetChanged();
                            break;
                        case ERROR:
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getContext(), pagedListResource.message, Toast.LENGTH_SHORT).show();
                            break;
                    }
                }

            });

            rvMovies.setLayoutManager(new GridLayoutManager(getContext(), 2));
            rvMovies.setHasFixedSize(true);
            rvMovies.setAdapter(moviesAdapter);


        }

        final SwipeRefreshLayout pullToRefresh = view.findViewById(R.id.swipe_refresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                moviesViewModel.fetch(true);
                pullToRefresh.setRefreshing(false);
            }
        });
    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.items_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String sort = "";
        switch (item.getItemId()) {
            case R.id.action_newest:
                sort = SortUtils.NEWEST;
                break;
            case R.id.action_oldest:
                sort = SortUtils.OLDEST;
                break;
            case R.id.action_random:
                sort = SortUtils.RANDOM;
                break;
        }
        moviesViewModel.getAllNotes(sort).observe(this, noteObserver);
        item.setChecked(true);
        return super.onOptionsItemSelected(item);
    }

    private final Observer<Resource<PagedList<ItemEntity>>> noteObserver = new Observer<Resource<PagedList<ItemEntity>>>() {
        @Override
        public void onChanged(@Nullable Resource<PagedList<ItemEntity>> pagedListResource) {
            if (pagedListResource != null) {
                if (pagedListResource != null) {
                    switch (pagedListResource.status) {
                        case LOADING:
                            rvMovies.setVisibility(View.GONE);
                            progressBar.setVisibility(View.VISIBLE);
                            break;
                        case SUCCESS:
                            rvMovies.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            moviesAdapter.submitList(pagedListResource.data);
                            moviesAdapter.notifyDataSetChanged();
                            break;
                        case ERROR:
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getContext(), pagedListResource.message, Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }
        }
    };

    @NonNull
    private MoviesViewModel obtainViewModel(FragmentActivity activity, Fragment fragment) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(fragment, factory).get(MoviesViewModel.class);
    }
}