package org.jointheleague.features.student;

import org.jointheleague.api_wrapper.ReceivedMessage;
import org.jointheleague.discord_bot.DiscordBot;
import org.jointheleague.features.examples.third_features.plain_old_java_objects.AuctionDataWrapper;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.jointheleague.features.templates.FeatureTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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


    private static final String baseUrl = "https://api.hypixel.net/v2/skyblock/auctions";
    /*USER PROCESS
        /money
            if blank it will ask for input, if not, takes in stuff after space as input (all inputs)
        checks what comes back with the input, and confirms with the user if it's what they want
                if there's multiple allow user to say number/name of item to confirm
        ask to set price
                allow for commas/ k/m/b markers

     */
    public final String COMMAND = "/money";
    public HelpEmbed helpEmbed;
    public ArrayList<String> fallbackSearch = new ArrayList<>();
    public ArrayList<String> emptySearch = new ArrayList<>();
    protected String channelName;
    int indexed = 0;
    int count = 0;
    long max = 10_002_000_000_000L;
    long total = 0;
    int averageRange = 5;
    String target = "NUL";
    String minp = "8m";
    String maxp = "12m";
    int minc = 7;
    int maxc = 9;
    Map<String, ArrayList<Long>> everything = new HashMap<>(50_000_000);
    DiscordBot discord;
    String specTarget;
    String msg = "";
    String[] reforges = {"Awkward", "Rich", "Clean", "Fierce", "Heavy", "Light", "Mythic", "Pure", "Smart", "Titanic", "Wise", "Bizarre", "Itchy", "Ominous",
            "Pleasant", "Pretty", "Shiny", "Simple", "Strange", "Vivid", "Godly", "Demonic", "Forceful", "Hurtful", "Keen", "Strong", "Superior", "Unpleasant", "Zealous",
            "Deadly", "Fine", "Grand", "Hasty", "Neat", "Rapid", "Unreal", "Epic", "Fair", "Fast", "Gentle", "Heroic", "Legendary", "Odd", "Sharp", "Spicy", "Salty", "Treacherous",
            "Stiff", "Lucky", "Very", "Highly", "Extremely", "Not", "Possibly", "Fabled", "Suspicious", "Warped", "Withered", "Bulky", "Jerry's", "Salty", "Treacherous",
            "Heated", "Auspicious", "Fleet", "Magnetic", "Mithraic", "Refined", "Stellar", "Fruitful", "Toil", "Blessed", "Bountiful", "Moil", "Groovy", "Green Thumb", "Candied",
            "Submerged", "Reinforced", "Cubic", "Giant", "Loving", "Perfect", "Necrotic", "Ancient", "Spiked", "Renowned", "Redone", "Cubic", "Empowered", "Cold", "Frigid",
            "Bloodshot", "Waxed", "Fortified", "Strengthened", "Shiny", "Glistening", "Rooted", "Blooming", "Snowy", "Festive", "Headstrong", "Burgeoning", "precise", "spiritual"};
    String name = "";
    String[] symbols = {"➊", "➋", "➌", "➍", "➎"};
    long cheap;
    String temp = "";
    long out = 0;
    long track = 0;
    int index = 0;
    int counter = 0;
    long specPrice;
    int specTotal;
    Boolean print = true;
    private final WebClient webClient;

    //https://www.brandonfowler.me/skyblockah/

    public PriceNotifier(String channelName, DiscordBot discord) {
        super(channelName);
        this.discord = discord;

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "gives a brief price summary for item on the Skyblock auction house" +
                        "\nhttps://api.hypixel.net/#tag/SkyBlock/paths/~1v2~1skyblock~1auctions/get"
        );

        this.webClient = WebClient
                .builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(2 * 1024 * 1024 * 2))
                .build()
        ;
    }

    public HelpEmbed getHelpEmbed() {
        return this.helpEmbed;
    }

    @Override
    public void handle(ReceivedMessage event) {
        if (!event.getAuthor().equals("1424893152765935738")) {
            String messageContent = event.getMessageContent();
            target = "NUL";
            commandParser(messageContent);

            println("MESSAGE: " + messageContent + " (" + event.getAuthor() + ")");

            //main command
            if (messageContent.split(" ")[0].toLowerCase().startsWith(COMMAND)
                //        || messageContent.split(" ")[0].toLowerCase().startsWith("m")
            ) {
                msg = "";

                cheap = cheapest();
                if (!specTarget.isEmpty()) {
                    event.sendResponse("\u200Esearching for " + specTarget + " under " + f(max));
                    msg = ("count: " + getCount(fetch(specTarget)) +
                            "\naveraged bottom " + averageRange + " to: " + f(averageN(fetch(specTarget))) + "\n" + "cheapest " + specTarget + ": " + f(cheap) + msg);
                    event.sendResponse(msg);
                } else {
                    if (target.equals("NUL")) {
                        event.sendResponse("/money [item name] [max item price (optional)] [how many to average across + \"r\"](optional)");
                    } else {
                        event.sendResponse(target + " was not a part of any item name listed for BIN");
                    }
                }
            }
            //finds items within a certain price point that appear a certain amount of times
            else if (messageContent.split(" ")[0].toLowerCase().startsWith("search")) {
                commandParser(messageContent);
                ArrayList<String> found = dealFinder();
                String deals = found.get(0);
                for (int i = 1; i < found.size() - 1; i++) {
                    deals += "\n" + found.get(i);
                }
                event.sendResponse(deals);
            } else if (messageContent.split(" ")[0].toLowerCase().contains("bye lebron")) {
                discord.sendMessage("disconnecting");
                println("SHUTTING DOWN");
                discord.api.shutdown();
            }
        }
    }

    //gets the AH data of a specific page (used in index)
    public AuctionDataWrapper getData(int page) {
        println(baseUrl + "?page=" + page);
        AuctionDataWrapper out;
        try {
            out = webClient.get().uri(baseUrl + "?page=" + page).retrieve().bodyToMono(AuctionDataWrapper.class).block();
        } catch (Exception e) {
            println(page + "data spoofed");
            out = new AuctionDataWrapper();
            out.spoof();
        }

        return out;
    }

    //iterates through every auction for stats message and to make dealfinder work
    //iterates through every auction and populates the price and quantity arrays
    //works by saving arraylists containing every bin price of an item and saving the arraylists to hashmaps with the item name as the key
    public void index() {

        count = 0;
        indexed = 0;
        println("ran");
        boolean run = true;
        AuctionDataWrapper data;
        int i = 0;
        while (run) {
            data = getData(i);
            if (i % 5 == 0) {
                discord.progressMsg(i, data.getTotalPages());
            }
            if (data.getStatus()) {
                for (int j = 0; j < data.getAuctions().length - 1; j++) {
                    indexed++;
                    if (data.getAuctions()[j].getBin()) {
                        //clears reforge
                        name = star(data.getAuctions()[j].getItem_name().toLowerCase());
                        Arrays.stream(reforges).forEach(x -> {
                            if (name.startsWith(x.toLowerCase())) {
                                name = name.replace(x.toLowerCase() + " ", "");
                            }
                        });
                        //if it hasn't already, adds a new arraylist to the hashmap matching the item's name
                        everything.putIfAbsent(name.toLowerCase(), new ArrayList<>());
                        //adds the item's price to the arraylist
                        everything.get(name.toLowerCase())
                                .add(data.getAuctions()[j].getStarting_bid());
                    }
                }
            } else {
                run = false;
            }
            i++;
        }
        println("indexing finished");
        discord.sendFinish();
    }

    public long cheapest() {
        long cheapest = max;
        specTarget = "";
        for (String i : everything.keySet()) {
            if (i.contains(target)) {
                println("found, " + i);
                for (int j = 0; j < everything.get(i).size(); j++) {
                    if (everything.get(i).get(j) < cheapest) {
                        cheapest = everything.get(i).get(j);
                        println("\nnew cheapest: " + everything.get(i).get(j) + " - " + i);
                        specTarget = i;

                    }
                }
            }
        }
        println("spec > " + specTarget);

        return cheapest;
    }

    //finds the average price of the (5) cheapest numbers in an array
    //really unoptimized, iterates through everything in the "best" array to find the
    //most expensive index everytime, then replaces that one
    public long averageN(ArrayList<Long> in) {
        out = 0;
        track = 0;
        index = 0;
        counter = 0;
        total = 0;
        long[] best = new long[averageRange];
        Arrays.fill(best, max);
        for (Long aLong : in) {
            track = 0;
            index = 0;
            total += aLong;
            for (int j = 0; j < averageRange; j++) {

                if (best[j] > track) {
                    track = best[j];
                    index = j;
                }
            }
            if (aLong < best[index]) {
                best[index] = aLong;
            }
        }
        for (int j = 0; j < averageRange; j++) {

            if (best[j] == max) {
                best[j] = 0;
                counter++;
            }
            println("N best: " + best[j]);
            out += best[j];
        }
        println("N out: " + out);
        println("N counter: " + counter);
        println("N array size: " + best.length);
        println("N in: " + in);
        return (out / (best.length - counter));
    }

    //finds the best items to test with
    public ArrayList<String> dealFinder() {
        if (!maxp.equals("0")) {
            println(minp + " " + maxp + " " + minc + " " + maxc);
            ArrayList<String> out = new ArrayList<>();

            for (String key : everything.keySet()) {

                specPrice = 0;
                specTotal = 0;
                for (int i = 0; i < everything.get(key).size(); i++) {
                    specPrice += everything.get(key).get(i);
                    specTotal++;
                }
                if (minc <= specTotal && maxc >= specTotal && specPrice < fd(maxp) && specPrice > fd(minp)) {
                    println(f(specPrice) + " for " + specTotal + " " + star(key) + "s");
                    out.add(f(specPrice) + " for " + specTotal + " " + star(key) + "s");
                }
            }
            if (!out.isEmpty()) {
                return out;
            } else {
                return emptySearch;
            }
        } else {
            return fallbackSearch;
        }
    }

    //Rewrites star values to be more readable (used in simplifying index inputs)
    public String star(String in) {
        temp = in;
        for (String symbol : symbols) {
            in = in.replace(symbol, "");
        }
        in = in.replaceAll(" ✪", "");
        in = in.replaceAll("✪", "");
        return in;
    }

    //sets variables (target & setPrice) to what the user inputs
    public void commandParser(String in) {
        //main command
        //start, item name, price (optional)
        if (in.toLowerCase().startsWith("m") || in.toLowerCase().startsWith(COMMAND)) {
            println("1");
            int offset = 0;
            String[] split = in.toLowerCase().split(" ");
            if (split.length > 1) {
                target = split[1];
                println("2");
                try {
                    if (split[split.length - 1].charAt(split[split.length - 1].length() - 1) == 'r') {
                        println("3" + (split[split.length - 1].substring(0, (split[split.length - 1].length() - 1))));
                        averageRange = Integer.parseInt((split[split.length - 1].substring(0, (split[split.length - 1].length() - 1))));
                        println("4");
                        println("range changed to " + averageRange);
                        split[split.length - 1] = split[split.length - 2];
                        println("5");
                        offset++;
                    }
                    println("6");
                    println("suffix -> " + split[split.length - 1]);
                    max = fd(split[split.length - 1].trim());
                    println(max + "<- setPrice");
                    offset++;
                } catch (Exception e) {
                    println("suffix not a number/range");
                }
                for (int i = 2; i < split.length - offset; i++) {
                    target += " " + split[i];
                }
                println(offset + " offset");
                println(target + "<- target");
            } else {
                println("no input/too many " + split.length);

            }
        }
        //search
        if (in.toLowerCase().startsWith("search")) {
            String[] split = in.toLowerCase().split(" ");
            if (split.length == 5) {
                minp = split[1];
                maxp = split[2];
                minc = Integer.parseInt(split[3]);
                maxc = Integer.parseInt(split[4]);
            } else {
                maxp = "0";
            }
        }
    }

    //formats numbers to have commas or k/m/b/t
    public String f(long in) {
        String marker = "";
        String str = "" + in;
        String out = "";
        int track = 0;
        for (int i = str.length() - 1; i >= 0; i--) {
            if (track == 3) {
                track = 0;
                out = "," + out;
            }
            out = str.charAt(i) + out;
            track++;
        }
//     change this 0 to 1 if you don't want to abbreviate thousands |
        if (out.chars().filter(num -> num == ',').count() > 0) {
            str = out.split(",")[0];
            if (out.split(",")[1].charAt(0) != '0') {
                str += "." + out.split(",")[1].charAt(0);
            }
            if (out.chars().filter(num -> num == ',').count() == 2) {
                marker = "m";
            } else if (out.chars().filter(num -> num == ',').count() == 3) {
                marker = "b";
            } else if (out.chars().filter(num -> num == ',').count() == 4) {
                marker = "t";
            } else if (out.chars().filter(num -> num == ',').count() == 1) {
                marker = "k";
            } else {
                marker = "q";
                println("over 4 commas in number");
            }

        }

        return str + marker;
    }

    //decodes formatted numbers
    public long fd(String in) {
        long mult = 1;
        long out = 67_000_000_000L;
        if (in.contains(",")) {
            out = Long.parseLong(in.replace(",", ""));
        }
        if (in.contains(".")) {
            in = in.replace(".", "");
            mult = 10;
        }
        long prefixNum = Long.parseLong(in.substring(0, in.length() - 1));
        if (in.contains("k")) {
            out = prefixNum * 1_000;
        } else if (in.contains("m")) {
            out = prefixNum * 1_000_000;
        } else if (in.contains("b")) {
            out = prefixNum * 1_000_000_000;
        } else if (in.contains("t")) {
            out = prefixNum * 1_000_000_000_000L;
        } else {
            try {
                out = Long.parseLong(in);
            } catch (Exception e) {
                println("removed foreign symbol from number");
            }
        }

        println(mult + "x parsed as: " + (out / mult) + " f" + f(out / mult));
        return out / mult;
    }

    public ArrayList<Long> fetch(String in) {
        for (String i : everything.keySet()) {
            if (i.contains(in)) {
                println("Fetched " + i + " as " + everything.get(i));
                return everything.get(i);
            }
        }
        return null;

    }

    public int getCount(ArrayList<Long> in) {
        int itemCount = 0;
        for (Long aLong : in) {
            if (aLong < max) {
                itemCount++;
            }
        }
        return itemCount;
    }

    public void println(Object in) {
        if (print) {
            System.out.println(in);
        }
    }
} 