package org.jointheleague.features.student.second_feature;

import java.util.Random;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.jointheleague.features.templates.FeatureTemplate;

public class FeatureTwo extends FeatureTemplate {
    public final String COMMAND = "!blackjack";
    private final Random random = new Random();
    int numberToGuess;
    int yourValue = 0;
    int botValue = 0;

    public FeatureTwo(String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "Get as close to 21 as you can with your cards. 'Hit' means to ask for another card, stand means you are finished. Between us, whoever is closer to 21 wins!");
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();

        //start the game with the command
        if (messageContent.equals(COMMAND)) {
            String botCard = cardPicker();
            String yourCard = cardPicker();
            botValue += findValue(botCard);
            yourValue += findValue(yourCard);
            System.out.println("starting game");
            event.getChannel().sendMessage("My Card: " + botCard + "\n Your Card: " + yourCard + "\n !Blackjack hit or !Blackjack stand ?");
        } else if (messageContent.contains(COMMAND) && messageContent.contains("hit")) {
           if(botValue == 0) {
               event.getChannel().sendMessage("Please start the game before using this command.");
           } else {
               System.out.println("hitting");
               String yourCard2 = cardPicker();
               yourValue += findValue(yourCard2);
               event.getChannel().sendMessage("Your Next Card: " + yourCard2);
               event.getChannel().sendMessage("Your Total Card Value " + yourValue);
               if(yourValue > 21) {
                   event.getChannel().sendMessage("Bot wins! You busted!");
                   botValue = 0;
                   yourValue = 0;
               } else {
                   event.getChannel().sendMessage("!Blackjack hit or !Blackjack stand ?");
               }
           }
           } else if(messageContent.contains(COMMAND) && messageContent.contains("stand")) {
            if(botValue == 0) {
                event.getChannel().sendMessage("Please start the game before using this command.");
            } else {
                while(botValue < 17) {
                    String botCard2 = cardPicker();
                    botValue += findValue(botCard2);
                }
                   	if(yourValue > 21) {
                        event.getChannel().sendMessage("Bot wins! You busted!");
                    } else if(botValue > 21) {
                        event.getChannel().sendMessage("You win! Bot busted!");
                    }
                    if (botValue == yourValue) {
                        event.getChannel().sendMessage("Tie! You both scored " + yourValue);
                    } else if (botValue > yourValue) {
                        event.getChannel().sendMessage("Bot wins! Score: " + botValue);
                    } else if (botValue < yourValue) {
                        event.getChannel().sendMessage("You win! Score: " + botValue);
                    }
                    botValue = 0;
                    yourValue = 0;
                }
        }
        boolean end = false;
        while (!end) {
            if (messageContent.equalsIgnoreCase("!Hit")) {

            } else if (messageContent.equalsIgnoreCase("!Stand")) {
                end = true;
                if (yourValue > 21) {
                    event.getChannel().sendMessage("Bust! You lose.");
                } else {
                    if (botValue > yourValue) {
                        event.getChannel().sendMessage("Bot's Total Card Value: " + botValue);
                        event.getChannel().sendMessage("Bot wins! Score: " + botValue);
                    } else {
                        boolean end2 = false;
                        while (!end2) {
                            String botCard2 = cardPicker();
                            botValue += findValue(botCard2);
                            if (botValue == 20 || botValue == 21) {
                                if (botValue == yourValue) {
                                    event.getChannel().sendMessage("Tie! You both scored " + yourValue);
                                } else if (botValue > yourValue) {
                                    event.getChannel().sendMessage("Bot wins! Score: " + botValue);
                                } else if (botValue < yourValue) {
                                    event.getChannel().sendMessage("You win! Score: " + botValue);
                                }
                                end2 = true;
                            } else if (botValue > yourValue) {
                                event.getChannel().sendMessage("Bot wins! Score: " + botValue);
                                end2 = true;
                            } else if (botValue > 21) {
                                event.getChannel().sendMessage("Bot busted! You win! Score: " + botValue);
                            }
                        }
                    }
                }
            }
        }


    }

    public String cardPicker() {
        String cardName = "";
        numberToGuess = random.nextInt(10) + 2;
        if (numberToGuess == 10) {
            numberToGuess = random.nextInt(4);
            if (numberToGuess == 0) {
                cardName += "10";
            } else if (numberToGuess == 1) {
                cardName += "Jack";
            } else if (numberToGuess == 2) {
                cardName += "Queen";
            } else if (numberToGuess == 3) {
                cardName += "King";
            }
        } else if (numberToGuess == 11) {
            cardName += "Ace";
        } else {
            cardName += (numberToGuess);
        }
        numberToGuess = random.nextInt(4);
        if (numberToGuess == 0) {
            cardName += " of Hearts";
        } else if (numberToGuess == 1) {
            cardName += " of Diamonds";
        } else if (numberToGuess == 2) {
            cardName += " of Spades";
        } else if (numberToGuess == 3) {
            cardName += " of Clubs";
        }
        return cardName;
    }

    public int findValue(String card) {
        if (card.substring(0, 1).equals("2")) {
            return 2;
        } else if (card.substring(0, 1).equals("3")) {
            return 3;
        } else if (card.substring(0, 1).equals("4")) {
            return 4;
        } else if (card.substring(0, 1).equals("5")) {
            return 5;
        } else if (card.substring(0, 1).equals("6")) {
            return 6;
        } else if (card.substring(0, 1).equals("7")) {
            return 7;
        } else if (card.substring(0, 1).equals("8")) {
            return 8;
        } else if (card.substring(0, 1).equals("9")) {
            return 9;
        } else if (card.substring(0, 1).equals("10")) {
            return 10;
        } else if (card.substring(0, 3).equals("Ace")) {
            return 11;
        } else {
            return 10;
        }
    }
}
