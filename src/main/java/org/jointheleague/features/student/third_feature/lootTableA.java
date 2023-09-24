package org.jointheleague.features.student.third_feature;

public enum lootTableA {

    Sardine(32.5, 3),SeaBass(9, 7),Tuna(15.5, 6.5),Squid(3.5, 30),Striper(6.25, 12.5),Lobster(1.75, 35);
    double value;
    double weightOrChanceOfGettingFish;
    lootTableA(double weightOrChanceOfGettingFish, double value) {
    this.weightOrChanceOfGettingFish=weightOrChanceOfGettingFish;
    this.value = value;
    }

    public double getValue() {
        return value;
    }

    public double getChance() {
        return weightOrChanceOfGettingFish;
    }
}
