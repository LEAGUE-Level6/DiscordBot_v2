package org.jointheleague.discordbot;

import org.javacord.api.DiscordApi;

import org.javacord.api.DiscordApiBuilder;

/**
 * Launches all of the listeners for one channel.
 * @author keithgroves and https://tinystripz.com
 *
 */
import org.jointheleague.features.HighLowGame;
import org.jointheleague.features.example.ApiExample;
import org.jointheleague.features.example.CurrentTime;
import org.jointheleague.features.example.RandomNumber;
import org.jointheleague.features.example.help_embed.HelpListener;


public class Bot  {
	
	// The string to show the custom :vomiting_robot: emoji
	public static String emoji = "<:vomiting_robot:642414033290657803>";

	private String token;
	private String channelName;
	DiscordApi api;
	HelpListener helpListener;

	public Bot(String token, String channelName) {
		this.token = token;
		this.channelName = channelName;
		helpListener = new HelpListener(channelName);
	}

	public void connect(boolean printInvite) {
		
		api = new DiscordApiBuilder().setToken(token).login().join();

		// Print the URL to invite the bot
		if (printInvite) {
			System.out.println("You can invite the bot by using the following url: " + api.createBotInvite());
		}

		api.getServerTextChannelsByName(channelName).forEach(e -> e.sendMessage("Bot Connected"));
		
		//add Listeners
		RandomNumber randomNumber = new RandomNumber(channelName);
		api.addMessageCreateListener(randomNumber);
		helpListener.addHelpEmbed(randomNumber.getHelpEmbed());

		ApiExample apiExample = new ApiExample(channelName);
		api.addMessageCreateListener(apiExample);
		helpListener.addHelpEmbed(apiExample.getHelpEmbed());

		CurrentTime currentTime = new CurrentTime(channelName);
		api.addMessageCreateListener(currentTime);
		helpListener.addHelpEmbed((currentTime.getHelpEmbed()));

		HighLowGame highLowGame = new HighLowGame(channelName);
		api.addMessageCreateListener(highLowGame);
		helpListener.addHelpEmbed(highLowGame.getHelpEmbed());

		api.addMessageCreateListener(helpListener);
	}
}
