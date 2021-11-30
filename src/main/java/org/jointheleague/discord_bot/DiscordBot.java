package org.jointheleague.discord_bot;

import java.util.*;
import java.util.concurrent.TimeUnit;

import org.javacord.api.*;
import org.javacord.api.entity.emoji.*;
import org.javacord.api.entity.message.embed.*;
import org.javacord.api.entity.channel.*;

public class DiscordBot {
	DiscordApi API;
	public DiscordBot(String Token) {
		this.API = new DiscordApiBuilder().setToken(Token).login().join();
	}
	ArrayList<Long> AnnoyedChannels = new ArrayList<Long>();
	public void Connect() {
		long ClientID = API.getClientId();
		API.addMessageCreateListener(E -> {
			String Content = E.getMessageContent();
			String[] Data = Content.substring(6).split(" ");
			TextChannel Channel = E.getChannel();
			Long ChannelID = Channel.getId();
			if (Content.startsWith("BBot: ")) {
				switch (Data[0]) {
					case "Annoy":
						if (!AnnoyedChannels.contains(ChannelID)) {
							ReactionUtils.ThumbsUp(E);
							AnnoyedChannels.add(ChannelID);
							Channel.addMessageCreateListener(M -> {
								if (M.getMessageAuthor().getId() != ClientID) {
									Channel.sendMessage("\"" + M.getMessageContent() + "\"");
								}
							}).removeAfter(15, TimeUnit.MINUTES);
						} else {
							ReactionUtils.ThumbsDown(E);
						}
						break;
					case "PollCreate":
						if (Data.length > 1) {
							ReactionUtils.ThumbsUp(E);
							//TODO: continue
						} else {
							ReactionUtils.ThumbsDown(E);
						}
						break;
					case "Poll":
						break;
				}
			}
		});
	}
}
