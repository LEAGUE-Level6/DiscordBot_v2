package org.jointheleague.features.student.questions;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.student.questions.QuestionsWrapper;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.List;

public class Questions extends Feature {
    public final String COMMAND = "!questionsapi";

    private WebClient webClient;
    private static final String baseUrl = "https://opentdb.com/api.php";

    public Questions(String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "Question");

        this.webClient = WebClient
                .builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            String question = getQuestionAndAnswer()[0];
            String answer = getQuestionAndAnswer()[1];
            event.getChannel().sendMessage(question);
            if(messageContent.contains(answer)) {
                event.getChannel().sendMessage("Good job.");
            } else {
                event.getChannel().sendMessage("Wrong.");
            }
        }
    }

    public String[] getQuestionAndAnswer() {
        String questionData = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("amount", "1")
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
        String[] cutQuestion = questionData.split("question\":\"");
        String[] cutAnswer = cutQuestion[1].split("\",\"incorrect_answers");
        String[] trimmedQA = cutAnswer[0].split("\",\"correct_answer\":\"");
       return trimmedQA;
    }

    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }
}
