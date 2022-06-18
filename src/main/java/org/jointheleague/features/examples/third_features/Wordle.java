package org.jointheleague.features.examples.third_features;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import javax.swing.JOptionPane;

import org.javacord.api.event.message.MessageCreateEvent;

import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.cat_facts_api.CatWrapper;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class Wordle extends Feature {

	ArrayList<String> words = new ArrayList<String>();
	boolean userHasGuessed=false;
	String wordleWord="";



	public final String COMMAND = "!wordle";

	public Wordle(String channelName) {
		super(channelName);
		helpEmbed = new HelpEmbed(COMMAND, "Allows you to play wordle!");

	}
	public String getRandomWord() {
		if(words.isEmpty()) {
			 readFromFile();
		}
			Random randNum= new Random();
			return words.get(randNum.nextInt(words.size()));
	}

	public void readFromFile() {

	//	String randomWord=" ";
		try {
			BufferedReader reader = new BufferedReader(new FileReader("word_list.txt"));
			String line = reader.readLine();
			while (line != null) {
				String[] wordsLine = line.split(" ");
				for (String word : wordsLine) {
					if(word.length()==5) {
					words.add(word);
					}
				}
				line = reader.readLine();
			}

			reader.close();
		} catch (Exception e) {

		}


	}

	@Override
	public void handle(MessageCreateEvent event) {
		
		int numberToGuess = 0;
		String wordleGuess="";
		//System.out.println(getRandomWord());
		if (userHasGuessed==false) {
		wordleWord = getRandomWord();
		}

		//System.out.println("WORDLE");
		String messageContent = event.getMessageContent();
		if (messageContent.startsWith(COMMAND) && messageContent.endsWith(COMMAND)) {
			userHasGuessed=true;
			System.out.println(wordleWord);
			event.getChannel().sendMessage("Take a guess! ");
			System.out.println("The wordle word is: "+wordleWord);
		} else if (messageContent.startsWith(COMMAND) && !messageContent.endsWith(COMMAND)) {
			userHasGuessed=true;
			wordleGuess = messageContent.replaceAll(" ", "").replace(COMMAND, "");
			
			System.out.println("guess: "+wordleGuess +". Real word: "+wordleWord);
			//the wordle word changes between lines 83 and 86...
			
			if (wordleGuess.equalsIgnoreCase(wordleWord)) {
				System.out.println("CORRECT");
				userHasGuessed=false;
				//get this to work
				event.getChannel().sendMessage("CORRECT!");
				}else {
				
					for (int i = 0; i < 5; i++) {
						if(wordleGuess.charAt(i)==wordleWord.charAt(i)) {
						event.getChannel().sendMessage(wordleGuess.charAt(i)+" is the correct letter in the right spot");
						System.out.println(wordleGuess.charAt(i)+" is the correct letter in the right spot");}
			
						else if (wordleWord.contains(wordleGuess.charAt(i) + "")) {
							event.getChannel()
								.sendMessage(wordleGuess.charAt(i) + " is the correct letter in the wrong spot");
						System.out.println(wordleGuess.charAt(i) + " is the correct letter in the wrong spot");
					} else {
						event.getChannel()
						.sendMessage(wordleGuess.charAt(i) + " is not used :(");
				System.out.println(wordleGuess.charAt(i) + " is not used :(");
					}
					}
					
					}
					 
		}
						
					
					
				}
		
			}
		
	
