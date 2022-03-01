package org.jointheleague.discord_bot;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.interaction.MessageComponentInteraction;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.listener.message.MessageCreateListener;
import org.javacord.api.util.event.ListenerManager;
public class DiscordBot {
	DiscordApi API;
	public DiscordBot(String Token) {
		this.API = new DiscordApiBuilder().setToken(Token).login().join();
	}
	HashMap<Long, ListenerManager<MessageCreateListener>> AnnoyedChannels = new HashMap<Long, ListenerManager<MessageCreateListener>>();
	HashMap<Long, String> AnnoyedChannelStarters = new HashMap<Long, String>();
	Random Rand = new Random();
	public void Connect() {
		long ClientID = API.getClientId();
		API.addMessageCreateListener(E -> {
			String Content = E.getMessageContent();
			if (Content.length() > 6) {
				String[] Data = Content.substring(6).split(" ");
				TextChannel Channel = E.getChannel();
				Long ChannelID = Channel.getId();
				String AuthorName = E.getMessageAuthor().asUser().get().getName();
				if (Content.startsWith("BBot: ")) {
					switch (Data[0]) {
						case "Annoy":
							if (!AnnoyedChannels.containsKey(ChannelID)) {
								Channel.sendMessage("Done");
								AnnoyedChannels.put(ChannelID, Channel.addMessageCreateListener(M -> {
									if (M.getMessageAuthor().getId() != ClientID) {
										Channel.sendMessage("\"" + M.getMessageContent() + "\"");
									}
								}));
								AnnoyedChannelStarters.put(ChannelID, AuthorName);
							} else {
								Channel.sendMessage("Already active");
							}
							break;
						case "StopAnnoy":
							if (!AnnoyedChannels.containsKey(ChannelID)) {
								Channel.sendMessage("Not active");
							} else if (!AnnoyedChannelStarters.get(ChannelID).equals(AuthorName)) {
								Channel.sendMessage("No");
							} else {
								Channel.sendMessage("Done");
								AnnoyedChannels.get(ChannelID).remove();
								AnnoyedChannels.remove(ChannelID);
								AnnoyedChannelStarters.remove(ChannelID);
							}
							break;
						case "RPS":
							try {
								Message M = new MessageBuilder().setContent("Choose one").addComponents(ActionRow.of(Button.success("Rock", "Rock"), Button.success("Paper", "Paper"), Button.success("Scissors", "Scissors"))).send(Channel).get();
								API.addMessageComponentCreateListener(S -> {
									MessageComponentInteraction Interaction = S.getMessageComponentInteraction();
									int Result = Rand.nextInt(100);
									if (Result < 75) {
										Interaction.createImmediateResponder().setContent("You lost!").respond();
									} else if (Result < 90) {
										Interaction.createImmediateResponder().setContent("Tie!").respond();
									} else {
										Interaction.createImmediateResponder().setContent("You won!").respond();
									}
									M.delete();
								});
							} catch(Exception S) {	
							}
							break;
						
						default:
							Channel.sendMessage("\"BBot: Help\" for help");
							break;
					}
				}
			}
		});
	}
}