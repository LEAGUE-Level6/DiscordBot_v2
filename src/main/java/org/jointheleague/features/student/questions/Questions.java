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
    public final String ATTEMPT = "!attempt";

    private WebClient webClient;
    private static final String baseUrl = "https://opentdb.com/api.php";

    public boolean gameStarted = false;
    public int wrongCounter = 0;

    String question = "", answer = "";
    String[] response;

    public Questions(String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "type !questionsapi for a question and !attempt to guess. ex: !guess duck . after 3 guesses, use !request answer");

        this.webClient = WebClient
                .builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();

        if (messageContent.startsWith(COMMAND)) {
            response = getQuestionAndAnswer();
            question = response[0];
            answer = response[1];
            event.getChannel().sendMessage(question);
            System.err.println(answer);
            gameStarted = true;
        }

        if (gameStarted == true && messageContent.contains(ATTEMPT)) {
            if (messageContent.contains(answer)) {
                event.getChannel().sendMessage("Good job.");
            } else {
                event.getChannel().sendMessage("Wrong.");
                wrongCounter ++;
            }
        }
        if (gameStarted == true && messageContent.contains("!request answer")) {
            if(wrongCounter >= 3) {
                event.getChannel().sendMessage(answer);
                gameStarted = false;
            } else {
                event.getChannel().sendMessage("You must guess " + (3 - wrongCounter) + ".");
            }
        }
    }

    public String[] getQuestionAndAnswer() {
        QuestionsWrapper questionData = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("amount", "1")
                        .build())
                .retrieve()
                .bodyToMono(QuestionsWrapper.class)
                .block();

        return new String[] {
                questionData.getResults().get(0).getQuestion(),
                questionData.getResults().get(0).getCorrectAnswer()
        };
    }

    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }
}
