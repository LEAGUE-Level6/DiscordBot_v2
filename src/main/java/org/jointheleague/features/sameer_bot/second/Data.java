package org.jointheleague.features.sameer_bot.second;
import java.util.HashMap;
import java.util.ArrayList;
public class Data {
    public static HashMap<String, Integer> userToCoins = new HashMap<>();
    public static HashMap<String, ArrayList<String>> userToInventory = new HashMap<>();

    public static String[] validItems = {"milk","tomato"};
    public static String[] formattedItems = {":milk: Milk", ":tomato: Tomato"};
    public static int[] prices = {10,8};

    public static int getMoney(String id) {
        if (!Data.userToCoins.containsKey(id)) {
            Data.userToCoins.put(id, 50);
            return 50;
        }
        return Data.userToCoins.get(id);
    }
    public static String formatItems(ArrayList<String> items) {
        Object[] formatted = items.stream().map(item -> {
            switch(item){
                case "milk": return ":milk: Milk";
                case "tomato": return ":tomato: Tomato";
                default: return "oops... that item wasn't found!";
            }
        }).toArray();
        String asString = "";
        for (Object i : formatted){
            asString = asString + i + '\n';
        }
        return asString;
    }

}

