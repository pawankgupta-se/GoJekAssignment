package com.assignment.gojek.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Pawan Gupta on 19/05/19.
 */
public class GitUser implements Parcelable {
	@SerializedName("url")
	@Expose
	private String href;
	@SerializedName("avatar")
	@Expose
	private String avatar;
	@SerializedName("username")
	@Expose
	private String username;
	public final static Parcelable.Creator<GitUser> CREATOR = new Creator<GitUser>() {


		@SuppressWarnings({
				"unchecked"
		})
		public GitUser createFromParcel(Parcel in) {
			return new GitUser(in);
		}

		public GitUser[] newArray(int size) {
			return (new GitUser[size]);
		}

	};

	protected GitUser(Parcel in) {
		this.href = ((String) in.readValue((String.class.getClassLoader())));
		this.avatar = ((String) in.readValue((String.class.getClassLoader())));
		this.username = ((String) in.readValue((String.class.getClassLoader())));
	}

	public GitUser() {
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "GitUser{" +
				"href='" + href + '\'' +
				", avatar='" + avatar + '\'' +
				", username='" + username + '\'' +
				'}';
	}

	public void writeToParcel(Parcel dest, int flags) {
		dest.writeValue(href);
		dest.writeValue(avatar);
		dest.writeValue(username);
	}

	public int describeContents() {
		return 0;
	}


}
