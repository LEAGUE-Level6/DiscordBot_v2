package org.jointheleague.features.GooBotFeatures;

import org.jointheleague.features.GooBotFeatures.RandomWordWrapper;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.cat_facts_api.CatWrapper;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class RandomWordGenerator extends Feature {


    public final String COMMAND = "!CoolWord";

    private WebClient webClient;
    private static final String baseUrl = "https://random-word-api.herokuapp.com/word";
    private URL url;

    public RandomWordGenerator(String channelName) {
        super(channelName);
        helpEmbed = new HelpEmbed(COMMAND, "This will use an API to grab a random word from any abbreviated language" +
                "you put in!");

        //build the WebClient

        try {
            url = new URL(baseUrl);
        } catch (MalformedURLException e) {
            System.out.println("Error of Malformed URL Exception:" + e.getStackTrace());
        }

    }

    @Override
    public void handle(MessageCreateEvent event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            String coolWord = getCoolWord();
            event.getChannel().sendMessage("Here is your special word: " + coolWord);
        }
    }

    public String getCoolWord() {
        String word = "error";
        try {
            URLConnection con = url.openConnection();
            InputStream is = con.getInputStream();
            String encoding = con.getContentEncoding();
            encoding = encoding == null ? "UTF-8" : encoding;
            Scanner s = new Scanner(is);
            word = s.next();
            is.close();
            s.close();
            word = word.substring(1, word.length()-1);
        } catch (IOException e) {
            System.out.print("Here is the error: + " + e.getStackTrace());
        }
        return word;
    }

    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }

}


