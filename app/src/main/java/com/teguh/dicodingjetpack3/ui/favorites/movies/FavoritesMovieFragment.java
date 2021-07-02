package com.teguh.dicodingjetpack3.ui.favorites.movies;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.teguh.dicodingjetpack3.R;
import com.teguh.dicodingjetpack3.data.source.local.entity.ItemEntity;
import com.teguh.dicodingjetpack3.ui.adapter.ItemsPagedAdapter;
import com.teguh.dicodingjetpack3.viewmodel.ViewModelFactory;

public class FavoritesMovieFragment extends Fragment {

	private RecyclerView rvFavoritesMovie;
	private ProgressBar progressBar;
	private ItemsPagedAdapter favoritesMovieAdapter;
	private FavoritesMovieViewModel favoritesMovieViewModel;
	private TextView tvFavoritesMovieEmpty;

	public static FavoritesMovieFragment newInstance() {
		return new FavoritesMovieFragment();
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.inner_favorites_fragment, container, false);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		rvFavoritesMovie = view.findViewById(R.id.rv_favorites);
		progressBar = view.findViewById(R.id.progress_bar);
		tvFavoritesMovieEmpty = view.findViewById(R.id.tv_favorites_empty);
		if (getActivity() != null) {
			favoritesMovieViewModel = obtainViewModel(getActivity(), this);
			favoritesMovieAdapter = new ItemsPagedAdapter();

			favoritesMovieViewModel.getFavoritesPaged().observe(getViewLifecycleOwner(), pagedListResource -> {
				if (pagedListResource != null) {
					switch (pagedListResource.status) {
						case LOADING:
							progressBar.setVisibility(View.VISIBLE);
							break;
						case SUCCESS:
							progressBar.setVisibility(View.GONE);
							favoritesMovieAdapter.submitList(pagedListResource.data);
							favoritesMovieAdapter.notifyDataSetChanged();
							if (pagedListResource.data != null) {
								if (pagedListResource.data.size() == 0)
									tvFavoritesMovieEmpty.setVisibility(View.VISIBLE);
								else
									tvFavoritesMovieEmpty.setVisibility(View.GONE);
							}
							break;
						case ERROR:
							progressBar.setVisibility(View.GONE);
							tvFavoritesMovieEmpty.setVisibility(View.VISIBLE);
							Toast.makeText(getContext(), pagedListResource.message, Toast.LENGTH_SHORT).show();
							break;
					}
				}
			});

			rvFavoritesMovie.setLayoutManager(new GridLayoutManager(getContext(), 2));
			rvFavoritesMovie.setHasFixedSize(true);
			rvFavoritesMovie.setAdapter(favoritesMovieAdapter);
			itemTouchHelper.attachToRecyclerView(rvFavoritesMovie);

		}
	}

	private ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
		@Override
		public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
			return makeMovementFlags(0,  ItemTouchHelper.RIGHT);
		}

		@Override
		public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
			return true;
		}

		@Override
		public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
			if (getView() != null) {
				int swipedPosition = viewHolder.getAdapterPosition();
				ItemEntity item = favoritesMovieAdapter.getItemById(swipedPosition);
				favoritesMovieViewModel.setFavorite(item);
				Snackbar snackbar = Snackbar.make(getView(), "Undo removal?", Snackbar.LENGTH_LONG);
				snackbar.setAction("UNDO", view -> favoritesMovieViewModel.setFavorite(item));

				if (getContext() != null)
					snackbar.setActionTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimaryTernary));
				snackbar.show();
			}
		}
	});

	@Override
	public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
		inflater.inflate(R.menu.favorites_menu, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(@NonNull MenuItem item) {
		if (item.getItemId() == R.id.action_clear)
			new AlertDialog.Builder(getContext())
					.setTitle("Delete entry")
					.setMessage("Hapus semua data?")

					// Specifying a listener allows you to take an action before dismissing the dialog.
					// The dialog is automatically dismissed when a dialog button is clicked.
					.setPositiveButton(R.string.oke, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							favoritesMovieViewModel.clearFavorites();
						}
					})

					// A null listener allows the button to dismiss the dialog and take no further action.
					.setNegativeButton(R.string.batal, null)
					.setIcon(android.R.drawable.ic_dialog_alert)
					.show();
		return super.onOptionsItemSelected(item);
	}

	@NonNull
	private FavoritesMovieViewModel obtainViewModel(FragmentActivity activity, Fragment fragment) {
		ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
		return ViewModelProviders.of(fragment, factory).get(FavoritesMovieViewModel.class);
	}
}
