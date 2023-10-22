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
    public boolean[] bought = new boolean[3]; //Number in boolean is the total number of upgrades at the current moment.
    public List<String> locations = new ArrayList<String>();

    ArrayList<ListenerManager<MessageComponentCreateListener>> lm = new ArrayList<ListenerManager<MessageComponentCreateListener>>();




    public FeatureThree(String channelName) {
        super(channelName);

        //Create a help embed to describe feature when !help command is sent
        helpEmbed = new HelpEmbed(
                COMMAND,
                "A luck based fishing game with lots of fish, sizes of fish, qualities of fish, upgrades, and more. If new to !\u200Efish, please start with !\u200Efish tutorial. REMEMBER TO ADD A SAVING FEATURE TO ADD A TEXT KEY TO RESTORE PROGRESS"
        );
        locations.add(location);

    }

    @Override
    public void handle(MessageCreateEvent event) {
        channel = event.getChannel();
        String messageContent = event.getMessageContent();
        if (messageContent.equals(COMMAND) || messageContent.equalsIgnoreCase(COMMAND+" tutorial")) {
            //respond to message here
            event.getChannel().sendMessage("Welcome to !\u200Efish. In this game you will fish, like the name implies. You will continue to fish and gain money. With this money you can upgrade you rod, luck, hook, bait, et cetera. With all of this you should get new better fish. This journey begins the command !\u200Efish menu \nFish Size Value Multiplication Chart: PLACEHOLDER \nRarity Value Multiplication Chart: PLACEHOLDER \n*BETA TUTORIAL*");
        }
        else if (messageContent.equalsIgnoreCase(COMMAND + " menu")){
           // event.getChannel().sendMessage("Message to test whether TextChannel broke again.");
            new MessageBuilder().setContent("| Current Money: $" + money+" | Current Location: "+location+" | Luck Modifier: +" + luckModifier/5+" points |").addComponents(ActionRow.of(Button.success("fish", "Go Fishing"),Button.secondary("modify", "Modify Set-Up"),Button.secondary("save", "Save Game"))).send(channel);
            for (ListenerManager<MessageComponentCreateListener> i: lm){
                i.remove();
            }
            lm.add(event.getApi().addMessageComponentCreateListener(event2 -> {
               MessageComponentInteraction mci = event2.getMessageComponentInteraction();
               String cID = mci.getCustomId();

               switch(cID){
                   case "fish":
                       event.getChannel().sendMessage("Going fishing...");
                       randomFish(location, event);
                       randomFish(location, event);
                       randomFish(location, event);
                       randomFish(location, event);
                       randomFish(location, event);
                       randomFish(location, event);
                       break;
                   case "modify":
                       setupModification(event);
                       break;
                   case "save" :
                       event.getChannel().sendMessage("SAVING");
                       break;
               }


            }));
        }
        else if(messageContent.equals(COMMAND + " testMoney")){
            money = money + 10000000;
            luckModifier = luckModifier + 2135450;
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
    }

public void lootAssigning(double totalWeight, MessageCreateEvent event,lootTableA lt,lootTableA[] list){
    lootTableB[] list2 = {new lootTableB(0.00125,0.01, "Sub-Atomic", "🥇"),new lootTableB(0.25, 0.1, "Mircoscopic", "🥈"), new lootTableB(1,0.125,"Miniature","🥉"),new lootTableB(10,0.75,"Tiny","🏅"),new lootTableB(25.75,1,"Small","🏅"),new lootTableB(15.6525,1.25,"Medium","🏅"),new lootTableB(6.675,1.65,"Large","🏅"),new lootTableB(2.35,2.35,"Huge","🥉"),new lootTableB(1.25,3.15,"Gigantic","🥉"),new lootTableB(0.75,4,"Titanic","🥉"),new lootTableB(0.25,5.65,"Humongous","🥈"), new lootTableB(0.15,7.5,"Ginormous","🥈"),new lootTableB(0.015,12.75,"Record-Breaking","🥇"),new lootTableB(0.00125,100.75,"Cosmic","🌌"),new lootTableB(0.000165,2575.45,"Special","🎖")};
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
    double ran = random.nextDouble(totalWeight)+luckModifier/5;
    double ran2 = random.nextDouble(totalWeight2)+luckModifier/12.5;
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
    money = Math.round(money*100.0)/100.0;
    double earned = Math.round(lt.getValue()*size.getMultiplier()*100.0)/100.0;
    event.getChannel().sendMessage(" You caught a "+lt.name() +" that was "+ size.name() + "! "+"| You sold this fish for $"+earned+" | Your bank account is now at $" + money + " | The rarity symbols associated with this fish are "+size.getRankingEmoji()+"!");
}
    public void setupModification(MessageCreateEvent event){
        new MessageBuilder().setContent("What would you like to modify in your set-up?").addComponents(ActionRow.of(Button.success("upgrade", "Buy Upgrades"),Button.success("location", "Change Location"), Button.danger("leave", "Cancel"))).send(channel);
        lm.add(event.getApi().addMessageComponentCreateListener(event2 -> {
            MessageComponentInteraction mci = event2.getMessageComponentInteraction();
            String cID = mci.getCustomId();

        switch(cID){
            case "upgrade":
                upgradeMenu(event);
                break;
            case "location":
                changeLocation(event);
                break;
            case "leave":
                //clear old message here
                event.getChannel().sendMessage("Cancelling modification menu. Please go back to !fish menu.");
            break;
            default:
                break;
        }
        }));
        }
        public void upgradeMenu(MessageCreateEvent event) {
            new MessageBuilder().setContent("What would you like to buy").addComponents(ActionRow.of(Button.secondary("0", "Carbon Fibre Rod | $125.25"), Button.secondary("1", "Deep-Dive Lure | $35.50"), Button.secondary("2", "Better Boat Service | $1250.75"))).send(channel);
            lm.add(event.getApi().addMessageComponentCreateListener(event2 -> {
            MessageComponentInteraction mci = event2.getMessageComponentInteraction();
            String cID = mci.getCustomId();
            //add system to stop purchasing multiple  plus a buy message.
            switch (cID) {
                case "0":
                    if(money>=125.25 && !bought[0]) {
                        money = money - 125.25;
                        luckModifier = luckModifier + 25;
                        event.getChannel().sendMessage("Congrats, you now own a carbon fibre fishing rod! This upgrades increases your luck.");
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
                    if(money>=35.50 && !bought[1]) {
                        money = money - 35.50;
                        luckModifier = luckModifier + 5.75;
                        event.getChannel().sendMessage("Congrats, you now own a deep-dive lure! This upgrades slightly increases your luck.");
                        bought[1] = true;
                    }
                    else if(money<35.50 && !bought[1]){
                        event.getChannel().sendMessage("You cannot afford this item.");
                    }
                    else{
                        event.getChannel().sendMessage("You cannot buy an item you already have.");
                    }
                    break;
                case "2":
                    if(money>=1250.75 && !bought[2]) {
                        money = money - 1250.75;
                        luckModifier = luckModifier + 12.5;
                        event.getChannel().sendMessage("Congrats, you now have a better boat service!  This upgrades slightly increases your luck and allows you to travel to a new location, the trench.");
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
                switch(cID){
                    case "Sea":
                        location = "Sea";
                        break;
                    case "Trench":
                        location = "Trench";
                        break;
                }
            }));
         event.getApi().removeListener(MessageComponentCreateListener.class, null);
        }
        }





