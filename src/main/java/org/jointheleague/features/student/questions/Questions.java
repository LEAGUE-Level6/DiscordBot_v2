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
    private static final String baseUrl = "https://opentdb.com/api.php?amount=1";

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
            String question = getQuestion();
            event.getChannel().sendMessage(question);
        }
    }

    public String getQuestion() {
        Mono<QuestionsWrapper> questionWrapperMono = webClient.get()
                .retrieve()
                .bodyToMono(QuestionsWrapper.class);

        QuestionsWrapper questionWrapper = questionWrapperMono.block();

        String message = questionWrapper.getData().get(1);

        return message;
    }

    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }
}
