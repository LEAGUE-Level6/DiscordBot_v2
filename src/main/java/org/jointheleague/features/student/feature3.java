package org.jointheleague.features.student;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.news_api.ApiExampleWrapper;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.news_api.Article;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import sun.nio.cs.UTF_8;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;


public class feature3 extends Feature{
        public final String COMMAND = "!player";

        private WebClient webClient;
        private static String baseUrl;

    static {
        try {
            baseUrl = new URI("https://api.clashroyale.com/v1/players/#9JJCYYRP8").toString();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private final String apiKey = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiIsImtpZCI6IjI4YTMxOGY3LTAwMDAtYTFlYi03ZmExLTJjNzQzM2M2Y2NhNSJ9.eyJpc3MiOiJzdXBlcmNlbGwiLCJhdWQi" +
                "OiJzdXBlcmNlbGw6Z2FtZWFwaSIsImp0aSI6ImZmNzRhYmEzLTRmYzctNDU2OS05N2I2LTY0NTIwZmFjZGIxZCIsImlhdCI6MTYzMzkxMDE1NSwic3ViIjoiZGV2ZWxvcGVyL2Q4ZTNmNmEz" +
                "LThhMGItZDAwMS01NDJkLWUwYzAzOGI0ZTU3OSIsInNjb3BlcyI6WyJyb3lhbGUiXSwibGltaXRzIjpbeyJ0aWVyIjoiZGV2ZWxvcGVyL3NpbHZlciIsInR5cGUiOiJ0aHJvdHRsaW5nIn0seyJjaWR" +
                "ycyI6WyI3Ni45MS4xOTkuNDYiXSwidHlwZSI6ImNsaWVudCJ9XX0.Gsi7OAvKxu6XO83_uX5lI7edy6ZQ6wNA0tE2Xu_azuJNUTt_XUKMkzxfp9a5lWND8xjvklMFrisibhWE2CGybw";

        public feature3(String channelName) {
            super(channelName);
            helpEmbed = new HelpEmbed(COMMAND, "Example of using an API to get information from another service.  This returns a news story related to a topic (e.g. !apiExample cats)");

            //build the WebClient
            this.webClient = WebClient
                    .builder()
                    .baseUrl(baseUrl)
                    .build();
        }

        @Override
        public void handle(MessageCreateEvent event) {
            String messageContent = event.getMessageContent();
            if (messageContent.startsWith(COMMAND)) {
                messageContent = messageContent
                        .replace(COMMAND, "")
                        .replace(" " , "");
                if (messageContent.equals("")) {
                    event.getChannel().sendMessage("Please put your user ID after the command (do not include #");
                }
                else{
                   // String story = findPlayer(messageContent);
                   // event.getChannel().sendMessage(story);
                    getUserId(messageContent);
                }



            }
        }

        public void getUserId(String id) {
           /* System.out.println(baseUrl);
            System.out.println(id);
            this.webClient = WebClient
                    .builder()
                    .baseUrl(baseUrl + id.trim())
                    .build(); */
            String apiExampleWrapperMono = webClient.get()
                    .headers(headers -> headers.setBearerAuth(apiKey))
                            .retrieve()
                            .bodyToMono(String.class)
                            .block();



System.out.println(apiExampleWrapperMono);

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


