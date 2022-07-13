package org.jointheleague;

import org.jointheleague.discord_bot.DiscordBot;

public class Launcher {
    public static void main(String[] args) {

    	
    	
        //Initialize variables
        String channelName = System.getenv("CHANNEL_NAME");
        String discordToken = System.getenv("DISCORD_TOKEN");
        //System.out.println(channelName + "  " + discordToken);
        boolean printDiscordInvite = false;

        //Instantiate DiscordBot and connect
        DiscordBot discordBot =  new DiscordBot(discordToken, channelName);
        discordBot.connect(printDiscordInvite);

    }
}
