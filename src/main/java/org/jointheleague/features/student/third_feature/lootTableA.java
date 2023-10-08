package org.jointheleague.features.student.third_feature;

public enum lootTableA {

    Minnow(35.7525,4),Sardine(32.5, 7),SeaBass(9, 16),Tuna(15.5, 12.5),ReefShark(12.5, 13.5),Squid(3.5, 75),TriggerFish(5,45),ElectricEel(2.25,115),Striper(6.25, 25),Lobster(1.75, 145),Swordfish(0.975,160),Sunfish(0.675,235),GoldMinnowStatue(0.00565,127500);
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
