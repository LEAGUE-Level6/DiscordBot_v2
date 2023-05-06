package org.jointheleague.features.examples.second_features;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import java.util.Random;
import java.util.ArrayList;
public class GuessTheCookie extends Feature {
    public final String COMMAND = "!CookieGuesser";
    ArrayList<String> cookieTypes = new ArrayList<String>();
    Random ran = new Random();
    int random;
    public GuessTheCookie(String channelName) {
        super(channelName);
        cookieTypes.add("https://static.wikia.nocookie.net/cookieclicker/images/b/bb/Plain_cookies.png/revision/latest/scale-to-width-down/30?cb=20160217140558"); //plain
        cookieTypes.add("https://static.wikia.nocookie.net/cookieclicker/images/5/5a/Sugar_cookies.png/revision/latest?cb=20160217141253"); //sugar
        cookieTypes.add("https://static.wikia.nocookie.net/cookieclicker/images/1/1e/Oatmeal_raisin_cookies.png/revision/latest?cb=20160217140448"); //Oatmeal Raisin


        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "Is it a bird? Is it plane? IT'S A COOKIE"
        );
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String plainCookie= "https://static.wikia.nocookie.net/cookieclicker/images/b/bb/Plain_cookies.png/revision/latest/scale-to-width-down/30?cb=20160217140558";
        String messageContent = event.getMessageContent();
        if (messageContent.equals(COMMAND)) {
            //respond to message here
            random=ran.nextInt(cookieTypes.size());
            event.getChannel().sendMessage("What cookie is this?");
            event.getChannel().sendMessage(cookieTypes.get(random));


        }
        else if(messageContent.contains(COMMAND)
                && !messageContent.contains("e.g.")
                && !messageContent.contains("this:")){
            String guessMessage = messageContent.replace(COMMAND, "").trim();
            if(cookieTypes.get(random).equalsIgnoreCase("https://static.wikia.nocookie.net/cookieclicker/images/b/bb/Plain_cookies.png/revision/latest/scale-to-width-down/30?cb=20160217140558")){
                if(guessMessage.equalsIgnoreCase("Plain Cookie")){
                    event.getChannel().sendMessage("CORRECT!");
                }
                else{
                    event.getChannel().sendMessage("INCORRECT!");
                }
            }
            if(cookieTypes.get(random).equalsIgnoreCase("https://static.wikia.nocookie.net/cookieclicker/images/5/5a/Sugar_cookies.png/revision/latest?cb=20160217141253")){
                if(guessMessage.equalsIgnoreCase("Sugar Cookie")){
                    event.getChannel().sendMessage("CORRECT!");
                }
                else{
                    event.getChannel().sendMessage("INCORRECT!");
                }
            }
            if(cookieTypes.get(random).equalsIgnoreCase("https://static.wikia.nocookie.net/cookieclicker/images/1/1e/Oatmeal_raisin_cookies.png/revision/latest?cb=20160217140448")){
                if(guessMessage.equalsIgnoreCase("Oatmeal Raisin Cookie")){
                    event.getChannel().sendMessage("CORRECT!");
                }
                else{
                    event.getChannel().sendMessage("INCORRECT!");
                }
            }
           // event.getChannel().sendMessage(guessMessage);
        }
    }
}
