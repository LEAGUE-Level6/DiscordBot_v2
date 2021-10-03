package org.jointheleague.discord_bot;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.examples.second_features.HighLowGame;
import org.jointheleague.features.examples.third_features.CatFactsApi;
import org.jointheleague.features.examples.third_features.NewsApi;
import org.jointheleague.features.examples.first_features.CurrentTime;
import org.jointheleague.features.examples.first_features.RandomNumber;
import org.jointheleague.features.help_embed.HelpListener;
import org.jointheleague.features.student.Pun;
import org.jointheleague.features.student.Riddle;

public class DiscordBot {

	private String token;

	private String channelName;

	DiscordApi api;

	HelpListener helpListener;

	public DiscordBot(String token, String channelName) {
		this.token = token;
		this.channelName = channelName;
		helpListener = new HelpListener(channelName);
	}

	public void connect(boolean printInvite) {
		
		api = new DiscordApiBuilder().setToken(token).login().join();

		//Print the URL to invite the bot
		if (printInvite) {
			System.out.println("To authorize your bot, send your teacher this link: " + api.createBotInvite()
					+"\n\tThis message can be disabled in Launcher.java");
		}

		//Send bot connected message in channel
		api.getServerTextChannelsByName(channelName).forEach(e -> e.sendMessage(api.getYourself().getName() + " has connected"));

		//add help listener to bot
		api.addMessageCreateListener(helpListener);

		//add features
		//addFeature(new RandomNumber(channelName));
		//addFeature(new CurrentTime(channelName));
		//addFeature(new HighLowGame(channelName));
		/*addFeature(new NewsApi(channelName));
		addFeature(new CatFactsApi(channelName));*/
		addFeature(new Pun(channelName));
		addFeature(new Riddle(channelName));
	}

	private void addFeature(Feature feature){
		api.addMessageCreateListener(feature);
		helpListener.addHelpEmbed(feature.getHelpEmbed());
	}
}
