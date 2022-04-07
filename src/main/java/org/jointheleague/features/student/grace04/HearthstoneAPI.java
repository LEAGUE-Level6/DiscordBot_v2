package org.jointheleague.features.student.grace04;

import org.javacord.api.event.message.MessageCreateEvent;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class HearthstoneAPI extends Feature {

    public final String COMMAND = "!hsAPI";

    private WebClient webClient;
    private static final String baseUrl = "https://rapidapi.com/omgvamp/api/hearthstone/";

    public HearthstoneAPI(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "This returns Hearthstone card info"
        );

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
            if (messageContent.equals("")) {
                event.getChannel().sendMessage("Please put a card name after the command");
            }
            else{
                String name = getCard(messageContent);
                event.getChannel().sendMessage(name);
            }
        }
    }

    public Card getCardByCard(String card) {
        Mono<Card> cardMono = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("uri", "https://omgvamp-hearthstone-v1.p.rapidapi.com/cards/%7Bname%7D")
                        .queryParam("X-RapidAPI-Host", "omgvamp-hearthstone-v1.p.rapidapi.com")
                        .queryParam("X-RapidAPI-Key", "6fa354383amsh1ff11039b032073p1ed12cjsn5a0682d5c183")
                        .build())
                .retrieve()
                .bodyToMono(Card.class);

        Card result = cardMono.block();
        System.out.println(result);
        return result;
    }

    public String getCard(String card){

        Card drac = getCardByCard(card);

        String name = drac.getName();

        String faction = drac.getFaction();

        String type = drac.getType();

        int cost = drac.getCost();

        int attack = drac.getAttack();

        int health = drac.getHealth();

        String text = drac.getText();

        String flavor = drac.getFlavor();

        //Create the message
        String message =
                name + " - " + faction + " " + type + " (" + cost + "/" + attack + "/" + health + ")"
                        + "\nCard text: " + text + "\nFlavor: " + flavor;

        //Send the message
        return message;
    }

    public void setWebClient(WebClient webClient) {
        this.webClient = webClient;
    }
}
