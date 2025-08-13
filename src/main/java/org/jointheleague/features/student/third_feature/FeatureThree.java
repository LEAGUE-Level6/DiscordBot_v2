package org.jointheleague.features.student.third_feature;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.utils.FileUpload;
import org.jointheleague.api_wrapper.ReceivedMessage;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class  FeatureThree extends Feature {

    private static final String baseUrl = "https://api.nasa.gov/planetary/apod?api_key=" + System.getenv("API_KEY");
    public final String COMMAND = "!SpacePic";
    boolean messageSent = false;

    boolean waitForMessage = false;
    private final WebClient webClient;

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
    public void handle(ReceivedMessage event) throws IOException {

        String messageContent = event.getMessageContent();

       
        MessageChannel mc = event.getMessageChannel();

        if (waitForMessage && messageSent) {

            if (messageContent.equalsIgnoreCase("yes")) {

                EmbedBuilder eb = new EmbedBuilder();
                ArrayList<String> data = getAPODData();
                eb.setAuthor("Photographer: " + data.get(2));
                eb.setTitle("Title: " + data.get(0));
                eb.setDescription("Explanation: " + data.get(1));
                eb.setColor(Color.BLUE);
                mc.sendMessageEmbeds(eb.build()).queue();
                messageSent = false;
            } else {
                waitForMessage = false;
            }

        } else if (messageContent.startsWith(COMMAND)) {

            //respond to message here
            String imageLink = getAPOD();
            EmbedBuilder eb = new EmbedBuilder();
            InputStream file = new URL(imageLink).openStream();
            eb.setImage("attachment://upload.jpg");
            eb.setDescription("Here is NASA's space picture of the day!");

            FileUpload fu = FileUpload.fromData(file, "upload.jpg");
            mc.sendFiles(fu).setEmbeds(eb.build()).complete();

            mc.sendMessage("Do you want to know more?").queue((message -> {
                messageSent = true;
                waitForMessage = true;
            }));

        }


    }

    public String getAPOD() {

        Mono<ImageWrapper> imageWrapperMono = webClient.get()
                .retrieve()
                .bodyToMono(ImageWrapper.class);

        //collect the response into a plain old java object
        ImageWrapper imageWrapper = imageWrapperMono.block();
        String image = imageWrapper.getUrl();

        return image;
    }

    public ArrayList<String> getAPODData() {

        Mono<ImageWrapper> imageWrapperMono = webClient.get()
                .retrieve()
                .bodyToMono(ImageWrapper.class);

        //collect the response into a plain old java object
        ImageWrapper imageWrapper = imageWrapperMono.block();
        ArrayList<String> dataList = new ArrayList<String>();
        dataList.add(imageWrapper.getTitle());
        dataList.add(imageWrapper.getExplanation());
        dataList.add(imageWrapper.getCopyright());

        return dataList;
    }


}
