package org.jointheleague.features.sameer_bot.second;

public class MoneyData {
    private int coins = 5;

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public int getCoins() {
        return coins;
    }

    public void increaseCoins(int amount) {
        this.coins += amount;
    }
}
