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
// split URouUH0zfXDNOatcfdGPz0cWw8heCos
//CHANNEL_NAME=borl;DISCORD_TOKEN=MTQyNDg5MzE1Mjc2NTkzNTczOA.G2Djbq.3nRWdT-
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
//
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
        public final String COMMAND = "/money";
        protected String channelName;
        public HelpEmbed helpEmbed;
        private WebClient webClient;
        private static final String baseUrl = "https://api.hypixel.net/v2/skyblock/auctions";
        int indexed=0;
        int count = 0;
        AuctionDataWrapper auctions;
        int pages = 0;
        long max = 10_002_000_000_000l;

        String target = "Jerry Candy";


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
        if (messageContent.split(" ")[0].toLowerCase().startsWith(COMMAND) || messageContent.split(" ")[0].toLowerCase().startsWith("m")) {
            String out = "";
            commandParser(messageContent);
            event.sendResponse("searching for "+target +" at "+f(max));
            auctions = getData(0);
           // event.sendResponse(out = new Random().nextBoolean() ? ":thumbsup:" : ":thumbsdown:");


            //Finds LBIN

            long cheapest = 5;

            try {
                event.sendResponse(index());
                String stats = "indexed: " + f(indexed)+"\ncount: "+f(count)+"\nsize: " + auctions.getTotalPages()+"\nstatus: "+f(auctions.getTotalAuctions());
                System.out.println(stats);
                event.sendResponse(stats);
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


    public String index(){
        count = 0;
        indexed=0;
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
        return("cheapest "+f(cheapest) + " averaged to "+ f(averageN(prices)));
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
        }
        return (out/averageRange);
    }
    //sets variables (target & setPrice) to what the user inputs
    public void commandParser(String in){
        int offset = 0;
        String[] split = in.toLowerCase().split(" ");
        if(split.length>1){
            target=split[1];
            try{
                max = fd(split[split.length-1]);
                System.out.println(max + "<- setPrice");
                offset=1;
            }catch(Exception e){

            }
            for (int i = 2; i<split.length-offset; i++) {
                target += " "+split[i];
            }
            System.out.println(offset + " offset");
            System.out.println(target + "<- target");
        }else{
            System.out.println("no input/too many "+split.length);

        }
    }
    //formats numbers to have commas or k/m/b/t
    public String f(long in){
        boolean markers = true;
        String marker = "";
        String str = ""+in;
        String out = "";
        int track = 0;
        for (int i = str.length()-1; i >= 0; i--){
            if(track==3){
                track=0;
                        out=","+out;
            }
            out=str.charAt(i)+out;
            track++;
        }

        if(markers & out.chars().filter(num -> num == ',').count()>1){
            str = out.split(",")[0];
        if(out.chars().filter(num -> num == ',').count()==2){
            marker="m";
        }else if(out.chars().filter(num -> num == ',').count()==3){
            marker="b";
        }else if(out.chars().filter(num -> num == ',').count()==4){
            marker="t";
        }else{
            marker="q";
            System.out.println("over 4 commas in number");
        }

        }

        return str+marker;
    }
    //decodes numbers
    public long fd(String in){
        long out = 676867;
        if(in.contains(",")){
            out=Long.parseLong(in.replace(",", ""));
        }else{
            if(in.contains("m")){
                out=Long.parseLong(in.substring(0, in.length()-2))*1_000_000;
            }else if(in.contains("b")){
                out=Long.parseLong(in.substring(0, in.length()-2))*1_000_000_000;
            }else if(in.contains("t")){
                out=Long.parseLong(in.substring(0, in.length()-2))*1_000_000_000_000l;
            }else{
                try{
                    out=Long.parseLong(in);
                }catch (Exception e){
                    Long.parseLong(in.substring(0,in.length()-2));
                    System.out.println("removed foreign symbol from number");
                }
            }
        }
        return out;
    }

}

