package org.jointheleague.features.examples.third_features;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.cat_facts_api.CatWrapper;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.springframework.web.reactive.function.client.WebClient;

import net.aksingh.owmjapis.api.APIException;
import reactor.core.publisher.Mono;

public class PremierLeagueScores extends Feature {

	public final String COMMAND = "!premScores";

	private WebClient webClient;
	private static final String baseUrl = "https://raw.githubusercontent.com/footballcsv/england/master/2010s/2019-20/eng.1.csv";

	public PremierLeagueScores(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "This returns Premier League Soccer scores");
		this.webClient = WebClient.builder().baseUrl(baseUrl).build();

		// TODO Auto-generated constructor stub
	}

	@Override
	public void handle(MessageCreateEvent event) {
		String messageContent = event.getMessageContent();
		if (messageContent.startsWith(COMMAND)) {
			String score = getScore();
			event.getChannel().sendMessage(score);
		}
	}

	public String getScore() {
		String message = "";
		ArrayList<String> arr = new ArrayList<String>();
		try {
			String workingDir = System.getProperty("user.dir");
			System.out.println(workingDir);
			
			BufferedReader premScores = new BufferedReader(new FileReader("/Users/jordanluo/Desktop/GitHub/DiscordBot_v2/eng1.txt"));
			message = premScores.readLine();
			
			while(message != null){
				System.out.print(message);
				arr.add(message);
				
			}
			
			
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int i = 0; i < arr.size(); i++) {

		}

		return message;
	}

}
