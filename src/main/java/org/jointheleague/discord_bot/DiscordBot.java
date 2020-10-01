package org.jointheleague.discord_bot;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.HighLowGame;
import org.jointheleague.features.examples.ApiExample;
import org.jointheleague.features.examples.CurrentTime;
import org.jointheleague.features.examples.RandomNumber;
import org.jointheleague.help_embed.HelpListener;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class DiscordBot {

	private String token;

	private String channelName;

	DiscordApi api;

	HelpListener helpListener;

	private DiscordBot(String token, String channelName) {
		this.token = token;
		this.channelName = channelName;
		helpListener = new HelpListener(channelName);
	}

	public static DiscordBot fromConfigJsonFile(){
		JSONParser parser = new JSONParser();
		try {
			//Read and parse config.json file
			FileReader fr = new FileReader(new File("src/main/resources/config.json"));
			JSONObject json = (JSONObject) parser.parse(fr);

			//Get the token and channelName from the object
			String token = (String) json.get("token");
			String channelName = (String) json.get("channelName");

			return new DiscordBot(token, channelName);
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void connect(boolean printInvite) {
		
		api = new DiscordApiBuilder().setToken(token).login().join();

		//Print the URL to invite the bot
		if (printInvite) {
			System.out.println("To authorize your bot, send your teacher this link: " + api.createBotInvite());
		}

		//Send bot connected message in channel
		api.getServerTextChannelsByName(channelName).forEach(e -> e.sendMessage(api.getYourself().getName() + " is connected"));

		//add help listener to bot
		api.addMessageCreateListener(helpListener);

		//add features
		addFeature(new RandomNumber(channelName));
		addFeature(new ApiExample(channelName));
		addFeature(new CurrentTime(channelName));
		addFeature(new HighLowGame(channelName));

	}

	private void addFeature(Feature feature){
		api.addMessageCreateListener(feature);
		helpListener.addHelpEmbed(feature.getHelpEmbed());
	}
}
