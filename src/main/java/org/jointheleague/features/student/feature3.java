package org.jointheleague.features.student;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


public class feature3 extends Feature{
        public final String COMMAND = "!player";

        private WebClient webClient;
        private static String baseUrl = "https://api.clashroyale.com/v1/players";

    private final String apiKey = System.getenv("API_KEY");
    ;
        public feature3(String channelName) {
            super(channelName);
            helpEmbed = new HelpEmbed(COMMAND, "Example of using an API to get information from another service.  This returns a news story related to a topic (e.g. !apiExample cats)");

            //build the WebClient
            this.webClient = WebClient
                    .builder()
                    .defaultHeaders(headers -> headers.setBearerAuth(apiKey))
                    .baseUrl(baseUrl)
                    .build();
        }

        @Override
        public void handle(MessageCreateEvent event) {
            String messageContent = event.getMessageContent();
            if (messageContent.startsWith(COMMAND)) {
                messageContent = messageContent
                        .replace(COMMAND, "")
                        .replace(" ", "");
                getUserId(messageContent);
            }


        }

        public void getUserId(String id) {

            Mono<String> test = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/#9JJCYYRP8")
                            .build())
                    .retrieve()
                    .bodyToMono(String.class);
            System.out.print(test.block());
        }

     /*   public String findPlayer(String topic){

            //Get a story from News API
            ApiExampleWrapper apiExampleWrapper = getUserId(topic);

            //Get the first article
            Article article = apiExampleWrapper.getArticles().get(0);

            //Get the title of the article
            String articleTitle = article.getTitle();

            //Get the content of the article
            String articleContent = article.getContent();

            //Get the URL of the article
            String articleUrl = article.getUrl();

            //Create the message
            String message =
                    articleTitle + " -\n"
                            + articleContent
                            + "\nFull article: " + articleUrl;

            //Send the message
            return message;
        }
*/
        public void setWebClient(WebClient webClient) {
            this.webClient = webClient;
        }

    }


