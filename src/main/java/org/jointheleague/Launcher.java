package org.jointheleague;

import org.jointheleague.discord_bot.DiscordBot;

public class Launcher {
    public static void main(String[] args) throws InterruptedException {

        //Initialize variables
        String channelName = "eddie";
        String discordToken = "MTI2MTEyMjg0MTk0MDcyNTg3NQ.GoMBCe.Noh6EkpP7UnKB__QSNWPzTL33NHozv_2YnBQSE";
        boolean printDiscordInvite = true;

        //Instantiate DiscordBot and connect
        DiscordBot discordBot =  new DiscordBot(discordToken, channelName);
        discordBot.connect(printDiscordInvite);

    }
}
