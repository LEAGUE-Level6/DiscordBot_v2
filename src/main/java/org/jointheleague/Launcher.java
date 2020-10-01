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
		DiscordBot discordBot = DiscordBot.fromConfigJsonFile();

		boolean printBotAuthorizationInvite =  true;
		discordBot.connect(printBotAuthorizationInvite);
	}
}
