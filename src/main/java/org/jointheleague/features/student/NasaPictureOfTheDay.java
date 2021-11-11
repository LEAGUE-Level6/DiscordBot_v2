package org.jointheleague.features.student;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.jointheleague.features.student.NasaPicture.NasaPictureWrapper;
import org.springframework.web.reactive.function.client.WebClient;


import reactor.core.publisher.Mono;

public class NasaPictureOfTheDay extends Feature{
	public final String COMMAND = "!nasaAPOD";

    private WebClient webClient;
    private static final String baseUrl = "https://api.nasa.gov/planetary/apod";
    private final String apiKey = "Y01ELGRKSZuaczdrx5m4KJc3sf93yOh5jywRR51U";
    private String date = java.time.LocalDate.now().toString();
    public NasaPictureOfTheDay(String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "explanation");

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
            		
            if(messageContent.length()>0) {
            	
            }
                String pictureInfo = findPicture();
                event.getChannel().sendMessage(pictureInfo);
            
        }
    }

    public NasaPictureWrapper getSomethingSomething() {
        Mono<NasaPictureWrapper> apiExampleWrapperMono = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("api_key", apiKey)
                        .queryParam("date", date)
                        .build())
                .retrieve()
                .bodyToMono(NasaPictureWrapper.class);

        return apiExampleWrapperMono.block();
    }

    public String findPicture() {
    	NasaPictureWrapper nasaPictureWrapper = getSomethingSomething();
    	String url = nasaPictureWrapper.getUrl();
    	String title = nasaPictureWrapper.getTitle();
    	url = url.replace("embed/", "watch?v=");
    	
    	
    	return url;
    }
    
//    public String findStory(String topic){
//
//        //Get a story from News API
//        ApiExampleWrapper apiExampleWrapper = getNewsStoryByTopic(topic);
//
//        //Get the first article
//        Article article = apiExampleWrapper.getArticles().get(0);
//
//        //Get the title of the article
//        String articleTitle = article.getTitle();
//
//        //Get the content of the article
//        String articleContent = article.getContent();
//
//        //Get the URL of the article
//        String articleUrl = article.getUrl();
//
//        //Create the message
//        String message =
//                articleTitle + " -\n"
//                        + articleContent
//                        + "\nFull article: " + articleUrl;
//
//        //Send the message
//        return message;
//    }

    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }
}
