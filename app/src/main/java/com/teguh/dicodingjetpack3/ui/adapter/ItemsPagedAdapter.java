package com.teguh.dicodingjetpack3.ui.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.teguh.dicodingjetpack3.R;
import com.teguh.dicodingjetpack3.data.source.local.entity.ItemEntity;
import com.teguh.dicodingjetpack3.ui.detail.DetailActivity;


public class ItemsPagedAdapter extends PagedListAdapter<ItemEntity, ItemsPagedAdapter.ItemsViewHolder> {

	private static DiffUtil.ItemCallback<ItemEntity> DIFF_CALLBACK = new DiffUtil.ItemCallback<ItemEntity>() {
		@Override
		public boolean areItemsTheSame(@NonNull ItemEntity oldItem, @NonNull ItemEntity newItem) {
			return oldItem.getId() == newItem.getId();
		}

		@SuppressLint("DiffUtilEquals")
		@Override
		public boolean areContentsTheSame(@NonNull ItemEntity oldItem, @NonNull ItemEntity newItem) {
			return oldItem.equals(newItem);
		}
	};

	public ItemsPagedAdapter() {
		super(DIFF_CALLBACK);
	}

	@NonNull
	@Override
	public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_movie, parent, false);
		return new ItemsViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull ItemsViewHolder holder, int position) {
		final ItemEntity item = getItem(position);
		if (item != null) {
			String title = checkTextIfNull(item.getName());
			if (title.length() > 25) {
				holder.tvName.setText(String.format("%s...", title.substring(0, 20)));
			} else {
				holder.tvName.setText(item.getName());
			}
			Glide.with(holder.itemView.getContext())
					.load(item.getImgPosterPath())
					.apply(RequestOptions.placeholderOf(R.drawable.ic_loading).error(R.drawable.ic_error))
					.into(holder.imgPoster);

			holder.itemView.setOnClickListener(view -> {
				Context context = holder.itemView.getContext();
				Intent moveToDetailItem = new Intent(context, DetailActivity.class);
				moveToDetailItem.putExtra(DetailActivity.ENTITY_ID, item.getId());
				context.startActivity(moveToDetailItem);
			});
		}
	}

	public ItemEntity getItemById(int swipedPosition) {
		return getItem(swipedPosition);
	}


	String checkTextIfNull(String text) {
		if (text != null && !text.isEmpty()) {
			return text;
		} else {
			return "-";
		}
	}

	class ItemsViewHolder extends RecyclerView.ViewHolder {

		final ImageView imgPoster;
		final TextView tvName;

		ItemsViewHolder(@NonNull View itemView) {
			super(itemView);

			imgPoster = itemView.findViewById(R.id.img_item_photo);
			tvName = itemView.findViewById(R.id.title_tv);
		}
	}
}
