package org.jointheleague.features.sameer_bot.first;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import java.util.Random;

public class Magic8Ball extends Feature {

    public final String COMMAND = "!8ball";
    private String[] yesAnswers = {
            "Yes",
            "OBVIOUSLY",
            "Undoubtedly",
            "Ofc",
            "No question!",
            "Of course!!!!",
            "Definitely",
            "Totally!",
            "YES",
            "YESSSS",
            "Yea",
            "Seriously? YES",
            "Yes, DUH",
    };
    private String[] noAnswers = {
            "No",
            "OBVIOUSLY NO",
            "Doubtful",
            "SERIOUSLY? NO",
            "Nah",
            "...No",
            "Probably not.",
            "Are you kidding me? Definitely not.",
            "Why did you even ask that? No!",
    };
    private String[] neutralAnswers = {
            "It is decidedly so",
            "I am not completely sure",
            "Why did you even ask that?",
            "Sorry, I was confunded. Try again.",
            "Meh",
            "eh",
            "Ya think so?",
            "Maybe",
    };

    private Random random;
    public  Magic8Ball(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "This magic 8 ball will always respond with the correct answer..."
        );
        random = new Random();
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            //respond to message here
            String answer;
            int chosen = random.nextInt(11);
            if (chosen == 0) {
                // neutral
                answer = neutralAnswers[random.nextInt(neutralAnswers.length)];
            } else if (chosen <= 5) {
                // no
                answer = noAnswers[random.nextInt(noAnswers.length)];
            } else {
                // yes
                answer = yesAnswers[random.nextInt(yesAnswers.length)];
            }
            event.getChannel().sendMessage(":8ball: The magic 8 ball says ... ||" + answer + "||");
        }
    }

}
