package org.jointheleague.features.examples.third_features;

import java.awt.Event;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.CompletableFuture;
import org.javacord.api.entity.message.Message;
import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.springframework.web.reactive.function.client.WebClient;


public class ImgurApi extends Feature {

    public final String IMAGE_COMMAND = "!image";
	private WebClient webClient;
	private static final String baseUrl = "https://imgur.com";
	static String imageName = "";
	String clientID = "4d7979a11b2ca0c";
	String clientSecret = "249c3c725c980eae4dcaeb1ef7e2617fabee7e94";
	
    public ImgurApi(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                IMAGE_COMMAND,
                "Use this method to search for any image or video you'd like!"
        );
        this.webClient = WebClient
                .builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.contains(IMAGE_COMMAND)) {
            //respond to message here!
       	 //System.out.println("Hi!");
             imageName = event.getMessageContent().replaceAll("!image ", "");
             
             String output = "https://imgur.com/search?q="+imageName;
             
             event.getChannel().sendMessage(output);
         
			
		}
        
    }

}
