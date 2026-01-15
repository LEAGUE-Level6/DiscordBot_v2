package org.jointheleague.features.student;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jointheleague.api_wrapper.ReceivedMessage;
import org.jointheleague.discord_bot.DiscordBot;
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
//split pVComE-1yB1eaArvIMw
//CHANNEL_NAME=borl;DISCORD_TOKEN=MTQyNDg5MzE1Mjc2NTkzNTczOA.G4f_c9.UzRIn0RFcdEFZFCalz-
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
            allow for commas/ k/m/b markers

 */
    public final String COMMAND = "/money";
    protected String channelName;
    public HelpEmbed helpEmbed;
    private WebClient webClient;
    private static final String baseUrl = "https://api.hypixel.net/v2/skyblock/auctions";
    int indexed = 0;
    int count = 0;
    //AuctionDataWrapper auctions;
    int pages = 0;
    long max = 10_002_000_000_000l;
    long total = 0;
    int averageRange = 5;
    boolean hasIndexed = false;
    String indexMsg = "";
    String target = "jerry candy";
    String minp = "8m";
    String maxp = "12m";
    int minc = 7;
    int maxc = 9;
    Map<String, ArrayList<Long>> everything = new HashMap<>(50_000_000);
    Map<String, Integer> counts = new HashMap<>(50_000_000);
    DiscordBot discord;
    String specTarget = "";
    String msg = "";


    public PriceNotifier(String channelName, DiscordBot discord) {
        super(channelName);
        this.discord=discord;

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
        commandParser(messageContent);
        //main command
        if (messageContent.split(" ")[0].toLowerCase().startsWith(COMMAND)
        || messageContent.split(" ")[0].toLowerCase().startsWith("m")) {
            try {
                msg = " found at " + f(cheapest(target));
                if (!specTarget.isEmpty()) {
                    event.sendResponse("\u200Esearching for " + specTarget + " under " + f(max));
                    msg = ("indexed: " + f(indexed) + "\ncount: " + f(fetch(specTarget).size()) +
                            "\naveraged to: " + f(averageN(fetch(specTarget))) + "\n" + specTarget + msg);
                    event.sendResponse(msg);
                } else {
                    event.sendResponse(target + " was not a part of any item name listed for BIN");
                }
            }catch (Exception e) {e.printStackTrace();}
        }
        //market manipulation finder
        else if(messageContent.split(" ")[0].toLowerCase().startsWith("search")){
            try {
                commandParser(messageContent);
                ArrayList<String> found = dealFinder();
                String deals = found.get(0);
                for (int i = 1; i < found.size() - 1; i++) {
                    deals += "\n" + found.get(i);
                }
                event.sendResponse(deals);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    //gets the AH data of a specific page (used in index)
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

        return out;
    }
    //iterates through every auction for stats message and to make dealfinder work
    //iterates through every auction and populates the price and quantity arrays
        //works by saving arraylists containing every bin price of an item and saving the arraylists to hashmaps with the item name as the key
    public void index(){
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
                if(i%5==0){
                discord.progressMsg(i, data.getTotalPages());
                }
                if(data.getStatus()) {
                    for (int j = 0; j < data.getAuctions().length - 1; j++) {
                        indexed++;
                        if(data.getAuctions()[j].getBin()) {
                            //if it hasnt already, adds a new arraylist to the hashmap matching the item's name
                            everything.putIfAbsent(data.getAuctions()[j].getItem_name().toLowerCase(), new ArrayList<Long>());
                            //adds the item's price to the arraylist
                            everything.get(data.getAuctions()[j].getItem_name().toLowerCase())
                                    .add(data.getAuctions()[j].getStarting_bid());
                        }
                    }
                }else{
                    run=false;
                }
                i++;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        //return("lowest "+f(cheapest) + " averaged "+ f(averageN(prices)) +" across " + averageRange + " cheapest");
        System.out.println("indexing finished");
        discord.sendFinish();
        //discord.sendMessage("m ember");
    }
    public long cheapest(String in){
        String placehold = "";
        try {
            long cheapest = max;
            specTarget = "";
            for (String i : everything.keySet()) {
                if (i.contains(target)) {
                    System.out.println("found, " + i);
                    for (int j = 0; j < everything.get(i).size(); j++) {
                        System.out.println(everything.get(i).size());
                        System.out.print("/");
                        if (everything.get(i).get(j) < cheapest) {
                            cheapest = everything.get(i).get(j);
                            System.out.println("\nnew cheapest: " + everything.get(i).get(j));
                            specTarget = i;
                        }
                    }
                }
            }
            System.out.println("");
            return cheapest;
        }catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }
    //finds the average price of the (5) cheapest numbers in an array
    //really unoptimized, iterates through everything in the "best" array to find the
        //most expensive index everytime, then replaces that one
    public long averageN(ArrayList<Long> in){
        int out=0;
        long track=0;
        int index=0;
        int counter=0;
        long[] best = new long[averageRange];
        Arrays.fill(best, max);
        for (int i = 0; i<in.size(); i++){
            track=0;
            index=0;
            total += in.get(i);
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
                counter++;
            }
            out+=best[j];
        }
        if(fetch(specTarget).size()<averageRange){
            return out/fetch(specTarget).size();
        }
        return (out/averageRange-counter);
    }
    //finds the best items to test with
    public ArrayList<String> dealFinder(){
        System.out.println(minp + " " + maxp + " " + minc + " " + maxc);
        ArrayList<String> out = new ArrayList<String>();
            for (String key : everything.keySet()) {
                for (int i = 0; i < everything.get(key).size(); i++) {
            }
            }long specPrice = 0;
            int specTotal = 0;
        for (String key : everything.keySet()) {

                specPrice = 0;
                specTotal = 0;
                for (int i = 0; i < everything.get(key).size(); i++) {
                    specPrice += everything.get(key).get(i);
                    specTotal++;
                }
                if(minc<=specTotal && maxc>=specTotal && specPrice<fd(maxp) && specPrice>fd(minp)) {
                    System.out.println(f(specPrice) + " for " + specTotal + " " + star(key) + "'s");
                    out.add(f(specPrice) + " for " + specTotal + " " + star(key) + "'s");
                }
        }
            return out;
    }
    //Rewrites star values to be more readable (used in dealfinder)
    public String star(String in){
        if(in.contains("✪")){
            int countS = 0;

            for(int i=0; i < in.length(); i++) {
                if(in.charAt(i) == '✪') {
                    countS++;
                }else if(in.charAt(i) == '➊'){
                    countS+=1;
                }else if(in.charAt(i) == '➋'){
                    countS+=2;
                }else if(in.charAt(i) == '➌'){
                    countS+=3;
                }else if(in.charAt(i) == '➍'){
                    countS+=4;
                }else if(in.charAt(i) == '➎'){
                    countS+=5;
                }
            }
            in=in.replaceFirst("✪", (count+"✪"));
            in=in.replaceAll("✪", "");
        }
        return in;
    }
    //sets variables (target & setPrice) to what the user inputs
    public void commandParser(String in){
        //main command
        //start, item name, price (optional)
        if(in.toLowerCase().startsWith("m")||in.toLowerCase().startsWith(COMMAND)) {
            int offset = 0;
            String[] split = in.toLowerCase().split(" ");
            if (split.length > 1) {
                target = split[1];
                try {
                    System.out.println("suffix -> " + split[split.length - 1]);
                    max = fd(split[split.length - 1].trim());
                    System.out.println(max + "<- setPrice");
                    offset = 1;
                } catch (Exception e) {
                    System.out.println("suffix not a number");
                }
                for (int i = 2; i < split.length - offset; i++) {
                    target += " " + split[i];
                }
                System.out.println(offset + " offset");
                System.out.println(target + "<- target");
            } else {
                System.out.println("no input/too many " + split.length);

            }
        }
        //search
        if(in.toLowerCase().startsWith("search")){
            String[] split = in.toLowerCase().split(" ");
            minp=split[1];
            maxp=split[2];
            minc=Integer.parseInt(split[3]);
            maxc=Integer.parseInt(split[4]);
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
//     change this 0 to 1 if you dont want to abbreviate thousands |
        if(markers & out.chars().filter(num -> num == ',').count()>0){
            str = out.split(",")[0];
        if(out.chars().filter(num -> num == ',').count()==2){
            marker="m";
        }else if(out.chars().filter(num -> num == ',').count()==3){
            marker="b";
        }else if(out.chars().filter(num -> num == ',').count()==4){
            marker="t";
        }else if(out.chars().filter(num -> num == ',').count()==1){
                marker="k";
            }else{
            marker="q";
            System.out.println("over 4 commas in number");
        }

        }

        return str+marker;
    }
    //decodes numbers
    public long fd(String in){
        long out = 67_000_000_000l;
        if(in.contains(",")){
            out=Long.parseLong(in.replace(",", ""));
        }else{if(in.contains("k")){
            out=Long.parseLong(in.substring(0, in.length()-1))*1_000;
        }else if(in.contains("m")){
                out=Long.parseLong(in.substring(0, in.length()-1))*1_000_000;
            }else if(in.contains("b")){
                out=Long.parseLong(in.substring(0, in.length()-1))*1_000_000_000;
            }else if(in.contains("t")){
                out=Long.parseLong(in.substring(0, in.length()-1))*1_000_000_000_000l;
            }else{
                try{
                    out=Long.parseLong(in);
                }catch (Exception e){
                    Long.parseLong(in.substring(0,in.length()-1));
                    System.out.println("removed foreign symbol from number");
                }
            }
        }
        return out;
    }
    public ArrayList<Long> fetch(String in){
        for (String i : everything.keySet()) {
            if (i.contains(target)) {
                return everything.get(i);
            }
        }return null;

    }

}

