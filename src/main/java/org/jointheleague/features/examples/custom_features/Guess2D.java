package org.jointheleague.features.examples.custom_features;

import java.awt.*;
import java.util.Random;
import java.util.Scanner;

public class Guess2D {
    public static final int MAX = 100;
    public static void main(String[] args) {
        introduction();
        Scanner console = new Scanner(System.in);
        Random rand = new Random();
        int games = 0;
        int totalGuesses = 0;

        do {
            totalGuesses += playGame(console, rand);
            games++;
            System.out.print("Play again? ");
        } while (console.next().toLowerCase().startsWith("y"));

        reportStats(games, totalGuesses);
    }
    public static int playGame(Scanner console, Random rand) {
        System.out.println();
        Point correct = new Point(rand.nextInt(MAX) + 1, rand.nextInt(MAX) + 1);

        int guesses = 1;
        Point guess = getGuess(console);

        while (!guess.equals(correct)) {
            hint(guess, correct);
            guess = getGuess(console);
            guesses++;
        }

        System.out.println("You got it right in " + guesses + " guesses!");
        return guesses;
    }

    public static Point getGuess(Scanner console) {
        System.out.print("Guess x and y: ");
        return new Point(console.nextInt(), console.nextInt());
    }

    public static void hint(Point guess, Point correct) {
        double distance = guess.distance(correct);
        if (distance <= 1.5) {
            System.out.print("You're hot! Go ");
        } else if (distance <= 5.0) {
            System.out.print("You're warm. Go ");
        } else {
            System.out.print("You're cold. Go ");
        }

        if (guess.y < correct.y) {
            System.out.print("north ");
        } else if (guess.y > correct.y) {
            System.out.print("south ");
        }
        if (guess.x < correct.x) {
            System.out.print("east ");
        } else if (guess.x > correct.x) {
            System.out.print("west ");
        }
        System.out.println();
    }

    public static void introduction() {
        System.out.println("This program is a 2-D guessing game.");
        System.out.println("I will think of a point somewhere");
        System.out.println("between (1, 1) and (" + MAX + ", " + MAX + ")");
        System.out.println("and give hints until you guess it.");
    }

    public static void reportStats(int games, int guesses) {
        System.out.println();
        System.out.println("Overall results:");
        System.out.println("Games played  = " + games);
        System.out.println("Total guesses = " + guesses);
        System.out.printf("Guesses/game  = %.1f\n", ((double) guesses / games));
    }
}