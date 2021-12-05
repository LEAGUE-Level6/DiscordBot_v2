package org.jointheleague.discord_bot;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class plus extends Feature {

    public final String COMMAND = "!plus";
    public int ans =0;

    public plus(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "plius"
        );
    }
    int amount=0;
    int[] list;
    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();

        if (messageContent.startsWith(COMMAND)) {
            //respond to message here
            if (ans == 0) {
                if (messageContent.replaceAll(" ", "").replace(COMMAND, "").equals("")) {
                    amount = (int) (Math.random() * 10);
                    list = new int[amount];
                    ans = 0;
                    String ques = "";
                    for (int i = 0; i < amount; i++) {
                        list[i] = (int) (Math.random() * 100);
                        ans += list[i];
                        ques += list[i] + "+";
                    }
                    ques= ques.substring(0, ques.length() - 1);
                    event.getChannel().sendMessage(ques);
                }
                else event.getChannel().sendMessage("._.");
            } else {
                String guessMessage = messageContent.replaceAll(" ", "").replace(COMMAND, "");
                int aye = 0;
                if (guessMessage.equals("")){
                    //I KNOW I CAN SHORTEN THIS BY SO MUCH BUT I'M REALLY TOO LAZY
                    amount = (int) (Math.random() * 10);
                    list = new int[amount];
                    ans = 0;
                    String ques = "";
                    for (int i = 0; i < amount; i++) {
                        list[i] = (int) (Math.random() * 100);
                        ans += list[i];
                        ques += list[i] + "+";
                    }
                    ques=  ques.substring(0, ques.length() - 1);
                    event.getChannel().sendMessage(ques);
                }
                else{
                try {
                    aye = Integer.parseInt(guessMessage);
                } catch (NumberFormatException e) {
                }
                 if (ans == aye) {
                    event.getChannel().sendMessage("correct");
                    ans=0;
                }
                else event.getChannel().sendMessage("you suck");
                }
            }
        }
    }

}
