package org.jointheleague.features.student;

import java.util.ArrayList;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class MemoList extends Feature{
	public final String COMMAND1 = "!list";
	public final String COMMAND2 = "!add";
	public final String COMMAND3 = "!remove";

    public MemoList(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND1,
                ""
        );
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        ArrayList<String> list = new ArrayList<String>();
        String temp = messageContent.substring(messageContent.indexOf(" ")+1);
        try {
        	if (messageContent.startsWith(COMMAND1)) {
                //Shows the list
        		int index = Integer.parseInt(temp);
        		String listComp = "";
            	for (int i = (index*5)-5; i <= (index*5); i++) {
					if(i<list.size()) {
						listComp += list.get(i) + "\n";
					}
				}
                event.getChannel().sendMessage(listComp);
            }
        	
            if (messageContent.startsWith(COMMAND2)) {
                //Adds to the list
            	
            	list.add(temp);
                event.getChannel().sendMessage("Added \"" +temp+"\" to the list.");
            }
            
            if (messageContent.startsWith(COMMAND3)) {
                //removes something from the list
            	int index = Integer.parseInt(temp);
            	list.remove(index);
                event.getChannel().sendMessage("Removed \"" +temp+"\" from the list.");
            }
            
		} catch (NumberFormatException e) {
			event.getChannel().sendMessage("Command not formatted correctly");
		}
        
        
    }
}
