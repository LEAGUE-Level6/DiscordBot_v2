package org.jointheleague.features.student.third_feature;

import net.dv8tion.jda.api.EmbedBuilder;
import org.jointheleague.api_wrapper.ReceivedMessage;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.cat_facts_api.CatWrapper;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

public class FeatureThree extends Feature {

    public final String COMMAND = "!SpacePic";
    private WebClient webClient;
    private static final String baseUrl = "https://api.nasa.gov/planetary/apod?api_key="+System.getenv("API_KEY");
    public FeatureThree(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "Provides the picture of the day from the NASA APOD API by default. Add"
        );
        this.webClient = WebClient
                .builder()
                .baseUrl(baseUrl)
                .build();
    }

    @Override
    public void handle(ReceivedMessage event) {

        String messageContent = event.getMessageContent();
        System.out.println("Message Received");
        System.out.println(messageContent);
        if (messageContent.startsWith(COMMAND)) {
            //respond to message here
            System.out.println("Printing Image");
            String imageLink = getAPOD();
            EmbedBuilder embed = new EmbedBuilder();
            embed.setImage(imageLink);
            event.sendResponse(embed.build());
        }
    }
    public String getAPOD(){
        System.out.println("Getting Image");
        Mono<ImageWrapper> imageWrapperMono = webClient.get()
                .retrieve()
                .bodyToMono(ImageWrapper.class);

        //collect the response into a plain old java object
        ImageWrapper imageWrapper = imageWrapperMono.block();
        String image = imageWrapper.getUrl();
        System.out.println(image);
        return image;
    }

}
