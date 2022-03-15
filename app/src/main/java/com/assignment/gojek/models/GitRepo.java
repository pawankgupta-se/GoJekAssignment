package com.assignment.gojek.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

/**
 * Created by Pawan Gupta on 19/05/19.
 */
public class GitRepo implements Parcelable {
	@SerializedName("username")
	@Expose
	private String author;
	@SerializedName("repositoryName")
	@Expose
	private String name;
	@SerializedName("url")
	@Expose
	private String url;
	@SerializedName("description")
	@Expose
	private String description;
	@SerializedName("language")
	@Expose
	private String language;
	@SerializedName("languageColor")
	@Expose
	private String languageColor;
	@SerializedName("totalStars")
	@Expose
	private int stars;
	@SerializedName("forks")
	@Expose
	private int forks;
	@SerializedName("starsSince")
	@Expose
	private int currentPeriodStars;
	@SerializedName("builtBy")
	@Expose
	private List<GitUser> builtBy = null;
	private boolean isExpanded;

	public GitRepo() {
	}

	protected GitRepo(Parcel in) {
		author = in.readString();
		name = in.readString();
		url = in.readString();
		description = in.readString();
		language = in.readString();
		languageColor = in.readString();
		stars = in.readInt();
		forks = in.readInt();
		currentPeriodStars = in.readInt();
		builtBy = in.createTypedArrayList(GitUser.CREATOR);
		isExpanded = in.readByte() != 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(author);
		dest.writeString(name);
		dest.writeString(url);
		dest.writeString(description);
		dest.writeString(language);
		dest.writeString(languageColor);
		dest.writeInt(stars);
		dest.writeInt(forks);
		dest.writeInt(currentPeriodStars);
		dest.writeTypedList(builtBy);
		dest.writeByte((byte) (isExpanded ? 1 : 0));
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<GitRepo> CREATOR = new Creator<GitRepo>() {
		@Override
		public GitRepo createFromParcel(Parcel in) {
			return new GitRepo(in);
		}

		@Override
		public GitRepo[] newArray(int size) {
			return new GitRepo[size];
		}
	};

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStars() {
		return stars;
	}

	public int getForks() {
		return forks;
	}

	public int getCurrentPeriodStars() {
		return currentPeriodStars;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getLanguageColor() {
		return languageColor;
	}

	public void setLanguageColor(String languageColor) {
		this.languageColor = languageColor;
	}

	public void setStars(int stars) {
		this.stars = stars;
	}

	public void setForks(int forks) {
		this.forks = forks;
	}

	public void setCurrentPeriodStars(int currentPeriodStars) {
		this.currentPeriodStars = currentPeriodStars;
	}

	public boolean isExpanded() {
		return isExpanded;
	}

	public void setExpanded(boolean expanded) {
		isExpanded = expanded;
	}

	public List<GitUser> getBuiltBy() {
		return builtBy;
	}

	public void setBuiltBy(List<GitUser> builtBy) {
		this.builtBy = builtBy;
	}



	public String getAuthorAvatar(){
		return getBuiltBy().get(0).getAvatar();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof GitRepo)) return false;
		GitRepo gitRepo = (GitRepo) o;
		return Objects.equals(getAuthor(), gitRepo.getAuthor()) &&
				Objects.equals(getName(), gitRepo.getName()) &&
				Objects.equals(getUrl(), gitRepo.getUrl());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getAuthor(), getName(), getUrl());
	}
}
