package org.jointheleague.features.whale;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.javacord.api.entity.permission.Role;
import org.javacord.api.entity.user.User;

public class Person {
	private CompletableFuture<User> user;
	private String nickname = "";
	private String username = "";
	private String[] tags;
	private String timezone;


	public Person(CompletableFuture<User> userById) {
		// TODO Auto-generated constructor stub
		user = userById;

	}


	public String getNickname() {
		// its empt and not Optional.Empty because of the substring on line 38
		if (nickname.equalsIgnoreCase("empt")) {
			return username;
		} else {
			return nickname;
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname.substring(9, nickname.length() - 1);
		;
	}

	public CompletableFuture<User> getUser() {
		return user;
	}

	public void setUser(CompletableFuture<User> user) {
		this.user = user;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

}
