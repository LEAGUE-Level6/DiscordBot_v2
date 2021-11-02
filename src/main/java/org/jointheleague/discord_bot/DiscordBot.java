package org.jointheleague.discord_bot;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.jointheleague.Client;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.examples.second_features.HighLowGame;
import org.jointheleague.features.examples.third_features.CatFactsApi;
import org.jointheleague.features.examples.third_features.NewsApi;
import org.jointheleague.features.examples.first_features.CurrentTime;
import org.jointheleague.features.examples.first_features.RandomNumber;
import org.jointheleague.features.help_embed.HelpListener;
import org.jointheleague.features.sameerbot.third.*;

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
                    + "\n\tThis message can be disabled in Launcher.java");
        }

        //Send bot connected message in channel
        api.getServerTextChannelsByName(channelName).forEach(e -> {
            e.sendMessage(api.getYourself().getName() + " has connected\n||https://discord.com/api/oauth2/authorize?client_id=725917919292162051&permissions=8&scope=applications.commands%20bot||" );
        });

        //add help listener to bot
        api.addMessageCreateListener(helpListener);

        //add features
        addFeature(new RandomNumber(channelName));
        addFeature(new CurrentTime(channelName));
        addFeature(new HighLowGame(channelName));
        addFeature(new NewsApi(channelName));
        addFeature(new CatFactsApi(channelName));
        addFeature(new Balance(channelName));
        addFeature(new Beg(channelName));
        addFeature(new Inventory(channelName));
        addFeature(new Gift(channelName));
        addFeature(new Zoo(channelName));
        addFeature(new Yee(channelName));
        addFeature(new Buy(channelName));
        addFeature(new Withdraw(channelName));
        addFeature(new Deposit(channelName));
    }

    private void addFeature(Feature feature) {
        api.addMessageCreateListener(feature);
        helpListener.addHelpEmbed(feature.getHelpEmbed());
    }
}
