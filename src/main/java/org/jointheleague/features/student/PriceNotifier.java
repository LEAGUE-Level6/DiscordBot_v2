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
//CHANNEL_NAME=borl;DISCORD_TOKEN=MTQyNDg5MzE1Mjc2NTkzNTczOA.GXH_GQ.1d6QZjCJFRvdmNO-rEmPjP5JAhZxf7GOMF-9fE
import reactor.core.publisher.Mono;

import java.util.*;

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


/*USER PROCESS
    /money
        if blank it will ask for input, if not, takes in stuff after space as input (all inputs)
    checks what comes back with the input, and confirms with the user if its what they want
            if there's multiple allow user to say number/name of item to confirm
    ask to set price
            allow for commas/ k/b/m markers

 */
        public final String COMMAND = "m";
        protected String channelName;
        public HelpEmbed helpEmbed;
        private WebClient webClient;
        private static final String baseUrl = "https://api.hypixel.net/v2/skyblock/auctions";
        int indexed=0;
        int count = 0;
        AuctionDataWrapper auctions;
        int pages = 0;
        long max = 10_002_000_000_000l;

        String target = "aspiring leap";


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
        if (messageContent.toLowerCase().startsWith(COMMAND)) {
            String out = "";
            event.sendResponse("h");
            auctions = getData(0);
           // event.sendResponse(out = new Random().nextBoolean() ? ":thumbsup:" : ":thumbsdown:");


            //Finds LBIN

            long cheapest = 5;

            try {
                index();
                System.out.println("indexed: " + indexed+"\ncount: "+count+"\nsize: " + auctions.getTotalPages()+"\nstatus: "+auctions.getTotalAuctions());
                event.sendResponse("final");
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
            //System.out.println("no errors pulling data");
        }catch(Exception e){
            System.out.println(page+"data spoofed");
             out = new AuctionDataWrapper();
            out.spoof();
        }
        //Mono<AuctionDataWrapper> request = webClient.get().uri(baseUrl+"?page="+page).retrieve().bodyToMono(AuctionDataWrapper.class);

        return out;
    }


    public void index(){
        long cheapest = max;
        System.out.println("ran");
        ArrayList<Long> prices = new ArrayList<Long>();
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
                        if (data.getAuctions()[j].getBin() && data.getAuctions()[j].getItem_name().toLowerCase().contains(target)) {
                            count++;
                            System.out.println("found "+data.getAuctions()[j].getItem_name());
                            prices.add(data.getAuctions()[j].getStarting_bid());
                            if(data.getAuctions()[j].getStarting_bid()<cheapest){
                                cheapest=data.getAuctions()[j].getStarting_bid();
                                System.out.println("new cheapest ^"+data.getAuctions()[j].getStarting_bid());
                            }
                            //set.add(auctions.getAuctions()[j].getId());
                        }
                    }
                }else{
                    run=false;
                }
                //System.out.println("cycle "+i+"/"+data.getTotalPages());
                i++;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("fin: cheapest "+cheapest + " averaged to "+ averageN(prices));
    }
    //finds the average price of the (5) cheapest numbers in an array
    //really unoptimized, iterates through everything in the "best" array to find the\n
    //most expensive index everytime, then replaces that one
    public long averageN(ArrayList<Long> in){
        int out=0;
        long track=0;
        int index=0;
        int averageRange = 5;
        long[] best = new long[averageRange];
        Arrays.fill(best, max);
        for (int i = 0; i<in.size(); i++){
            track=0;
            index=0;
            for (int j = 0; j<averageRange; j++){

                if (best[j]>track){
                    track=best[j];
                    index=j;
                }
            }
            System.out.println("largest "+best[index]+" at "+index);
            if(in.get(i)<best[index]){
                best[index]=in.get(i);
            }
        }
        for (int j = 0; j<averageRange; j++){
            if(best[j]==max){
            best[j]=0;
            averageRange--;
            }
            out+=best[j];
            System.out.println(j+"j ="+best[j]);
        }
        return (out/averageRange);
    }
}

