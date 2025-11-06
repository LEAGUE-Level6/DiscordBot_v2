package org.jointheleague.features.student;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jointheleague.api_wrapper.ReceivedMessage;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import org.jointheleague.api_wrapper.ReceivedMessage;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.jointheleague.features.student.pojo.AuctionDataWrapper;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Random;

public class PriceNotifier extends Feature {
    //when an item on ah reaches a certain min/max price it notifies users that it happened and notifies when it is
    //no longer past that threshold (islands or minion skins (blocks too) could be good for testing)

    //user selects item name - bot tries to find data for that type of item and sends confirmation/error message depending on if its null
    //  notification price   - if it's within a range (like 10%) let user know

    //saves price to initial value and checks to see if current price is above or below to know what to check for
    //constantly update current price var and checks if it passed the threshold
    //if it passes back over the threshold (threshold is between last retrieved price and current price) notify player too
    //can react to own message or use color emojis to quickly show if it's past or not

    //  command to check current values could be cool - shows initial and current price, threshold direction (higher/lower),
    //      -item name, date set, if it's past the threshold, and date last retrieved
    //      These should all be variables already stored

    //https://api.hypixel.net/#tag/SkyBlock/paths/~1v2~1skyblock~1auctions/get
    //https://developer.hypixel.net/

        public final String COMMAND = "/money";
        protected String channelName;
        public HelpEmbed helpEmbed;
        private WebClient webClient;
        private static final String baseUrl = "https://api.hypixel.net/v2/skyblock/auctions";

    public PriceNotifier(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "Notifies you when specified items reach a certain price threshold" +
                        "\nhttps://api.hypixel.net/#tag/SkyBlock/paths/~1v2~1skyblock~1auctions/get"
        );

        this.webClient = WebClient
                .builder()
                .baseUrl(baseUrl)
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(2 * 1024 * 1024 * 2))
                .build()
                ;
    }

        public HelpEmbed getHelpEmbed() {
            return this.helpEmbed;
        }

    @Override
    public void handle(ReceivedMessage event) {
        String messageContent = event.getMessageContent();
        if (messageContent.startsWith(COMMAND)) {
            String out = "";
            AuctionDataWrapper auctions = getData();
           // event.sendResponse(out = new Random().nextBoolean() ? ":thumbsup:" : ":thumbsdown:");
            event.sendResponse("h");

            event.sendResponse("status: " + auctions.getTotalAuctions());

//Finds LBIN
            String item = "Treasure Talisman";
            int lbin = getLBIN(auctions, item);
            event.sendResponse("indexed: " + lbin);
        }
    }

    int getLBIN(AuctionDataWrapper auctions, String item) {
System.out.println("started");
        int lBin=0;
        long cheapest = 5;
        try {
            for (int i = 0; i < auctions.getAuctions().length; i++) {
               // if (auctions.getAuctions()[i].getItem_name().equals(item)) {
                //    System.out.println("lBin++");
                //    lBin++;
               // }
                if(auctions.getAuctions()[i].getIsBin()){
                    lBin++;
                }

            }
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("ran");
        return lBin;
    }

    public AuctionDataWrapper getData(){
        Mono<AuctionDataWrapper> request = webClient.get().retrieve().bodyToMono(AuctionDataWrapper.class);
        System.out.println("no errors pulling data");
        AuctionDataWrapper out = null;
        try{
            out = request.block();
        }catch(Exception e){
e.printStackTrace();
        }
        return out;
    }

}

