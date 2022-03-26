package feature2.Nathan;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class Feature2 extends Feature {

	public final String COMMAND = "!animal";
	Random r = new Random();
	int state = 0;
	int animalNumbers;
	ArrayList<Integer> values = new ArrayList<Integer>();

	// put a variable here that keeps track of the # of times handle is called
	// this will ensure that the code picks the animal message that corresponds with
	// the calling variable's index for the distinct random # arraylist

	public Feature2(String channelName) {
		super(channelName);

		// Create a help embed to describe feature when !help command is sent
		helpEmbed = new HelpEmbed(COMMAND,
				"Give a brief description of your feature here, including how the user interacts with it");
	}

	public void distinctRandom() {
		for (int i = 0; i < 3; i++) {
			animalNumbers = r.nextInt(3);
			if (!values.contains(animalNumbers)) {
				values.add(animalNumbers);
			}
		}
		System.out.println(values);
	}

	@Override
	public void handle(MessageCreateEvent event) {
		// whenever I input a message, it should call handle
		String messageContent = event.getMessageContent();
		// if(event.getMessageAuthor().isYourself()){
		// if(event.getMessageAuthor()==this.);
		distinctRandom();
		for (int i = 0; i < 3; i++) {
			if (messageContent.startsWith(COMMAND)) {
				// animalNumbers=r.nextInt(9);
				// respond to message here
				// r.nextInt(10);
				if (state == 0) {

					// giraffe
					if (values.get(i) == 0) {
						event.getChannel().sendMessage("Long legs, short bodies, bony horns");
						if (!event.getMessageAuthor().isYourself()) {
							if (!event.getMessageContent().equals("!animal")) {
								// if(event.getMessageContent()!=guessMessage1) {
								if (event.getMessageContent().length() > 1) {
									state += 1;
									if (state == 1) {
										if (event.getMessageContent().equals("Giraffe")
												|| event.getMessageContent().equals("giraffe")) {
											event.getChannel().sendMessage("Correct!");
											state = 0;
										} else if (event.getMessageContent().length() > 1) {
											System.out.println(event.getMessageContent());
											event.getChannel()
													.sendMessage("Sorry, incorrect. The correct answer is a giraffe");
											state = 0;
										}
									}
								}
							}
						}
					}

					// monkey
					else if (values.get(i) == 1) {
						event.getChannel().sendMessage("forward facing eyes, grasping hands, nails, large brains");
						if (!event.getMessageAuthor().isYourself()) {
							if (!event.getMessageContent().equals("!animal")) {
								// if(event.getMessageContent()!=guessMessage1) {
								if (event.getMessageContent().length() > 1) {
									state += 1;
									if (state == 1) {
										if (event.getMessageContent().equals("monkey")
												|| event.getMessageContent().equals("Monkey")) {
											event.getChannel().sendMessage("Correct!");
											state = 0;
										} else if (event.getMessageContent().length() > 1) {
											System.out.println(event.getMessageContent());
											event.getChannel()
													.sendMessage("Sorry, incorrect. The correct answer is a monkey");
											state = 0;
										}
									}
								}
							}
						}
					}

					// alligator
					else if (values.get(i) == 2) {
						event.getChannel().sendMessage("long body, thick scales, short legs");
						if (!event.getMessageAuthor().isYourself()) {
							if (!event.getMessageContent().equals("!animal")) {
								// if(event.getMessageContent()!=guessMessage1) {
								if (event.getMessageContent().length() > 1) {
									state += 1;
									if (state == 1) {
										if (event.getMessageContent().equals("alligator")
												|| event.getMessageContent().equals("Alligator")) {
											event.getChannel().sendMessage("Correct!");
											state = 0;
										} else if (event.getMessageContent().length() > 1) {
											System.out.println(event.getMessageContent());
											event.getChannel()
													.sendMessage("Sorry, incorrect. The correct answer is a alligator");
											state = 0;
										}
									}
								}
							}
						}
					}

				}

			}
		}
	}
}

/*
 * //panda else if(animalNumbers==3) {
 * event.getChannel().sendMessage("black fur, black ears, white head"); }
 * 
 * //elephant else if(animalNumbers==4) {
 * event.getChannel().sendMessage("big head, wide ears, think skin"); }
 * 
 * //whale else if(animalNumbers==5) {
 * event.getChannel().sendMessage("long body, blue-gray colored skin"); }
 * 
 * //tiger else if(animalNumbers==6) {
 * event.getChannel().sendMessage("powerful carnivore, sharp teeth, agile body"
 * ); }
 * 
 * //llama else if(animalNumbers==7) {
 * event.getChannel().sendMessage("long neck, thick fur, split upper lip"); }
 * 
 * //kangaroo else if(animalNumbers==8) {
 * event.getChannel().sendMessage("muscular tail, large feet, long pointed ears"
 * ); }
 * 
 * //bear else if(animalNumbers==9) { event.getChannel().
 * sendMessage("large bodies, stocky legs, short tail, shaggy hair"); }
 * 
 * else {
 * event.getChannel().sendMessage("There is a problem. Look at animalNumbers.");
 * }
 * 
 */

// String guessMessage = messageContent.replaceAll(" ", "").replace(COMMAND,
// "");

/*
 * else if(animalNumbers==3) { if (event.getMessageContent().equals("panda") ||
 * event.getMessageContent().equals("Panda")) {
 * event.getChannel().sendMessage("Correct!"); state=0; } else if
 * (event.getMessageContent().length() > 1) {
 * System.out.println(event.getMessageContent()); event.getChannel().
 * sendMessage("Sorry, incorrect. The correct answer is a panda"); state=0; } }
 * 
 * else if(animalNumbers==4) { if (event.getMessageContent().equals("elephant")
 * || event.getMessageContent().equals("Elephant")) {
 * event.getChannel().sendMessage("Correct!"); state=0; } else if
 * (event.getMessageContent().length() > 1) {
 * System.out.println(event.getMessageContent()); event.getChannel().
 * sendMessage("Sorry, incorrect. The correct answer is a elephant"); state=0; }
 * }
 * 
 * else if(animalNumbers==5) { if (event.getMessageContent().equals("whale") ||
 * event.getMessageContent().equals("Whale")) {
 * event.getChannel().sendMessage("Correct!"); state=0; } else if
 * (event.getMessageContent().length() > 1) {
 * System.out.println(event.getMessageContent()); event.getChannel().
 * sendMessage("Sorry, incorrect. The correct answer is a whale"); state=0; } }
 * 
 * else if(animalNumbers==6) { if (event.getMessageContent().equals("tiger") ||
 * event.getMessageContent().equals("Tiger")) {
 * event.getChannel().sendMessage("Correct!"); state=0; } else if
 * (event.getMessageContent().length() > 1) {
 * System.out.println(event.getMessageContent()); event.getChannel().
 * sendMessage("Sorry, incorrect. The correct answer is a tiger"); state=0; } }
 * 
 * else if(animalNumbers==7) { if (event.getMessageContent().equals("llama") ||
 * event.getMessageContent().equals("Llama")) {
 * event.getChannel().sendMessage("Correct!"); state=0; } else if
 * (event.getMessageContent().length() > 1) {
 * System.out.println(event.getMessageContent()); event.getChannel().
 * sendMessage("Sorry, incorrect. The correct answer is a llama"); state=0; } }
 * 
 * else if(animalNumbers==8) { if (event.getMessageContent().equals("kangaroo")
 * || event.getMessageContent().equals("Kangaroo")) {
 * event.getChannel().sendMessage("Correct!"); state=0; } else if
 * (event.getMessageContent().length() > 1) {
 * System.out.println(event.getMessageContent()); event.getChannel().
 * sendMessage("Sorry, incorrect. The correct answer is a kangaroo"); state=0; }
 * }
 * 
 * else if(animalNumbers==9) { if (event.getMessageContent().equals("bear") ||
 * event.getMessageContent().equals("Bear")) {
 * event.getChannel().sendMessage("Correct!"); state=0; } else if
 * (event.getMessageContent().length() > 1) {
 * System.out.println(event.getMessageContent()); event.getChannel().
 * sendMessage("Sorry, incorrect. The correct answer is a bear"); state=0; } }
 */

// }

// List<Integer> animalNumbers = new ArrayList<Integer>(set);
