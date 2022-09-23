package org.jointheleague.features.examples.third_features;

import java.util.List;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.news_api.ApiExampleWrapper;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.news_api.Article;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.springframework.web.reactive.function.client.WebClient;

import net.aksingh.owmjapis.api.APIException;
import reactor.core.publisher.Mono;

public class TopThree extends Feature {

	public final String COMMAND = "!topThree";
	
	
	private WebClient webClient;
	private static final String baseUrl = "http://newsapi.org/v2/everything";
    private final String apiKey = "59ac01326c584ac0a069a29798794bec";
	
	public TopThree(String channelName) {
		super(channelName);
		// TODO Auto-generated constructor stub
		helpEmbed = new HelpEmbed(COMMAND, "Gives the most popular articles based on a word to search for");
		
		this.webClient = WebClient
				.builder()
				.baseUrl(baseUrl)
				.build();
	}

	@Override
	public void handle(MessageCreateEvent event) throws APIException {
		String messageContent = event.getMessageContent();
		// TODO Auto-generated method stub
		if(messageContent.contains(COMMAND)) {
			messageContent = messageContent.replaceAll(" ", "").replace(COMMAND, "");
			if(messageContent.equals("")) {
				event.getChannel().sendMessage("Please format your command: '!topThree [word]");
			}
			
			else {
				giveArticles(messageContent, event);
			}
			
		}
	}
	
	public void giveArticles(String inquiry, MessageCreateEvent event) {
		
		List<Article> article = getNewsStoryByTopic(inquiry).getArticles();
		
		for(int i = 0; i < 3; i++) {
			String title = article.get(i).getTitle();
			String author = article.get(i).getAuthor();
			String content = article.get(i).getUrl();
			event.getChannel().sendMessage(title + ", by " + author + ": " + content);
			
		}
	}
	
	//My own very original code don't steal
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
	
	
}
