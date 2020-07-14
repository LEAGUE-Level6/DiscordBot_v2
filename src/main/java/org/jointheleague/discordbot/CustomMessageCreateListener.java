package org.jointheleague.discordbot;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;

import net.aksingh.owmjapis.api.APIException;
import org.jointheleague.pojo.help_embed.HelpEmbed;

public abstract class CustomMessageCreateListener implements MessageCreateListener {
	protected String channelName;
	public HelpEmbed helpEmbed;

	public CustomMessageCreateListener(String channelName) {
		this.channelName = channelName;
	}

	@Override
	public void onMessageCreate(MessageCreateEvent event) {
		event.getServerTextChannel().ifPresent(e -> {
			if (e.getName().equals(channelName)) {
				try {
					handle(event);
				} catch (APIException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}
	
	public HelpEmbed getHelpEmbed() {
		return this.helpEmbed;
	}

	public abstract void handle(MessageCreateEvent event) throws APIException;
}