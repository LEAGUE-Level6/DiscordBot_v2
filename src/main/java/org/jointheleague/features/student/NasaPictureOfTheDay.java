package org.jointheleague.features.student;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
            
            
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String bottom = "1995-06-20"; 
            Date past = null; 
            Date user = null;
            Date today = null;
            try {
				past = formatter.parse(bottom);
				user = formatter.parse(messageContent);
				today = formatter.parse(java.time.LocalDate.now().toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}

            
        String[] YMD = messageContent.split("-");	
            
        if(messageContent.length()>0) { 
            	
            	if(YMD[0].length()==4&&YMD[1].length()==2&&YMD[2].length()==2) {
            		if(user.before(past)||user.after(today)) {
            			event.getChannel().sendMessage("Date is out of range");
            		}

            		else {
            			date = messageContent;
            			String pictureInfo = findPicture();
            			event.getChannel().sendMessage(pictureInfo);
            		}
            	}
            	
            	else {
            		event.getChannel().sendMessage("Date is not formmated correctly\nMust be formmated as (yyyy-mm-dd)");
            	}
            	
            }
        
            else{
            	date = java.time.LocalDate.now().toString();
            	String pictureInfo = findPicture();
                event.getChannel().sendMessage(pictureInfo);
            }
                
            
        }
    }

    public NasaPictureWrapper getAPOD() {
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
    	NasaPictureWrapper nasaPictureWrapper = getAPOD();
    	String url = nasaPictureWrapper.getUrl();
    	String title = nasaPictureWrapper.getTitle();
    	url = url.replace("embed/", "watch?v=");
    	
    	
    	return url;
    }
    



    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }
}
