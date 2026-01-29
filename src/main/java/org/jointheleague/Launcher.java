package org.jointheleague;

import org.jointheleague.discord_bot.DiscordBot;

import java.util.Scanner;

public class Launcher {
    public static void main(String[] args) throws InterruptedException {

        //Initialize variables
        String channelName = System.getenv("CHANNEL_NAME");
        String discordToken = System.getenv("DISCORD_TOKEN");
        boolean printDiscordInvite = false;

        //Instantiate DiscordBot and connect
        DiscordBot discordBot =  new DiscordBot(discordToken, channelName);
        discordBot.connect(printDiscordInvite);
        Scanner scan = new Scanner(System.in);
       /* while(true) {
            String in = scan.nextLine();
            System.out.println("CONSOLE:"+in);
            if (!in.isEmpty()) {
                discordBot.sendMessage(in);
            }
        }*/
    }
}
