package org.jointheleague.features.whale;

import java.util.concurrent.CompletableFuture;

import org.javacord.api.entity.user.User;

public class Person {
	private CompletableFuture<User> user;
	private String[] tags;
	private String timezone;

	public Person(CompletableFuture<User> userById) {
		// TODO Auto-generated constructor stub
		user = userById;
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
