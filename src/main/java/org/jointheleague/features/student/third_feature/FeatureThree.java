package org.jointheleague.features.student.third_feature;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.event.interaction.MessageComponentCreateEvent;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.interaction.MessageComponentInteraction;
import org.javacord.api.listener.interaction.MessageComponentCreateListener;
import org.jointheleague.discord_bot.DiscordBot;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.jointheleague.features.templates.FeatureTemplate;

public class FeatureThree extends FeatureTemplate {
    public final String COMMAND = "!fish";

    TextChannel channel;
    public double money = 0;



    public FeatureThree(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "A luck based fishing game with lots of fish, sizes of fish, qualities of fish, upgrades, and more. If new to !\u200Efish, please start with !\u200Efish tutorial. REMEMBER TO ADD A SAVING FEATURE TO ADD A TEXT KEY TO RESTORE PROGRESS"
        );
    }

    @Override
    public void handle(MessageCreateEvent event) {
        channel = event.getChannel();
        String messageContent = event.getMessageContent();
        if (messageContent.equals(COMMAND) || messageContent.equalsIgnoreCase(COMMAND+" tutorial")) {
            //respond to message here
            event.getChannel().sendMessage("Welcome to !\u200Efish. In this game you will fish, like the name implies. You will continue to fish and gain money. With this money you can upgrade you rod, luck, hook, bait, et cetera. With all of this you should get new better fish. This journey begins the command !\u200Efish menu \nFish Size Value Multiplication Chart: PLACEHOLDER \nRarity Value Multiplication Chart: PLACEHOLDER \n*BETA TUTORIAL*");
        }
        else if (messageContent.equalsIgnoreCase(COMMAND + " menu")){
           // event.getChannel().sendMessage("Message to test whether TextChannel broke again.");
            new MessageBuilder().setContent("Current Money: " + money+" | Current Location: PLACEHOLDER | Luck Modifier: PLACEHOLDER").addComponents(ActionRow.of(Button.success("fish", "Go Fishing"),Button.secondary("modify", "Modify Set-Up"),Button.secondary("save", "Save Game"))).send(channel);
            event.getApi().addMessageComponentCreateListener(event2 -> {
               MessageComponentInteraction mci = event2.getMessageComponentInteraction();
               String cID = mci.getCustomId();

               switch(cID){
                   case "fish":

                       event.getChannel().sendMessage("FISHING");
                       break;
                   case "modify":
                       event.getChannel().sendMessage("CHANGING");
                       break;
                   case "save" :
                       event.getChannel().sendMessage("SAVING");
                       break;
               }


            });
        }
    }
}
