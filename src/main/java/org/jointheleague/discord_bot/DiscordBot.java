package org.jointheleague.discord_bot;
import java.util.*;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;

import org.javacord.api.*;
import org.javacord.api.entity.emoji.*;
import org.javacord.api.entity.message.embed.*;
import org.javacord.api.listener.message.*;
import org.javacord.api.util.event.*;
import org.javacord.api.entity.channel.*;
public class DiscordBot {
	DiscordApi API;
	public DiscordBot(String Token) {
		this.API = new DiscordApiBuilder().setToken(Token).login().join();
	}
	HashMap<Long, ListenerManager<MessageCreateListener>> AnnoyedChannels = new HashMap<Long, ListenerManager<MessageCreateListener>>();
	HashMap<Long, String> AnnoyedChannelStarters = new HashMap<Long, String>();
	public void Connect() {
		long ClientID = API.getClientId();
		API.addMessageCreateListener(E -> {
			String Content = E.getMessageContent();
			String[] Data = Content.substring(6).split(" ");
			TextChannel Channel = E.getChannel();
			Long ChannelID = Channel.getId();
			String AuthorName = E.getMessageAuthor().asUser().get().getName();
			if (Content.startsWith("BBot: ")) {
				switch (Data[0]) {
					case "Annoy":
						if (!AnnoyedChannels.containsKey(ChannelID)) {
							ReactionUtils.ThumbsUp(E);
							AnnoyedChannels.put(ChannelID, Channel.addMessageCreateListener(M -> {
								if (M.getMessageAuthor().getId() != ClientID) {
									Channel.sendMessage("\"" + M.getMessageContent() + "\"");
								}
							}));
							AnnoyedChannelStarters.put(ChannelID, AuthorName);
						} else {
							ReactionUtils.ThumbsDown(E);
						}
						break;
					case "StopAnnoy":
						if (!AnnoyedChannels.containsKey(ChannelID)) {
							ReactionUtils.ThumbsDown(E);
						} else if (!AnnoyedChannelStarters.get(ChannelID).equals(AuthorName)) {
							ReactionUtils.Laugh(E);
						} else {
							ReactionUtils.ThumbsUp(E);
							AnnoyedChannels.get(ChannelID).remove();
							AnnoyedChannels.remove(ChannelID);
							AnnoyedChannelStarters.remove(ChannelID);
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
