package org.jointheleague.features.student.grace04;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
//import org.jointheleague.features.student.grace04.tetr_api.ApiWrapper;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.springframework.web.reactive.function.client.WebClient;

public class HearthstoneAPI extends Feature {

    public final String COMMAND = "!hsAPI";

    private WebClient webClient;
    private static final String baseUrl = "https://rapidapi.com/omgvamp/api/hearthstone/";

    public HearthstoneAPI(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "This returns Hearthstone card info"
        );

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
                event.getChannel().sendMessage("Please put a card name after the command");
            }
            else{
                String name = getCard(messageContent);
                event.getChannel().sendMessage(name);
            }
        }
    }

    /*public ApiWrapper getUserByName(String name) {
        Mono<ApiWrapper> apiWrapperMono = webClient.get()
                .retrieve()
                .bodyToMono(ApiWrapper.class);

        return apiWrapperMono.block();

        HttpRequest request = HttpRequest.newBuilder()
		    .uri(URI.create("https://omgvamp-hearthstone-v1.p.rapidapi.com/cards/%7Bname%7D"))
		    .header("x-rapidapi-host", "omgvamp-hearthstone-v1.p.rapidapi.com")
		    .header("x-rapidapi-key", "SIGN-UP-FOR-KEY")
		    .method("GET", HttpRequest.BodyPublishers.noBody())
		    .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }*/

    public String getCard(String name){

        //Get a story from News API
        ApiWrapper apiWrapper = getUserByName(name);

        //Get the first article
        User user = apiWrapper.getArticles().get(0);

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

    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }
}
