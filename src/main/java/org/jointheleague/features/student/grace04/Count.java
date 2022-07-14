package org.jointheleague.features.student.grace04;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class Count extends Feature {

    public final String COMMAND = "!count";
    long tls = 0;
    long ts = 0;
    //String lm = COMMAND;
    boolean hasCom;
    String em = "0";
    int me = 0;

    public Count(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "start counting (from 0)"
        );
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if(!event.getMessageAuthor().getDisplayName().equals("ARealBot")){
            ts = System.currentTimeMillis();
            if (messageContent.startsWith(COMMAND)) {
                //respond to message here
                hasCom = true;
                em = "0";
                me = 0;
                event.getChannel().sendMessage("go");
            }
           /*Thread t;
           try{
               Thread.sleep(3000);
           }
           catch(Exception e){
               System.out.println(e);
           }*/
            if(hasCom) {
                event.getChannel().sendMessage("last message was: " + messageContent);
                if(!messageContent.equals(COMMAND)) {
                    try {
                        int x = Integer.parseInt(messageContent);
                    } catch (NumberFormatException e) {
                        event.getChannel().sendMessage("ur bad");
                        hasCom = false;
                    }
                    if (ts - tls > 3000) {
                        event.getChannel().sendMessage("go faster");
                    }
                    if (!messageContent.equals(em)) {
                        event.getChannel().sendMessage("wrong answer");
                        event.getChannel().sendMessage("should be: " + em);
                        hasCom = false;
                    } else {
                        event.getChannel().sendMessage("ur right");
                        me++;
                        em = "" + me;
                        event.getChannel().sendMessage("next # is: " + em);
                    }
                }
            }
            //event.getChannel().sendMessage("game ended");
            tls = ts;
        }
    }
}
