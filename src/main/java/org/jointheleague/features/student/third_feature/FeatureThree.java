package org.jointheleague.features.student.third_feature;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.Button;
import org.javacord.api.entity.message.component.SelectMenu;
import org.javacord.api.entity.message.component.SelectMenuOption;
import org.javacord.api.event.interaction.MessageComponentCreateEvent;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.interaction.MessageComponentInteraction;
import org.javacord.api.listener.interaction.MessageComponentCreateListener;
import org.javacord.api.util.event.ListenerManager;
import org.jointheleague.discord_bot.DiscordBot;
import org.jointheleague.features.abstract_classes.Feature;
import org.jointheleague.features.help_embed.plain_old_java_objects.help_embed.HelpEmbed;
import org.jointheleague.features.templates.FeatureTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class FeatureThree extends FeatureTemplate {
    public final String COMMAND = "!fish";

    TextChannel channel;
    public double money = 0;
    public double luckModifier = 0.00;
    public String location = "Sea";
    public boolean[] bought = new boolean[16]; //Number in boolean is the total number of upgrades at the current moment.
    public List<String> locations = new ArrayList<String>();
    public int fishTimes = 1;
    boolean lobsterPotOwned = false;
    //public int lobsters = 0;
    public ArrayList<Integer> lobsters = new ArrayList<Integer>();
    public String win = "";

    public lootTableA[] testList = new lootTableA[60];

    ArrayList<ListenerManager<MessageComponentCreateListener>> lm = new ArrayList<ListenerManager<MessageComponentCreateListener>>();




    public FeatureThree(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "A luck based fishing game with lots of fish, sizes of fish, upgrades, and more. If new to !\u200Efish, please start with !\u200Efish tutorial."
        );
        locations.add(location);
        //locations.add("Test");


    }

    @Override
    public void handle(MessageCreateEvent event) {
        channel = event.getChannel();
        String messageContent = event.getMessageContent();
        if (messageContent.equals(COMMAND) || messageContent.equalsIgnoreCase(COMMAND+" tutorial")) {
            //respond to message here
            event.getChannel().sendMessage("Welcome to !\u200Efish. In this game you will fish, like the name implies. You will continue to fish and gain money. With this money you can upgrade you rod, luck, hook, bait, et cetera. With all of this you should get new better fish. This journey begins the command !\u200Efish menu. Your final goal is to retire, but that will cost you a pretty penny. You must fish up enough many across the different locations to win this game.");
        }
        else if (messageContent.equalsIgnoreCase(COMMAND + " menu")){
           // event.getChannel().sendMessage("Message to test whether TextChannel broke again.");
            new MessageBuilder().setContent("| Current Money: $" + money+" | Current Location: "+location+" | Luck Modifier: +" + luckModifier/5+" points | "+win).addComponents(ActionRow.of(Button.success("fish", "Go Fishing"),Button.secondary("modify", "Modify Set-Up"), Button.danger("lobPot", "Lobster Pot"))).send(channel);
            for (ListenerManager<MessageComponentCreateListener> i: lm){
                i.remove();
            }
            lm.add(event.getApi().addMessageComponentCreateListener(event2 -> {
               MessageComponentInteraction mci = event2.getMessageComponentInteraction();
               String cID = mci.getCustomId();
            mci.acknowledge();
               switch(cID){
                   case "fish":
                       event.getChannel().sendMessage("Going fishing...");
                       for(int i=0; i < fishTimes; i++) {
                           randomFish(location, event);
                       }
                       lobsterPotLuck(event);
                       break;
                   case "modify":
                       setupModification(event);
                       lobsterPotLuck(event);
                       break;
                   case "save" :
                       event.getChannel().sendMessage("SAVING");
                       break;
                   case "lobPot" :
                       for(int i = 0; i< lobsters.size(); i++) {
                           if (lobsters.get(i) > 0 && lobsterPotOwned == true) {
                               if (bought[12]) {
                                   money = money + 50 * lobsters.get(i) * locations.size();
                               }else{
                               money = money + 30 * lobsters.get(i) * locations.size();
                           }
                               if(bought[12]) {
                                   event.getChannel().sendMessage("You are attentive! There were " + lobsters.get(i) + " lobsters in pot " + (i + 1) + ". You sold these pot lobsters for $" + (50 * lobsters.get(i) * locations.size()) + "!");
                                   lobsters.set(i, 0);
                               }else{
                                   event.getChannel().sendMessage("You are attentive! There were " + lobsters.get(i) + " lobsters in pot " + (i + 1) + ". You sold these pot lobsters for $" + (30 * lobsters.get(i) * locations.size()) + "!");
                                   lobsters.set(i, 0);
                               }
                           } else if (lobsterPotOwned = true) {
                               event.getChannel().sendMessage("Bummer, no lobsters in pot "+(i+1)+". Maybe you should check it more often.");
                           } else {
                               event.getChannel().sendMessage("You look for the pot, hoping for lobsters until your realize your search is futile. You don't own a lobster pot yet!");
                           }
                       }
                       break;
               }


            }));
        }
        else if(messageContent.equals(COMMAND + " testMoney")){
            //money = money + 10000000;
           // luckModifier = luckModifier + 0;
          //  event.getChannel().sendMessage("We slipped you a bit of cash to get testing started. To warn you, there are "+lobsters+" lobsters in your pot");



        }
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public void addMoney(double money) {
        this.money = this.money + money;
    }
    public void randomFish(String location, MessageCreateEvent event) {
        Random random = new Random();
        //lootTableA[] list;
        // = new lootTableA[20]
        double totalWeight = 0.0;
        double ran;
        lootTableA lt = new lootTableA(0,0,"0");
        if (location.equals("Sea")) {
           lootTableA[] list = {new lootTableA(35.7525,4,"Minnow"),new lootTableA(32.5,7,"Sardine"),new lootTableA(9,16,"Sea Bass"),new lootTableA(15.5,12.5,"Tuna"),new lootTableA(12.5,13.5,"Reef Shark"),new lootTableA(3.5,75,"Squid"),new lootTableA(5,45,"Triggerfish"),new lootTableA(2.25,115,"Electric Eel"),new lootTableA(6.25,25,"Striper"),new lootTableA(1.75,145,"Lobster"),new lootTableA(0.975,160,"Swordfish"),new lootTableA(0.675,235,"Sunfish"),new lootTableA(0.0125,126500,"Walking Minnow"),new lootTableA(0.00565,127500,"Gold Minnow Statue")};
            testList = list;
           lootAssigning(totalWeight, event, lt, list);

          /*  for (int i = 0; i < list.length; i++) {
                totalWeight = totalWeight + list[i].getChance();
            }
            ran = random.nextDouble(totalWeight);
            totalWeight = 0;
            for (int i = 0; i < list.length; i++) {
                totalWeight = totalWeight + list[i].getChance();
                //if(totalWeight>ran){ //test luck addition system
                //    lt = list[i];
                //    break;
                // }
            }
            addMoney(lt.getValue());
            event.getChannel().sendMessage(" You caught a " + lt.name() + "! " + "| You sold this fish for $" + lt.getValue() + " | Your bank account is now at $" + money); */
        } else if (location.equals("Trench")) {
           lootTableA[] list = {new lootTableA(25.65,35,"Telescopefish"),new lootTableA(20.2575,40,"Rhinochimaera"),new lootTableA(15.125,55,"Anglerfish"),new lootTableA(12.25751,60,"Barreleye"),new lootTableA(11.524575,70,"Lanternfish"),new lootTableA(10.2575,75,"Glowing Minnow"),new lootTableA(8.7525,90,"Flying Minnow"),new lootTableA(5.245,125,"Abyssal Grenadier"),new lootTableA(5.65,120,"Marine Hatchetfish"),new lootTableA(2.5,165,"Goblin Shark"),new lootTableA(1.15, 225,"Jellyfish"),new lootTableA(0.75, 345, "Six-Gilled Shark"),new lootTableA(0.000125,35000000,"Snailfish")};
            testList = list;
           lootAssigning(totalWeight, event, lt, list);

          /*  lt2 = lootTableB.Telescopefish;
            for(int i =0; i < list2.length; i++){
                totalWeight = totalWeight + list2[i].getChance();
            }
            ran = random.nextDouble(totalWeight);
            totalWeight =0;
            for(int i =0; i < list2.length; i++){
                totalWeight = totalWeight + list2[i].getChance();
                //if(totalWeight>ran){ //test luck addition system
                //    lt = list[i];
                //    break;
                // }
            }
            addMoney(lt2.getValue());
            event.getChannel().sendMessage(" You caught a "+lt2.name() + "! "+"| You sold this fish for $"+lt2.getValue()+" | Your bank account is now at $" + money);
        }*/
        }
        else if (location.equals("The Coveted Coves")){
            lootTableA[] list = {new lootTableA(30,135,"Rockfish"),new lootTableA(22.7525,150,"Golemfish"),new lootTableA(15.752505,245,"Golden Rockfish"),new lootTableA(10.25,275,"Fire-Belly Fish"),new lootTableA(7.5,325,"Lightfish"),new lootTableA(7.5,325,"Darkfish"),new lootTableA(5,300,"Luckfish"),new lootTableA(4.5,400,"Swimming Fossil"),new lootTableA(4.5,425,"Riftfish"),new lootTableA(2.5,535,"Rodfish"),new lootTableA(2,650,"Bronzefish"),new lootTableA(2.5,585,"Moonfish"),new lootTableA(0.5,1475.25,"Boatfish"),new lootTableA(1,875,"Snarefish"),new lootTableA(1.5,750,"Cyclopic Golemfish"),new lootTableA(0.01,12575.25,"Chest of Fish"),new lootTableA(0.0001,1257525.75,"Chest of Precious Fish"),new lootTableA(0.000000001,75254525754525.75,"Bootfish")};
            testList = list;
            lootAssigning(totalWeight, event, lt, list);  //
            //                                                                                                                                                                                                                                                 Fire-Belly Fish could attack the player.                                                                                                                                                   Luckfish gives the player a very slight luck increase.                                                                                                                             Rodfish increases the number of times you can fish by clicking the fish button by one.                                                                                                                                              Boatfish makes the player randomly change to a different location             Snarefish trap or delay the player
        }
        else if (location.equals("Test")){
            lootTableA[] list = {new lootTableA(15,10,"Luckfish"),new lootTableA(15,10,"Rodfish"),new lootTableA(15,10,"Boatfish"),new lootTableA(15,10,"Snarefish")};
            testList = list;
            lootAssigning(totalWeight, event, lt, list);

        }

    }

public void lootAssigning(double totalWeight, MessageCreateEvent event,lootTableA lt,lootTableA[] list){
    lootTableB[] list2 = {new lootTableB(0.00125,0.01, "Sub-Atomic", "🥇"),new lootTableB(0.25, 0.1, "Microscopic", "🥈"), new lootTableB(1,0.125,"Miniature","🥉"),new lootTableB(1.25,0.5, "a Baby", "🥉"),new lootTableB(10,0.75,"Tiny","🏅"),new lootTableB(25.75,1,"Small","🏅"),new lootTableB(15.6525,1.25,"Medium","🏅"),new lootTableB(6.675,1.65,"Large","🏅"),new lootTableB(2.35,2.35,"Huge","🥉"),new lootTableB(1.25,3.15,"Gigantic","🥉"),new lootTableB(0.75,4,"Titanic","🥉"),new lootTableB(0.25,5.65,"Humongous","🥈"), new lootTableB(0.15,7.5,"Ginormous","🥈"),new lootTableB(0.015,12.75,"Record-Breaking","🥇"),new lootTableB(0.00125,100.75,"Cosmic","🌌"),new lootTableB(0.000345, 275.50, "Multi-Dimensional",  "🌌"),new lootTableB(0.000215,750.25, "Shiny", "🎖"),new lootTableB(0.000165,2575.45,"Special","🎖")};
    double totalWeight2 = 0;
    lootTableB size = new lootTableB(0,0,"Error","");
    for(int i =0; i < list.length; i++){
        totalWeight = totalWeight + list[i].getChance();
    }
    for(int i =0; i< list2.length; i++){
        totalWeight2 = totalWeight2 + list2[i].getWeight();
    }
    //lootTableB[] list2 = {new lootTableB(0.00125,0.01, "Sub-Atomic", "🥇"),new lootTableB(0.25, 0.1, "Mircoscopic", "🥈"), new lootTableB(1,0.125,"Miniature","🥉"),new lootTableB(10,0.75,"Tiny","🏅"),new lootTableB(25.75,1,"Small","🏅"),new lootTableB(15.6525,1.25,"Medium","🏅"),new lootTableB(6.675,1.65,"Large","🏅"),new lootTableB(2.35,2.35,"Huge","🥉"),new lootTableB(1.25,3.15,"Gigantic","🥉"),new lootTableB(0.75,4,"Titanic","🥉"),new lootTableB(0.25,5.65,"Humongous","🥈"), new lootTableB(0.15,7.5,"Ginormous","🥈"),new lootTableB(0.015,12.75,"Record-Breaking","🥇"),new lootTableB(0.00125,100.75,"Cosmic","🌌"),new lootTableB(0.000165,2575.45,"Special","🎖")};

    Random random = new Random();
    double ran = random.nextDouble()*totalWeight+luckModifier/10.75;
    double ran2 = random.nextDouble()*totalWeight2+luckModifier/17.25;
    totalWeight =0;
    totalWeight2 =0;
    for(int i =0; i < list.length; i++){
        totalWeight = totalWeight + list[i].getChance();
        if(totalWeight>ran){ //test luck addition system
            lt = list[i];
            break;
         } else if (i == list.length-1 && !(totalWeight>ran)){
            lt = list[i];
        }
    }
    for(int i =0; i< list2.length; i++){
        totalWeight2 = totalWeight2 + list2[i].getWeight();
        if(totalWeight2>ran2){
            size = list2[i];
            break;
        }else if (i == list2.length-1 && !(totalWeight2>ran2)){
            size = list2[i];
        }
    }

    addMoney(lt.getValue()*size.getMultiplier());
    //money = Math.round(money*100.0)/100.0;
    BigDecimal bd = new BigDecimal(money).setScale(2, RoundingMode.HALF_UP);
    money = bd.doubleValue();
    BigDecimal bd2 = new BigDecimal(lt.getValue()*size.getMultiplier()).setScale(2, RoundingMode.HALF_UP);
    //double earned = Math.round(lt.getValue()*size.getMultiplier()*100.0)/100.0;
    double earned = bd2.doubleValue();
    event.getChannel().sendMessage(" You caught a "+lt.name() +" that was "+ size.name() + "! "+"| You sold this fish for $"+earned+" | Your bank account is now at $" + money + " | The rarity symbols associated with this fish are "+size.getRankingEmoji()+"!");
    fishEffect(lt.name(), event);
    }
    public void setupModification(MessageCreateEvent event){
        new MessageBuilder().setContent("What would you like to modify in your set-up?").addComponents(ActionRow.of(Button.success("upgrade", "Buy Upgrades"),Button.success("location", "Change Location"), Button.danger("leave", "Buy Another Lobster Pot | $"+(125*lobsters.size())))).send(channel);
        lm.add(event.getApi().addMessageComponentCreateListener(event2 -> {
            MessageComponentInteraction mci = event2.getMessageComponentInteraction();
            String cID = mci.getCustomId();
            mci.acknowledge();
        switch(cID){
            case "upgrade":
                upgradeMenu(event);
                break;
            case "location":
                changeLocation(event);
                break;
            case "leave":
                //clear old message here
                if(lobsterPotOwned && bought[10]){
                    if(money>= (125*lobsters.size())){
                        money = money - (125*lobsters.size());
                        lobsters.add(0);
                        event.getChannel().sendMessage("Congratulations, you now have another lobster pot. Your bank account is now at $"+money);
                    } else{
                        event.getChannel().sendMessage("You don't have enough money to buy another lobster pot.");
                    }
                } else{
                    event.getChannel().sendMessage("Before trying to buy more lobster pots, consider buying your very first.");
                }
            break;
            default:
                break;
        }
        }));
        }
        public void upgradeMenu(MessageCreateEvent event) {
            new MessageBuilder().setContent("What would you like to buy").addComponents(ActionRow.of(Button.secondary("3","Rabbit's Foot | $15.00"),Button.secondary("4","Shinier Bait | $30.00"),Button.secondary("1", "Deep-Dive Lure | $35.75"),Button.secondary("10", "Lobster Pot | $50.00")),ActionRow.of(Button.secondary("0", "Carbon Fibre Rod | $125.25"),Button.secondary("5","Durable Fishing Line | $250.25"),Button.secondary("6","Four-Leaf Clover Pot | $300.00")),ActionRow.of(Button.secondary("7","Larger Bait | $350.50"),Button.secondary("8","Multi-Hooked Lure | $650.75"), Button.secondary("2", "Better Boat Service | $1250.75")),ActionRow.of(Button.secondary("11", "Fish Detector | $1750.00"),Button.secondary("12", "Lobster Pass | $2575.25"),Button.secondary("13", "Second Boat | $12750.25")), ActionRow.of(Button.secondary("9", "Treasure Map | $27500.25"),Button.secondary("14","Retirement | $1500000.00"))).send(channel);
            lm.add(event.getApi().addMessageComponentCreateListener(event2 -> {
            MessageComponentInteraction mci = event2.getMessageComponentInteraction();
            String cID = mci.getCustomId();
                mci.acknowledge();
            switch (cID) {
                case "0":
                    if(money>=125.25 && !bought[0]) {
                        money = money - 125.25;
                        luckModifier = luckModifier + 3.75;
                        event.getChannel().sendMessage("Congrats, you now own a carbon fibre fishing rod! This upgrade increases your luck by allowing you to haul up the better fish easier.");
                        bought[0] = true;
                    }
                    else if(money<125.25 && !bought[0]){
                        event.getChannel().sendMessage("You cannot afford this item.");
                    }
                    else{
                        event.getChannel().sendMessage("You cannot buy an item you already have.");
                    }
                    break;
                case "1":
                    if(money>=35.75 && !bought[1]) {
                        money = money - 35.75;
                        luckModifier = luckModifier + 2.5;
                        event.getChannel().sendMessage("Congrats, you now own a deep-dive lure! This upgrade slightly increases your luck by going deeper into the water where the better fish reside.");
                        bought[1] = true;
                    }
                    else if(money<35.75 && !bought[1]){
                        event.getChannel().sendMessage("You cannot afford this item.");
                    }
                    else{
                        event.getChannel().sendMessage("You cannot buy an item you already have.");
                    }
                    break;
                case "2":
                    if(money>=1250.75 && !bought[2]) {
                        money = money - 1250.75;
                        luckModifier = luckModifier + 3;
                        event.getChannel().sendMessage("Congrats, you now have a better boat service!  This upgrade slightly increases your luck and allows you to travel to a new location, the trench.");
                        bought[2] = true;
                        locations.add("Trench");
                        //new location available
                    }
                    else if(money<1250.75 && !bought[2]){
                        event.getChannel().sendMessage("You cannot afford this item.");
                    }
                    else{
                        event.getChannel().sendMessage("You cannot buy an item you already have.");
                    }
                    break;
                case "3":
                    if(money>=15 && !bought[3]) {
                        money = money - 15;
                        luckModifier = luckModifier + 1.25;
                        event.getChannel().sendMessage("Congrats, you now own a rabbits foot! This might increase your luck if you focus on the results.");
                        bought[3] = true;
                    }
                    else if(money<15 && !bought[3]){
                        event.getChannel().sendMessage("You cannot afford this item.");
                    }
                    else{
                        event.getChannel().sendMessage("You cannot buy an item you already have.");
                    }
                    break;
                case "4":
                    if(money>=30 && !bought[4]) {
                        money = money - 30;
                        luckModifier = luckModifier + 1.75;
                        event.getChannel().sendMessage("Congrats, you now own a bunch of shinier bait! This upgrade slightly increases your luck by attracting bigger and better fish.");
                        bought[4] = true;
                    }
                    else if(money<30 && !bought[4]){
                        event.getChannel().sendMessage("You cannot afford this item.");
                    }
                    else{
                        event.getChannel().sendMessage("You cannot buy an item you already have.");
                    }
                    break;
                case "5":
                    if(money>=250.25 && !bought[5]) {
                        money = money - 250.25;
                        //luckModifier = luckModifier + 5.75;
                        fishTimes++;
                        event.getChannel().sendMessage("Congrats, you now own more durable fishing line! This upgrade allows you to catch an extra fish each time you fish.");
                        bought[5] = true;
                    }
                    else if(money<250.25 && !bought[5]){
                        event.getChannel().sendMessage("You cannot afford this item.");
                    }
                    else{
                        event.getChannel().sendMessage("You cannot buy an item you already have.");
                    }
                    break;
                case "6":
                    if(money>=300 && !bought[6]) {
                        money = money - 300;
                        luckModifier = luckModifier + 5.75;
                        event.getChannel().sendMessage("Congrats, you now own a four-leaf clover pot! This upgrade increases your luck because common video game tropes make great features.");
                        bought[6] = true;
                    }
                    else if(money<300 && !bought[6]){
                        event.getChannel().sendMessage("You cannot afford this item.");
                    }
                    else{
                        event.getChannel().sendMessage("You cannot buy an item you already have.");
                    }
                    break;
                case "7":
                    if(money>=350.5 && !bought[7]) {
                        money = money - 350.5;
                        luckModifier = luckModifier + 6.75;
                        event.getChannel().sendMessage("Congrats, you now own a bunch of larger bait! This upgrade increases your luck by making the bigger fish want to eat the bigger easy food.");
                        bought[7] = true;
                    }
                    else if(money<350.5 && !bought[7]){
                        event.getChannel().sendMessage("You cannot afford this item.");
                    }
                    else{
                        event.getChannel().sendMessage("You cannot buy an item you already have.");
                    }
                    break;
                case "8":
                    if(money>=650.75 && !bought[8]) {
                        money = money - 650.75;
                        //luckModifier = luckModifier + 5.75;
                        fishTimes = fishTimes + 2;
                        event.getChannel().sendMessage("Congrats, you now own a multi-hook lure! This upgrade allows you to catch two more fish in one go.");
                        bought[8] = true;
                    }
                    else if(money<650.75 && !bought[8]){
                        event.getChannel().sendMessage("You cannot afford this item.");
                    }
                    else{
                        event.getChannel().sendMessage("You cannot buy an item you already have.");
                    }
                    break;
                case "9":
                    //Fix messages not sending
                    if(money>=27500.25 && !bought[9]) {
                    money = money - 27500.25;
                    //luckModifier = luckModifier + 5.75;
                    //fishTimes = fishTimes + 2;
                    locations.add("The Coveted Coves");
                    event.getChannel().sendMessage("Congrats, you now own a treasure map! This upgrade allows you to travel to the next location, The Coveted Coves.");
                    bought[9] = true;
                }
                    else if(money<27500.25 && !bought[9]){
                    event.getChannel().sendMessage("You cannot afford this item.");
                }
                else{
                    event.getChannel().sendMessage("You cannot buy an item you already have.");
                }
                break;
                case "10":
                    if(money>=50 && !bought[10]) {
                        money = money - 50;
                        //luckModifier = luckModifier + 5.75;
                        //fishTimes = fishTimes + 2;
                        lobsterPotOwned = true;
                        lobsters.add(0);
                        event.getChannel().sendMessage("Congrats, you now own a lobster pot! This upgrade allows you to idly catch small lobsters when fishing and shopping for upgrades.");
                        bought[10] = true;
                    }
                    else if(money<50 && !bought[10]){
                        event.getChannel().sendMessage("You cannot afford this item.");
                    }
                    else{
                        event.getChannel().sendMessage("You cannot buy an item you already have.");
                    }
                    break;
                case"11":
                    if(money>=1750 && !bought[11]) {
                        money = money - 1750;
                        luckModifier = luckModifier + 10.75;
                        //fishTimes = fishTimes + 2;
                        event.getChannel().sendMessage("Congrats, you now own a fish detector! This upgrade allows you to detect better fish and increases your luck.");
                        bought[11] = true;
                    }
                    else if(money<1750 && !bought[11]){
                        event.getChannel().sendMessage("You cannot afford this item.");
                    }
                    else{
                        event.getChannel().sendMessage("You cannot buy an item you already have.");
                    }
                    break;
                case "12":
                    //2575.25
                    if(money>=2575.25 && !bought[12]) {
                        money = money - 2575.25;
                        //luckModifier = luckModifier + 5.75;
                        fishTimes = fishTimes + 2;
                        event.getChannel().sendMessage("Congrats, you now own a lobster pass! This upgrade allows you to sell all your pot lobsters for a bit more money and to fish two more times in one go.");
                        bought[12] = true;
                    }
                    else if(money<2575.25 && !bought[12]){
                        event.getChannel().sendMessage("You cannot afford this item.");
                    }
                    else{
                        event.getChannel().sendMessage("You cannot buy an item you already have.");
                    }
                    break;
                case "13":
                    //12750.25
                    if(money>=12750.25 && !bought[13]) {
                        money = money - 12750.25;
                        //luckModifier = luckModifier + 5.75;
                        fishTimes = fishTimes + 6;
                        event.getChannel().sendMessage("Congrats, you now own a second boat! This upgrade allows you to fish a whopping six more times at the click of one button.");
                        bought[13] = true;
                    }
                    else if(money<12750.25 && !bought[13]){
                        event.getChannel().sendMessage("You cannot afford this item.");
                    }
                    else{
                        event.getChannel().sendMessage("You cannot buy an item you already have.");
                    }
                    break;
                case "14":
                    //1500000
                    if(money>=1500000.00 && !bought[14]) {
                        money = money - 1500000.00;
                        //luckModifier = luckModifier + 5.75;
                        //fishTimes = fishTimes + 2;
                        win = "🏆 Winner! 🏆 |";
                        event.getChannel().sendMessage("Congrats, you now are retired! You can still fish, but you finally feel accomplished and have won this game.");
                        bought[14] = true;
                    }
                    else if(money<1500000.00 && !bought[14]){
                        event.getChannel().sendMessage("You cannot afford this item.");
                    }
                    else{
                        event.getChannel().sendMessage("You cannot buy an item you already have.");
                    }
                    break;
            }
        }));
        }
        public void changeLocation (MessageCreateEvent event){
        List<SelectMenuOption> temp = new ArrayList<SelectMenuOption>();
        //for(int i=0; i < locations.size(); i++){
            //temp.add(SelectMenuOption.create(locations.get(i), "Click here to travel to " + locations.get(i)));
       // }
            MessageBuilder mb = new MessageBuilder();
            //SelectMenu menu = SelectMenu.createStringMenu("locationSelect", temp);
                    mb.setContent("Choose Your New Location");
                    for(int i =0; i < locations.size(); i++){
                        mb.addComponents(ActionRow.of(Button.secondary(locations.get(i),locations.get(i))));
                    }
                    mb.send(channel);
            lm.add(event.getApi().addMessageComponentCreateListener(event2 -> {
                MessageComponentInteraction mci = event2.getMessageComponentInteraction();
                String cID = mci.getCustomId();
                mci.acknowledge();
                switch(cID){
                    case "Sea":
                        location = "Sea";
                        break;
                    case "Trench":
                        location = "Trench";
                        break;
                    case "The Coveted Coves":
                        location = "The Coveted Coves";
                        break;
                    case "Test":
                        location = "Test";
                        break;
                }
            }));
         event.getApi().removeListener(MessageComponentCreateListener.class, null);
        }
        void lobsterPotLuck(MessageCreateEvent event){
        for(int i =0; i < lobsters.size(); i++){
        if(lobsterPotOwned) {
            //event.getChannel().sendMessage("TESTPOT");
            int pot = 16;
            Random ran = new Random();
            double ranint = ran.nextDouble()*pot;
            if (ranint >= (pot - 2 - luckModifier / 16)) {
                lobsters.set(i, lobsters.get(i) + (ran.nextInt(2 + locations.size()) + 1));
                //event.getChannel().sendMessage("Congrats, lobsters added. TESTMESSAGE");
            } else {
                if (lobsters.get(i) != 0) {
                    int removed = ran.nextInt(lobsters.get(i));
                    lobsters.set(i, lobsters.get(i) - removed);
                    //event.getChannel().sendMessage("Bummer, "+removed+" lobsters were removed from your pot. TESTMESSAGE");
                } else {
                    //event.getChannel().sendMessage("Bummer, nothing happened. TESTMESSAGE");
                }
            }
        }
        }
        }
    public void fishEffect(String fishName, MessageCreateEvent event){
        switch(fishName){
            case "Luckfish":
                event.getChannel().sendMessage("Congrats, the Luckfish you caught bestowed you with slightly more luck!");
                luckModifier = luckModifier + 1.25;
                break;
            case "Boatfish":
                Random ran = new Random();
                int ranInt = ran.nextInt(locations.size());
                if(ranInt==0){
                    location = "Sea";
                } else if(ranInt==1){
                    location = "Trench";
                } else{
                    location = "Sea";
                    event.getChannel().sendMessage("RanInt failed to pick a location this time. "+ranInt);
                }
                event.getChannel().sendMessage("Bummer, the Boatfish you caught brought you back to one of your other locations.");
                break;
            case "Rodfish":
                event.getChannel().sendMessage("Congrats, the Rodfish you caught brought you another fishing pole, allowing you to fish another time per button press!");
                fishTimes = fishTimes + 1;
                break;
            case "Snarefish":
            // try {
                ran = new Random();
                double random = ran.nextDouble()*(money-1)+1;
                BigDecimal bd = new BigDecimal(random).setScale(2, RoundingMode.HALF_UP);
                random = bd.doubleValue();
               money = money-random;
                 event.getChannel().sendMessage("The Snarefish has snagged your wallet, causing you to lose $"+random+".");
            //     event.wait(20000);
            // } catch(Exception e){
            //     event.getChannel().sendMessage("This game is having trouble processing the effects of the Snarefish. Exception is "+e+ ".");
            // }
                break;
        }
    }
        }





