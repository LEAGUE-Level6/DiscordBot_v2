package org.jointheleague.features.examples.second_features;

import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

import net.aksingh.owmjapis.api.APIException;

public class QuoteGuesser extends Feature {
	
	Random r = new Random();
	int play;
	String[] playList = {"MacBeth", "Julius Caesar", "Hamlet", "Antony and Cleopatra", "The Tempest"};
	String[] quoteList = {"The bell intives me. Hear it not, Duncan; for it is a knell that summons thee to heaven or hell.", "The fault, dear Brutus, is not in our stars/ But in ourselves, that we are underlings.", "The Lady doth protest too much, methinks.", "Finish, good lady; the bright day is done,/ And we are for the Dark.", "My library was dukedom large enough."};
	
	public final String COMMAND = "!shakespeareGuess";
	boolean quoteGuessed = true;
	public QuoteGuesser(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Enter the initial command to get a quote."
				+ "Then, re-enter the command, along with a guess at the play it comes from.");
		
	}
	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		// TODO Auto-generated method stub
		String messageContent = event.getMessageContent();
		
		if(messageContent.equals(COMMAND)) {
			play = r.nextInt(5);
			event.getChannel().sendMessage(quoteList[play]);
			quoteGuessed = false;
		}
		
		else if(messageContent.contains(COMMAND)&&quoteGuessed==false) {
			String guess = messageContent.replace(COMMAND, "").replaceAll(" ", "");
			if(guess.equalsIgnoreCase(playList[play].replaceAll(" ", ""))) {
				event.getChannel().sendMessage("Correct! The quotation was from " + playList[play] + ".");
				quoteGuessed = true;
			}
		}
	}
}
