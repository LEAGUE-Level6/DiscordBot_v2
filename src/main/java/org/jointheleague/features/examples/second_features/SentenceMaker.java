package org.jointheleague.features.examples.second_features;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class SentenceMaker extends Feature {
    public final String COMMAND = "!maker";

    public int check = 0;
    public String name;
    public String verb;
    public int number;
int num = 1;
    public SentenceMaker(String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "just do what the bot says if you cant figure it out your dumb");
    }

    @Override
    public void handle(MessageCreateEvent event) {

        String messageContent = event.getMessageContent();
        if (messageContent.equals(COMMAND)) {
            event.getChannel().sendMessage("ah hello give me a name, then a verb, then a number (example: !maker joe, then you say, !maker jumped)");
            check = 1;
        }
        else if(messageContent.contains(COMMAND) && !messageContent.contains("example")) {
            if(check == 0){
                //tell them to start the game first
                event.getChannel().sendMessage("Please start the game first using just the command");
                return;
            }


            String answer = messageContent.replaceAll(" ", "").replace(COMMAND, "");
            if(num == 1){
                name = answer;
                event.getChannel().sendMessage("ok");
                num++;
            }
            else if(num ==2){
                verb = answer;
                event.getChannel().sendMessage("ok 2");
                num++;
            }
            else if(num ==3){
                number = Integer.parseInt(answer);
                event.getChannel().sendMessage("ok 3");
                event.getChannel().sendMessage("there once was a person named " + name + " who " + verb + " " + number +
                        " times.");
                num = 0;
            }
        }


    }
}
