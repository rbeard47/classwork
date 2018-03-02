package woffortune;

import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Execution Harness for Wheel of Fortune Game Creates wheel, creates game
 * instance, loops through rounds
 *
 * @author william
 */
public class WofFortune {

    public static void main(String[] args) throws InterruptedException {
        int round = 1;
        boolean keepPlaying = true;
        Wheel wheel = new Wheel();
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to the Wheel of Fortune!");
        WofFortuneGame game = new WofFortuneGame(wheel);

        while (keepPlaying) {
            System.out.println("***********************");
            System.out.println("      ROUND " + round);
            System.out.println("***********************");
            System.out.println();
            game.playGame();

            // game played and ended, see if they want to play another round
            System.out.println();
            System.out.println("Would you like to play again?");

            //catch exceptions for char ans
            char ans = '\0';
            try {
                ans = sc.next().charAt(0);
            } catch (NoSuchElementException ex) {
                System.out.println("No tokens are available!");
            } catch (IllegalStateException ex) {
                System.out.println("Scanner was closed!");
            } catch (IndexOutOfBoundsException ex) {
                System.out.println("Index doesn't exist!");
            }

            if ((ans == 'y') || (ans == 'Y')) {
                // play again
                round += 1;
                game.reset();
            } else {
                // don't play again
                keepPlaying = false;
            }

        }

        System.out.println("Thank-you for playing the Wheel of Fortune!");

    }

} //end of WofFortune.java
