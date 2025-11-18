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
import org.jointheleague.features.student.pojo.PreDataWrapper;
import org.jointheleague.features.templates.FeatureTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class PriceNotifier extends FeatureTemplate {
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

        public final String COMMAND = "M";
        protected String channelName;
        public HelpEmbed helpEmbed;
        private WebClient webClient;
        private static final String baseUrl = "https://api.hypixel.net/v2/skyblock/auctions";
        int indexed=0;
        int count = 0;
        AuctionDataWrapper auctions;
        int pages = 0;


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
               // .baseUrl(baseUrl)
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
            auctions = getData(0);
           // event.sendResponse(out = new Random().nextBoolean() ? ":thumbsup:" : ":thumbsdown:");
            event.sendResponse("h");
            event.sendResponse("status: " + auctions.getTotalAuctions());



            //Finds LBIN

            long cheapest = 5;

            try {
                index();
                event.sendResponse("indexed: " + indexed+"\ncount: "+count+"\nsize: " + auctions.getTotalPages());
                event.sendResponse("status: " + auctions.getTotalAuctions());
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    public AuctionDataWrapper getData(int page){
        System.out.println(baseUrl+"?page="+page);
        AuctionDataWrapper out;
        try{
            out = webClient.get().uri(baseUrl+"?page="+page).retrieve().bodyToMono(AuctionDataWrapper.class).block();
            System.out.println("no errors pulling data");
        }catch(Exception e){
            System.out.println(page+"data spoofed");
             out = new AuctionDataWrapper();
            out.spoof();
        }
        //Mono<AuctionDataWrapper> request = webClient.get().uri(baseUrl+"?page="+page).retrieve().bodyToMono(AuctionDataWrapper.class);

        return out;
    }


    public void index(){
        System.out.println("ran");
        Boolean run = true;
        AuctionDataWrapper data;
        int i = 0;
        try {
            while(run){
              data=getData(i);
                //System.out.println("working.2  "+data.getTotalPages()+" "+data.getAuctions().length);
                if(data.getStatus()) {
                    for (int j = 0; j < data.getAuctions().length - 1; j++) {
                        indexed++;
                        if (auctions.getAuctions()[j].getBin() && auctions.getAuctions()[j].getItem_name().contains("Ember")) {
                            count++;
                            System.out.println("found "+auctions.getAuctions()[j].getItem_name());
                        }
                    }
                }else{
                    run=false;
                }
                System.out.println("cycle "+i+"/"+data.getTotalPages());
                i++;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("fin");
    }
}

