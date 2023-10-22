/*package org.jointheleague.features.student.third_feature;

public enum lootTableB {
    //Telescopefish(25.65,35),Rhinochimaera(20.2575,40),Angler(15.125,55),Barreleye(12.25751,60),Lanternfish(11.524575,70),GlowingMinnow(10.2575,75),FlyingMinnow(8.7525,90),AbyssalGrenadier(5.245,125),MarineHatchetfish(5.65,120),GoblinShark(2.5,165),Jellyfish(1.15, 225),Snailfish(0.000125,35000000);
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
*/
package org.jointheleague.features.student.third_feature;

import org.javacord.api.entity.message.MessageDecoration;

public class lootTableB {
    double weight;
    double multiplier;
    String name;
    String rankingEmoji;

    lootTableB(double weight, double multiplier, String name, String rankingEmoji){
        this.weight = weight;
        this.multiplier = multiplier;
        this.name = name;
        this.rankingEmoji = rankingEmoji;
    }

    public double getMultiplier() {
        return multiplier;
    }

    public double getWeight() {
        return weight;
    }

    public String name(){
        return name;
    }

    public String getRankingEmoji() {
        return rankingEmoji;
    }
}