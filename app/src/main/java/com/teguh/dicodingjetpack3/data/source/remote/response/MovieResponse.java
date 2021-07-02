package com.teguh.dicodingjetpack3.data.source.remote.response;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieResponse implements Parcelable {
	private int id;
	private String title;
	private String releaseDate;
	private String description;
	private String originalLanguage;
	private String poster;
	private int voteCount;
	private String backDrop;
	private float rate;
	private String genres;
	private String itemType;

	public MovieResponse() {
	}

	public MovieResponse(int id, String title, String releaseDate, String description, String originalLanguage, String poster, int voteCount, String backDrop, float rate, String genres, String itemType) {
		this.id = id;
		this.title = title;
		this.releaseDate = releaseDate;
		this.description = description;
		this.originalLanguage = originalLanguage;
		this.poster = poster;
		this.voteCount = voteCount;
		this.backDrop = backDrop;
		this.rate = rate;
		this.genres = genres;
		this.itemType = itemType;
	}

	protected MovieResponse(Parcel in) {
		id = in.readInt();
		title = in.readString();
		releaseDate = in.readString();
		description = in.readString();
		originalLanguage = in.readString();
		poster = in.readString();
		voteCount = in.readInt();
		backDrop = in.readString();
		rate = in.readFloat();
		genres = in.readString();
		itemType = in.readString();
	}

	public static final Creator<MovieResponse> CREATOR = new Creator<MovieResponse>() {
		@Override
		public MovieResponse createFromParcel(Parcel in) {
			return new MovieResponse(in);
		}

		@Override
		public MovieResponse[] newArray(int size) {
			return new MovieResponse[size];
		}
	};

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getVoteCount() {
		return voteCount;
	}

	public void setVoteCount(int voteCount) {
		this.voteCount = voteCount;
	}

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	public String getGenres() {
		return genres;
	}

	public void setGenres(String genres) {
		this.genres = genres;
	}

	public String getBackDrop() {
		return backDrop;
	}

	public void setBackDrop(String backDrop) {
		this.backDrop = backDrop;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOriginalLanguage() {
		return originalLanguage;
	}

	public void setOriginalLanguage(String originalLanguage) {
		this.originalLanguage = originalLanguage;
	}

	public String getPoster() {
		return poster;
	}

	public void setPoster(String poster) {
		this.poster = poster;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(title);
		dest.writeString(releaseDate);
		dest.writeString(description);
		dest.writeString(originalLanguage);
		dest.writeString(poster);
		dest.writeFloat(voteCount);
		dest.writeString(backDrop);
		dest.writeFloat(rate);
		dest.writeString(genres);
		dest.writeString(itemType);
	}
}
