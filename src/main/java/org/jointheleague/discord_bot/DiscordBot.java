package org.jointheleague.discord_bot;

import org.javacord.api.*;
import org.javacord.api.entity.emoji.*;

public class DiscordBot {
	DiscordApi API;
	public DiscordBot(String Token) {
		this.API = new DiscordApiBuilder().setToken(Token).login().join();
	}
	public void Connect() {
		API.addMessageCreateListener(E -> {
			String Content = E.getMessageContent();
			if (Content.startsWith("BBot: ")) {
				String[] Data = Content.substring(6).split(" ");
				switch (Data[0]) {
					case "Poll":
						break;
				}
			}
		});
	}
}
