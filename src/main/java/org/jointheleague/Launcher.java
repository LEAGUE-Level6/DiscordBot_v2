package org.jointheleague;

import ch.qos.logback.core.subst.Token;
import org.jointheleague.discord_bot.DiscordBot;

public class Launcher {
    public static void main(String[] args) throws InterruptedException {

        //Initialize variables
        String channelName = "eddie";
        String discordToken = "MTI2MTEyMjg0MTk0MDcyNTg3NQ.Gixyau.20thV4-bN7TzOTt7QiXGm9Jg_D7tQc_bCfnUTM";
        boolean printDiscordInvite = true;

        //Instantiate DiscordBot and connect
        DiscordBot discordBot =  new DiscordBot(discordToken, channelName);
        discordBot.connect(printDiscordInvite);

    }
}
