package org.jointheleague.features.whale;

import org.javacord.api.DiscordApi;

public class ApiGetter {

	private DiscordApi api;
	public ApiGetter(DiscordApi api) {
		this.api = api;
	}
	DiscordApi getApi() {
		return api;
	}
}
