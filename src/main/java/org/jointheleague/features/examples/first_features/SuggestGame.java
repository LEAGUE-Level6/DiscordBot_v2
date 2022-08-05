package org.jointheleague.features.examples.first_features;

import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import net.aksingh.owmjapis.api.APIException;

public class SuggestGame extends Feature {

	public final String COMMAND = "!suggestion";
	public final String[] GAMES = {"https://www.nytimes.com/games/wordle/index.html", "https://wafflegame.net/", "https://offline-dino-game.firebaseapp.com/", "https://www.google.com/doodles/valentines-day-2017-day-1"};
	
	public SuggestGame(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Suggests an online game to play");
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		String message = event.getMessageContent();
		if (message.startsWith(COMMAND)) {
            Random r = new Random();
            event.getChannel().sendMessage(GAMES[r.nextInt(4)]);
        }
	}
}
