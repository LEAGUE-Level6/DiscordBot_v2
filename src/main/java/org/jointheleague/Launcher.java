package org.jointheleague;

import org.jointheleague.discord_bot.DiscordBot;

public class Launcher {
    public static void main(String[] args) throws InterruptedException {

        //Initialize variables
        String channelName = "eddie";
        String discordToken = "";
        boolean printDiscordInvite = true;

        //Instantiate DiscordBot and connect
        DiscordBot discordBot =  new DiscordBot(discordToken, channelName);
        discordBot.connect(printDiscordInvite);

    }
}
