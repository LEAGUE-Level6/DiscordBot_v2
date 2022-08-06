package feature1.Nathan;


import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;

public class Feature1 extends Feature{
	public final String COMMAND = "!greeting";

    public Feature1(String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "Allows the user to get greeted by the bot upon command");
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
        	event.getChannel().sendMessage("Good afternoon!");
        }
    }

}
