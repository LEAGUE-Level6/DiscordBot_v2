package org.jointheleague;

import org.jointheleague.discord_bot.DiscordBot;

/**
 * Main method for discord bot
 * @author
 * keithgroves
 * Matt Freedman (@mjfre)
 * and https://tinystripz.com
 *
 */

public class Launcher {
	public static void main(String[] args) {

		//Initialize discord bot
		String token = "TOKEN";
		String channelName = "CHANNEL_NAME";
		DiscordBot discordBot = new DiscordBot(token, channelName);

		//Connect discord bot to channel
		boolean printBotAuthorizationInvite =  true;
		discordBot.connect(printBotAuthorizationInvite);

	}
}
