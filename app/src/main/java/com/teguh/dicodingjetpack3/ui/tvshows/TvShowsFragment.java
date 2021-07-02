package com.teguh.dicodingjetpack3.ui.tvshows;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.teguh.dicodingjetpack3.R;
import com.teguh.dicodingjetpack3.databinding.FragmentTvShowsBinding;
import com.teguh.dicodingjetpack3.ui.adapter.ItemsPagedAdapter;
import com.teguh.dicodingjetpack3.viewmodel.ViewModelFactory;

public class TvShowsFragment extends Fragment {
    private FragmentTvShowsBinding fragmentTvShowsBinding;
    private RecyclerView rvTvShows;
    private ProgressBar progressBar;
    private TvShowsViewModel tvShowsViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentTvShowsBinding = FragmentTvShowsBinding.inflate(inflater, container, false);
        return fragmentTvShowsBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvTvShows = view.findViewById(R.id.rv_tvshows);
        progressBar = view.findViewById(R.id.progress_bar);
        if (getActivity() != null) {
            tvShowsViewModel = obtainViewModel(getActivity(), this);
            ItemsPagedAdapter tvShowsAdapter = new ItemsPagedAdapter();

            tvShowsViewModel.fetch(false);
            tvShowsViewModel.tvShows.observe(getViewLifecycleOwner(), pagedListResource -> {
                if (pagedListResource != null) {
                    switch (pagedListResource.status) {
                        case LOADING:
                            rvTvShows.setVisibility(View.GONE);
                            progressBar.setVisibility(View.VISIBLE);
                            break;
                        case SUCCESS:
                            rvTvShows.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            tvShowsAdapter.submitList(pagedListResource.data);
                            tvShowsAdapter.notifyDataSetChanged();
                            break;
                        case ERROR:
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getContext(), pagedListResource.message, Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            });

            rvTvShows.setLayoutManager(new GridLayoutManager(getContext(), 2));
            rvTvShows.setHasFixedSize(true);
            rvTvShows.setAdapter(tvShowsAdapter);
        }
        final SwipeRefreshLayout pullToRefresh = view.findViewById(R.id.swipe_refresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                tvShowsViewModel.fetch(true);
                pullToRefresh.setRefreshing(false);
            }
        });
    }


    @NonNull
    private TvShowsViewModel obtainViewModel(FragmentActivity activity, Fragment fragment) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(fragment, factory).get(TvShowsViewModel.class);
    }
}