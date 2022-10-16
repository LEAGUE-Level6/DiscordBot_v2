package org.jointheleague.features.examples.custom_features;

public class GuessingGameTest {
    public static void stats(int games, int guesses, int best) {
        System.out.println();
        System.out.println("Overall results:");
        System.out.println("Total games   = " + games);
        System.out.println("Total guesses = " + guesses);
        System.out.printf( "Guesses/game  = %.1f\n", (double) guesses / games);
        System.out.println("Best game     = " + best);
    }
}
