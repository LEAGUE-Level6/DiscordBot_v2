package org.jointheleague.features.student;

import javax.annotation.Generated;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ChessClasses.PlayerInfo;
import ChessClasses.PlayerStats;
import reactor.core.publisher.Mono;

public class ChessStats extends Feature {

	// ability for user to choose either player INFO or STATS, then username, ALL
	// stats are printed out in nested format

	public final String COMMAND = "!chess";
	private PlayerStats last;

	public ChessStats(String channelName) {
		super(channelName);

		// Create a help embed to describe feature when !help command is sent
		helpEmbed = new HelpEmbed(COMMAND,
				"Get stats about players on Chess.com using !chess [stats OR info] [username]");
	}

	@Override
	public void handle(MessageCreateEvent event) {
		String messageContent = event.getMessageContent();
		if (messageContent.startsWith(COMMAND)) {
			// should be !chess info/stats name
			String split[] = messageContent.split(" ");
			if (split.length != 3) {
				event.getChannel().sendMessage("Invalid Syntax!");
				event.getChannel()
						.sendMessage("Get stats about players on Chess.com using !chess [stats OR info] [username]");
			} else {

				if (split[1].equalsIgnoreCase("info")) {
					// print info
					final String URL = "https://api.chess.com/pub/player/" + split[2];
					WebClient w = WebClient.builder().baseUrl(URL)
							.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
					PlayerInfo c = w.get().retrieve().bodyToMono(PlayerInfo.class).block();
					String print = split[2] + ":\n  ID: " + c.getId() + "\n  URL: " + c.getUrl() + "\n  Country: "
							+ c.getCountry() + "\n  Streamer?: " + c.getIsStreamer() + "\n  Verified?: "
							+ c.getVerified();
					event.getChannel().sendMessage(print);
				} else if (split[1].equalsIgnoreCase("stats")) {
					// print stats
					final String URL = "https://api.chess.com/pub/player/" + split[2] + "/stats";
					WebClient w = WebClient.builder().baseUrl(URL)
							.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).build();
					PlayerStats s = w.get().retrieve().bodyToMono(PlayerStats.class).block();
					last = s;
					event.getChannel().sendMessage("Choose a command:\n!Bullet\n!Blitz\n!Rapid\n!Daily");

				} else {
					event.getChannel().sendMessage("Invalid Syntax!");
				}
			}
		} else if (messageContent.equalsIgnoreCase("!Blitz")) {

			if (last == null) {
				event.getChannel().sendMessage("Invalid Usage!");
			} else if (last.getChessBlitz() == null) {
				event.getChannel().sendMessage("This player hasn't played Blitz!");
			} else {
				event.getChannel()
						.sendMessage("Current: \n  Rating: " + last.getChessBlitz().getLast().getRating()
								+ "\nBest: \n  Rating: " + last.getChessBlitz().getBest().getRating() + "\n  Game: "
								+ last.getChessBlitz().getBest().getGame() + "\nRecord: \n  Wins: "
								+ last.getChessBlitz().getRecord().getWin() + "\n  Losses: "
								+ last.getChessBlitz().getRecord().getLoss() + "\n  Draws: "
								+ last.getChessBlitz().getRecord().getDraw());
			}
		} else if (messageContent.equalsIgnoreCase("!Bullet")) {

			if (last == null) {
				event.getChannel().sendMessage("Invalid Usage!");
			} else if (last.getChessBullet() == null) {
				event.getChannel().sendMessage("This player hasn't played Bullet!");
			} else {
				event.getChannel()
						.sendMessage("Current: \n  Rating: " + last.getChessBullet().getLast().getRating()
								+ "\nBest: \n  Rating: " + last.getChessBullet().getBest().getRating() + "\n  Game: "
								+ last.getChessBullet().getBest().getGame() + "\nRecord: \n  Wins: "
								+ last.getChessBullet().getRecord().getWin() + "\n  Losses: "
								+ last.getChessBullet().getRecord().getLoss() + "\n  Draws: "
								+ last.getChessBullet().getRecord().getDraw());
			}
		} else if (messageContent.equalsIgnoreCase("!Daily")) {

			if (last == null) {
				event.getChannel().sendMessage("Invalid Usage!");
			} else if (last.getChessDaily() == null) {
				event.getChannel().sendMessage("This player hasn't played Daily!");
			} else {
				event.getChannel()
						.sendMessage("Current: \n  Rating: " + last.getChessDaily().getLast().getRating()
								+ "\nBest: \n  Rating: " + last.getChessDaily().getBest().getRating() + "\n  Game: "
								+ last.getChessDaily().getBest().getGame() + "\nRecord: \n  Wins: "
								+ last.getChessDaily().getRecord().getWin() + "\n  Losses: "
								+ last.getChessDaily().getRecord().getLoss() + "\n  Draws: "
								+ last.getChessDaily().getRecord().getDraw());
			}
		} else if (messageContent.equalsIgnoreCase("!Rapid")) {

			if (last == null) {
				event.getChannel().sendMessage("Invalid Usage!");
			} else if (last.getChessRapid() == null) {
				event.getChannel().sendMessage("This player hasn't played Rapid!");
			} else {
				event.getChannel()
						.sendMessage("Current: \n  Rating: " + last.getChessRapid().getLast().getRating()
								+ "\nBest: \n  Rating: " + last.getChessRapid().getBest().getRating() + "\n  Game: "
								+ last.getChessRapid().getBest().getGame() + "\nRecord: \n  Wins: "
								+ last.getChessRapid().getRecord().getWin() + "\n  Losses: "
								+ last.getChessRapid().getRecord().getLoss() + "\n  Draws: "
								+ last.getChessRapid().getRecord().getDraw());
			}
		}

	}

	String convertDate(int m) {
		String date = new java.text.SimpleDateFormat("MM/dd/yyyy").format(new java.util.Date(m / 1000));
		return date;
	}

}
