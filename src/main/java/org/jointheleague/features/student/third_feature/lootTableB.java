package org.jointheleague.features.student.third_feature;

public enum lootTableB {
    Angler(15.125,40),;
    double value;
    double weightOrChanceOfGettingFish;
    lootTableB(double weightOrChanceOfGettingFish, double value) {
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
