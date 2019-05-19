package com.assignment.gojek.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Pawan Gupta on 19/05/19.
 */
public class GitRepo implements Parcelable {
	@SerializedName("author")
	@Expose
	private String author;
	@SerializedName("name")
	@Expose
	private String name;
	@SerializedName("url")
	@Expose
	private String url;
	@SerializedName("description")
	@Expose
	private String description;
	@SerializedName("stars")
	@Expose
	private Integer stars;
	@SerializedName("forks")
	@Expose
	private Integer forks;
	@SerializedName("currentPeriodStars")
	@Expose
	private Integer currentPeriodStars;
	@SerializedName("builtBy")
	@Expose
	private List<GitUser> builtBy = null;
	public final static Parcelable.Creator<GitRepo> CREATOR = new Creator<GitRepo>() {


		@SuppressWarnings({
				"unchecked"
		})
		public GitRepo createFromParcel(Parcel in) {
			return new GitRepo(in);
		}

		public GitRepo[] newArray(int size) {
			return (new GitRepo[size]);
		}

	};

	protected GitRepo(Parcel in) {
		this.author = ((String) in.readValue((String.class.getClassLoader())));
		this.name = ((String) in.readValue((String.class.getClassLoader())));
		this.url = ((String) in.readValue((String.class.getClassLoader())));
		this.description = ((String) in.readValue((String.class.getClassLoader())));
		this.stars = ((Integer) in.readValue((Integer.class.getClassLoader())));
		this.forks = ((Integer) in.readValue((Integer.class.getClassLoader())));
		this.currentPeriodStars = ((Integer) in.readValue((Integer.class.getClassLoader())));
		in.readList(this.builtBy, (GitUser.class.getClassLoader()));
	}

	public GitRepo() {
	}

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

	public Integer getStars() {
		return stars;
	}

	public void setStars(Integer stars) {
		this.stars = stars;
	}

	public Integer getForks() {
		return forks;
	}

	public void setForks(Integer forks) {
		this.forks = forks;
	}

	public Integer getCurrentPeriodStars() {
		return currentPeriodStars;
	}

	public void setCurrentPeriodStars(Integer currentPeriodStars) {
		this.currentPeriodStars = currentPeriodStars;
	}

	public List<GitUser> getBuiltBy() {
		return builtBy;
	}

	public void setBuiltBy(List<GitUser> builtBy) {
		this.builtBy = builtBy;
	}



	public void writeToParcel(Parcel dest, int flags) {
		dest.writeValue(author);
		dest.writeValue(name);
		dest.writeValue(url);
		dest.writeValue(description);
		dest.writeValue(stars);
		dest.writeValue(forks);
		dest.writeValue(currentPeriodStars);
		dest.writeList(builtBy);
	}

	public int describeContents() {
		return 0;
	}

	public String getAuthorAvatar(){
		return getBuiltBy().get(0).getAvatar();
	}

}
