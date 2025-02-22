package org.jointheleague.features.student.thrid_feature;

import org.jointheleague.api_wrapper.ReceivedMessage;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.cat_facts_api.CatWrapper;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.news_api.ApiExampleWrapper;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.news_api.Article;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;



public class AIStoryMaker extends Feature{
	
	public final String COMMAND = "!AIStoryMaker";

    private WebClient webClient;
    private static final String baseUrl = "find api";

        
    
	public AIStoryMaker(String channelName) {
		super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "This returns a random AI generated Story.");

        //build the WebClient
        this.webClient = WebClient
                .builder()
                .baseUrl(baseUrl)
                .build();
	}



	@Override
	public void handle(ReceivedMessage event) {
		// TODO Auto-generated method stub
		
	}

}
