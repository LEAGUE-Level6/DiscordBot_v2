package org.jointheleague.discord_bot;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.news_api.ApiExampleWrapper;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.news_api.Article;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class rimage extends Feature {

    public final String COMMAND = "!rimage";
    private WebClient webClient;


    private static final String baseUrl = "https://picsum.photos/";
    public rimage(String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "pic");
        this.webClient = WebClient
                .builder()
                .baseUrl(baseUrl)
                .build();
    }
//
String ink = "";
    int count =0;

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
    //    event.getChannel().sendMessage("a");

        if (messageContent.startsWith(COMMAND)) {
            messageContent = messageContent
                    .replace(COMMAND, "")
                    .replace(" " , "");
            if (messageContent.equals("")) {
                event.getChannel().sendMessage("grayscale,blur,size");
            }
//            else if (messageContent.startsWith("s")){
//                messageContent=messageContent.replace("s","");
//                event.getChannel().sendMessage("https://picsum.photos/"+Integer.parseInt(messageContent.substring(0, messageContent.indexOf('|')))+"/"+Integer.parseInt(messageContent.substring(messageContent.indexOf('|')+1, messageContent.length()))+"?random=1");
//            }
            if (messageContent.startsWith("grayscaleblur")){
                messageContent = messageContent.replace("grayscaleblur", "");
                ink = "?grayscaleblur=2";
            }

            else if (messageContent.startsWith("grayscale")){
                messageContent = messageContent.replace("grayscale", "");
                ink = "?grayscale";
            }
            else if (messageContent.startsWith("blur")){
                messageContent = messageContent.replace("blur", "");
                ink = "?blur";
            }
                int wid = Integer.parseInt(messageContent.substring(0, messageContent.indexOf('|')));
                int len = Integer.parseInt(messageContent.substring(messageContent.indexOf('|')+1, messageContent.length()));
                event.getChannel().sendMessage("https://picsum.photos/"+wid+"/"+len+ink+"random="+count);
                count++;
//            }
    }


//    public ApiData getimage(int a, int b) {
//        Mono<ApiData> dataApiMono = webClient.get()
//                .uri(uriBuilder -> uriBuilder
//                        .queryParam("width", a)
//                        .queryParam("height",b)
//                        .build())
//                .retrieve()
//                .bodyToMono(ApiData.class);
//        return dataApiMono.block();
    }

//    public String FIMage(int a, int b){
//
//        //Get a story from News API
//        System.out.println("FIMage");
//        ApiData apiData = getimage(a,b);
//
//        //Get the URL of the article
//        String urll= apiData.getUrl();
//
//        //Send the message
//        System.out.println(urll);
//        return urll;
//
//
//    }

}
