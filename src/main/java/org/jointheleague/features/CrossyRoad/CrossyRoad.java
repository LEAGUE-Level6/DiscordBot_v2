package org.jointheleague.features.CrossyRoad;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.swing.Timer;

import org.javacord.api.entity.message.MessageAuthor;
import org.javacord.api.entity.message.embed.Embed;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class CrossyRoad extends Feature {
	public final String COMMAND = "!road";
	HashMap<MessageAuthor, Holder> games = new HashMap<MessageAuthor, Holder>();
	MessageAuthor currentSetup = null;

	public CrossyRoad(String channelName) {
		super(channelName);
		// Create a help embed to describe feature when !help command is sent
		helpEmbed = new HelpEmbed(COMMAND,
				"This is a discord bot which allows you to play Crossy Road!\n"
				+"!road start //starts the game\n"
				+"!road end //finishes your active game\n"
				+"!road leaderboard (all) //shows the top 5 players, or all\n"
				+"!road submit {3 letter name code} //submits your score on the leaderboard");
	}

	@Override
	public void handle(MessageCreateEvent event) {
		String messageContent = event.getMessageContent();
		MessageAuthor auth = event.getMessageAuthor();
		if (event.getMessageAuthor().isYourself() && messageContent.equals("setup")) {
			games.put(currentSetup, setUpGame(event));
			games.get(currentSetup).t.start();
			currentSetup = null;
		} else {
		if (messageContent.startsWith(COMMAND)) {
			if (messageContent.contentEquals(COMMAND + " start")) {
				if (!games.containsKey(auth)) {
					currentSetup = auth;
					event.getChannel().sendMessage("setup");
				} else if (games.get(auth).r.dead) {
					games.remove(auth);
					currentSetup = auth;
					event.getChannel().sendMessage("setup");
				} else {
					event.getChannel().sendMessage(
							"You are already in a game, either do !road end or finish the game before starting a new game");
				}
			} else if ((COMMAND + " end").equals(messageContent)) {
				games.get(auth).r.dead = true;
			} else if ((COMMAND + " leaderboard").equals(messageContent)) {
				readBoard(event, false);
			} else if ((COMMAND + " leaderboard all").equals(messageContent)) {
				readBoard(event, true);
			} else if (messageContent.startsWith(COMMAND + " submit ") && messageContent.length() == 16) {
				if (games.containsKey(auth) && games.get(auth).r.dead) {
					submitScore(messageContent.substring(13), event, games.get(auth).r.score);
				} else {
					event.getChannel().sendMessage("You currently have no game to submit");
				}
			}
		}
	}
	}

	private void submitScore(String name, MessageCreateEvent event, int score) {
		ArrayList<String> scores = new ArrayList<String>();
		try {
			BufferedReader r = new BufferedReader(new FileReader(
					"/Users/league/git/DiscordBot_v2/src/main/java/org/jointheleague/features/CrossyRoad/leaderboard"));
			String w = r.readLine();
			while (w != null) {
				scores.add(w);
				w = r.readLine();
			}
			boolean e = true;
			for (int i = 0; i < scores.size(); i++) {
				if (score > Integer.parseInt(scores.get(i).substring(6))) {
					scores.add(i, (name + " - " + score));
					e = false;
					break;
				}
			}
			if (e) {
				scores.add(name + " - " + score);
			}
			r.close();
			FileWriter x = new FileWriter(
					"/Users/league/git/DiscordBot_v2/src/main/java/org/jointheleague/features/CrossyRoad/leaderboard");
			String fin = "";
			for (int i = 0; i < scores.size() - 1; i++) {
				fin += scores.get(i) + "\n";
			}
			fin += scores.get(scores.size() - 1);
			x.write(fin);
			x.close();
			{
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void readBoard(MessageCreateEvent event, boolean all) {
		try {
			String message = "";
			BufferedReader r = new BufferedReader(new FileReader(
					"/Users/league/git/DiscordBot_v2/src/main/java/org/jointheleague/features/CrossyRoad/leaderboard"));
			if (all) {
				String w = "";
				while (w != null) {
					message += w + "\n";
					w = r.readLine();
				}
			} else {
				for (int i = 0; i < 5; i++) {
					message += r.readLine() + "\n";
				}
			}
			r.close();
			event.getChannel().sendMessage(message);
		} catch (Exception e) {
			event.getChannel().sendMessage("Failed to get leaderboard");
		}
	}

	private Holder setUpGame(MessageCreateEvent e) {
		Row[] rows = new Row[7];
		for (int i = 0; i < rows.length - 1; i++) {
			int x = new Random().nextInt(10);
			if (x < 5) {
				rows[i] = new Row(0, false);
			} else if (x < 7) {
				rows[i] = new Row(1, false);
			} else if (x < 9) {
				rows[i] = new Row(2, false);
			} else {
				rows[i] = new Row(3, false);
			}
		}
		rows[6] = new Row(0, true);
		runningGame r = new runningGame(rows, e, currentSetup);
		return new Holder(new Thread(r), r);
	}
}
