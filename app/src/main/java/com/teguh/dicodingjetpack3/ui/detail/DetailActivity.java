package com.teguh.dicodingjetpack3.ui.detail;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.google.android.material.snackbar.Snackbar;
import com.teguh.dicodingjetpack3.R;
import com.teguh.dicodingjetpack3.data.source.local.entity.ItemEntity;
import com.teguh.dicodingjetpack3.databinding.ActivityDetailBinding;
import com.teguh.dicodingjetpack3.databinding.ContentDetailBinding;
import com.teguh.dicodingjetpack3.viewmodel.ViewModelFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.teguh.dicodingjetpack3.utils.Constant.IMAGE_URL;


public class DetailActivity extends AppCompatActivity {

    public static final String ENTITY_ID = "entity_id";
    public static final String ENTITY_TYPE = "entity_type";

    private ContentDetailBinding contentDetailBinding;
    private DetailItemViewModel detailItemViewModel;
    private MaterialFavoriteButton materialFavoriteButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActivityDetailBinding activityDetailBinding = ActivityDetailBinding.inflate(getLayoutInflater());
        contentDetailBinding = activityDetailBinding.detailContent;

        setContentView(activityDetailBinding.getRoot());
        setSupportActionBar(activityDetailBinding.toolbar);

        activityDetailBinding.progressBar.setVisibility(View.VISIBLE);
//        activityDetailBinding.content.setVisibility(View.INVISIBLE);

        detailItemViewModel = obtainViewModel(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int itemId = extras.getInt(ENTITY_ID, 0);
            if (itemId != 0) {
                detailItemViewModel.setItemId(itemId);
            }

        }

        detailItemViewModel.item.observe(this, itemEntityResource -> {
            if (itemEntityResource != null) {
                switch (itemEntityResource.status) {
                    case LOADING:
                        activityDetailBinding.progressBar.setVisibility(View.VISIBLE);
                        activityDetailBinding.content.setVisibility(View.GONE);
                        break;
                    case SUCCESS:
                        if (itemEntityResource.data != null) {
                            activityDetailBinding.progressBar.setVisibility(View.GONE);
                            activityDetailBinding.content.setVisibility(View.VISIBLE);

                            materialFavoriteButton = findViewById(R.id.favourite_button);
                            materialFavoriteButton.setFavorite(false);
                            boolean state = itemEntityResource.data.isFavorited();

                            if (state) {
                                materialFavoriteButton.setFavorite(true);
                                materialFavoriteButton.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
                                    @Override
                                    public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                                        setFavoriteButtonState(state);
                                        if (!favorite) {
                                            Snackbar.make(buttonView, getString(R.string.removed_from_fav),
                                                    Snackbar.LENGTH_SHORT).show();
                                        } else {

                                            Snackbar.make(buttonView, getString(R.string.add_to_fav),
                                                    Snackbar.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                            else {
                                materialFavoriteButton.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
                                    @Override
                                    public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                                        setFavoriteButtonState(state);
                                        if (!favorite) {
//                                            materialFavoriteButton.setFavorite(true);
                                            Snackbar.make(buttonView, getString(R.string.removed_from_fav),
                                                    Snackbar.LENGTH_SHORT).show();

                                        } else {
                                            materialFavoriteButton.setFavorite(true);
                                            Snackbar.make(buttonView, getString(R.string.add_to_fav),
                                                    Snackbar.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                            materialFavoriteButton.setOnClickListener(v->detailItemViewModel.setFavorite());

                            populateDetails(itemEntityResource.data);
                        }
                        break;
                    case ERROR:
                        activityDetailBinding.progressBar.setVisibility(View.GONE);
                        Toast.makeText(this, "Item not found", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

    }
    private void setFavoriteButtonState(boolean state) {
        if (state)
            materialFavoriteButton.setFavorite(true);
        else
            materialFavoriteButton.setFavorite(false);
    }
    private void populateDetails(final ItemEntity entity){
        contentDetailBinding.tvItemTitle.setText(entity.getName());
        contentDetailBinding.genres.setText(entity.getGenres());
        contentDetailBinding.tvItemVoteCount.setText(String.valueOf(entity.getVoteCount()));
        contentDetailBinding.tvItemLanguage.setText(entity.getLanguage());
        contentDetailBinding.tvItemRating.setText(String.valueOf(entity.getRating()));
        contentDetailBinding.description.setText(entity.getDescription());
        String time = entity.getYear();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = parser.parse(time);
            @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("EEEE, d MMM yyyy");
            String formattedDate = null;
            if (date != null) {
                formattedDate = formatter.format(date);
            }
            contentDetailBinding.release.setText(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Glide.with(this)
                .load(IMAGE_URL + entity.getImgPosterPath())
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading)
                        .error(R.drawable.ic_error))
                .into(contentDetailBinding.imgItemPhoto);

        Glide.with(this)
                .load(IMAGE_URL + entity.getImgBackdropPath())
                .transition(DrawableTransitionOptions.withCrossFade())
                .apply(RequestOptions.bitmapTransform(new BlurTransformation(15, 2)))
                .into(contentDetailBinding.mbackdrop);

    }
    private DetailItemViewModel obtainViewModel(AppCompatActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return ViewModelProviders.of(activity, factory).get(DetailItemViewModel.class);
    }
}