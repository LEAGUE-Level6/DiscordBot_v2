package org.jointheleague.features.myfeatures;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.myfeatures.YoutubeAPI.ApiExampleWrapper;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class YoutubeAPI extends Feature{
    public final String COMMAND = "!video";

    private WebClient webClient;
    private static final String baseUrl = "https://www.googleapis.com/youtube/v3/search";
    private final String apiKey = "AIzaSyBHBrau4HiCuKBgJEVZ2GPVkLaDQric2gI";

    public YoutubeAPI(String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "Get a YouTube video to a related topic, get the creator's name, and their subscriber count!");

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
                event.getChannel().sendMessage("Please put a topic after the command (e.g. " + COMMAND + " PewDiePie)");
            }
            else{
                String story = findStory(messageContent);
                event.getChannel().sendMessage(story);
            }
        }
    }

    public ApiExampleWrapper getNewsStoryByTopic(String topic) {
        Mono<ApiExampleWrapper> apiExampleWrapperMono = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("q", topic)
                        .queryParam("sortBy", "popularity")
                        .queryParam("apiKey", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(ApiExampleWrapper.class);

        return apiExampleWrapperMono.block();
    }

    public String findStory(String topic){

        //what i want it to do

        //get a video from YouTube API given search (e.g. !video pewdiepie)
        //get the first video from search results (index 0)
        //get the title of the video
        //get the URL of the video (video should be an embed)
        //get the creator of the video

       //if (!video /info), give the
        //get the #subscribers the creator has
        //get the #views on the video


        //collect the response into a java object using the classes you just created
     ApiExampleWrapper[] results =  getVideoByTopic(topic);

        //take the first Result in the array
        ApiExampleWrapper = results[0];

        //get the first article
        String bookTitle = result.getTitle();

        //get the title of the article
        String bookLink = result.getLink();


        //Create the message
        String message =
                articleTitle + " -\n"
                        + articleContent
                        + "\nVideo " + articleUrl;

        //Send the message
        return message;
    }


    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

}
