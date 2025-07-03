package org.jointheleague.features.student.third_feature;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.jointheleague.api_wrapper.ReceivedMessage;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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

            TextChannel textChannel = event.getMessageChannel();

            //respond to message here
            System.out.println("Printing Image");
            String imageLink = getAPOD();
//            EmbedBuilder embed = new EmbedBuilder();
//            embed.setImage("https://apod.nasa.gov/apod/image/2507/MwSpires_Chay_960.jpg");
            // embed.setAuthor(imageLink);
            // Create the EmbedBuilder instance
            EmbedBuilder eb = new EmbedBuilder();
            eb.setImage("https://apod.nasa.gov/apod/image/2507/MwSpires_Chay_1874.jpg");
            textChannel.sendMessageEmbeds(eb.build()).queue();
            textChannel.sendMessage("https://apod.nasa.gov/apod/image/2507/MwSpires_Chay_1874.jpg").queue();

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
