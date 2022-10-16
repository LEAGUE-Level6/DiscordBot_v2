package org.jointheleague.features.examples.custom_features;

import java.util.Random;
import java.util.Scanner;

public class GuessingGame {
    public static int MAX = 100;

    public static void main(String[] args) {
        intro();
        Scanner console = new Scanner(System.in);
        Random rand = new Random();

        int games = 0;
        int totalGuesses = 0;
        int best = 1000000;
        do {
            int guesses = game(console, rand);
            totalGuesses += guesses;
            games++;
            best = Math.min(best, guesses);

            System.out.print("Do you want to play again? ");
        } while (console.next().toLowerCase().startsWith("y"));

        stats(games, totalGuesses, best);
    }

    public static void intro() {
        System.out.println("This program allows you to guess random numbers.");
        System.out.println("I will think of a number between 1 and " + MAX);
        System.out.println("and you will try to guess it.");
        System.out.println("After each guess, I will give you a clue about");
        System.out.println("whether the correct number is higher or lower.");
    }
    public static int game(Scanner console, Random rand) {
        int correctAnswer = rand.nextInt(MAX) + 1;
        int guesses = 1;

        System.out.println();
        System.out.println("I'm thinking of a number between 1 and " + MAX + "...");
        System.out.print("Your guess? ");
        int guess = console.nextInt();

        while (guess != correctAnswer) {
            if (guess < correctAnswer) {
                System.out.println("It's higher.");
            } else {
                System.out.println("It's lower.");
            }

            System.out.print("Your guess? ");
            guess = console.nextInt();
            guesses++;
        }

        if (guesses == 1) {
            System.out.println("You got it right in 1 guess!");
        } else {
            System.out.println("You got it right in " + guesses + " guesses!");
        }
        return guesses;
    }

}
