/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package woffortune;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

/**
 * WofFortuneGame class Contains all logistics to run the game
 *
 * @author clatulip
 */
public class WofFortuneGame {

    private boolean puzzleSolved = false;
    private Wheel wheel;
    private String phrase;
    private ArrayList<Letter> user_input_phrase = new ArrayList<>();
    private ArrayList<String> random_phrase = new ArrayList<>();
    private final ArrayList<Player> player_list = new ArrayList<>();
    private ArrayList<Prize> prize_list = new ArrayList<>();

    /**
     * Constructor
     *
     * @param wheel Wheel
     * @throws InterruptedException
     */
    public WofFortuneGame(Wheel wheel) throws InterruptedException {
        // get the wheel
        this.wheel = wheel;

        // do all the initialization for the game
        setUpGame();

    }

    /**
     * Plays the game
     *
     * @throws InterruptedException
     */
    public void playGame() throws InterruptedException {
        int i = 0;

        while (!puzzleSolved) {
            if (i == player_list.size()) {
                i = 0;
            }
            playTurn(player_list.get(i));

            i++;

        }
    }

    /**
     * Sets up all necessary information to run the game
     */
    private void setUpGame() {
        randomPrize();
        addRandomPhrases();
        System.out.println("How many people are going to play?");
        Scanner sc = new Scanner(System.in);

        int players = 0;
        try {
            players = sc.nextInt();
        } catch (InputMismatchException ex) {
            System.out.println("Token not an integer, or is out of range!");
        } catch (NoSuchElementException ex) {
            System.out.println("Input is exhausted!");
        } catch (IllegalStateException ex) {
            System.out.println("Scanner was closed!");
        }

        for (int i = 0; i < players; i++) {

            System.out.println("What is your name player " + (i + 1));
            Scanner nameScanner = new Scanner(System.in);

            String name = "";
            try {
                name = nameScanner.nextLine();
            } catch (NoSuchElementException ex) {
                System.out.println("No line found!");
            } catch (IllegalStateException ex) {
                System.out.println("Scanner was closed!");
            }

            Player p = new Player(name);
            player_list.add(p);
            System.out.println("Hello, " + name + "! Welcome to the Wheel of Fortune!");
        }

        // print out the rules
        System.out.println("RULES!");
        System.out.println("Each player gets to spin the wheel, to get a number value");
        System.out.println("Each player then gets to guess a letter. If that letter is in the phrase, ");
        System.out.println(" the player will get the amount from the wheel for each occurence of the letter");
        System.out.println("If you have found a letter, you will also get a chance to guess at the phrase");
        System.out.println("Each player only has three guesses, once you have used up your three guesses, ");
        System.out.println("you can still guess letters, but no longer solve the puzzle.");
        System.out.println();

        phraseChoice();

        /* for each character in the phrase, create a letter and add to letters array
         * this is part of the original code, left it just for reference
         * for (int i = 0; i < phrase.length(); i++) {G
         * user_input_phrase[i] = new Letter(phrase.charAt(i));
        }
         */
        //for each character in the phrase, create a letter and add to user_input_phrase ArrayList
        for (int i = 0; i < phrase.length(); i++) {
            user_input_phrase.add(new Letter(phrase.charAt(i)));
        }
        // setup done
    }

    /**
     * One player's turn in the game Spin wheel, pick a letter, choose to solve
     * puzzle if letter found
     *
     * @param player
     * @throws InterruptedException
     */
    private void playTurn(Player player) throws InterruptedException {
        int money = 0;

        Scanner sc = new Scanner(System.in);
        System.out.println(player.getName() + ", you have $" + player.getWinnings());
        System.out.println("Spin the wheel! <press enter>");

        try {
            sc.nextLine();
        } catch (NoSuchElementException ex) {
            System.out.println("No line was found!");
        } catch (IllegalStateException ex) {
            System.out.println("Scanner was closed!");
        }

        System.out.println("<SPINNING>");

        try {
            Thread.sleep(200);
        } catch (InterruptedException ex) {
            System.out.println("Thread interrupted!");
        }

        Wheel.WedgeType type = wheel.spin();
        System.out.print("The wheel landed on: ");

        switch (type) {
            case MONEY:
                money = wheel.getAmount();
                System.out.println("$" + money);
                break;

            case LOSE_TURN:
                System.out.println("LOSE A TURN");
                System.out.println("So sorry, you lose a turn.");
                return; // doesn't get to guess letter

            case BANKRUPT:
                System.out.println("BANKRUPT");
                player.bankrupt();
                return; // doesn't get to guess letter

            case PRIZE:
                System.out.print("Prize!");
                break;

            default:

        }
        System.out.println("");
        System.out.println("Here is the puzzle:");
        showPuzzle();
        System.out.println();
        System.out.println(player.getName() + ", please guess a letter.");
        //String guess = sc.next();

        char letter = '\0';
        try {
            letter = sc.next().charAt(0);
        } catch (NoSuchElementException ex) {
            System.out.println("No tokens are available!");
        } catch (IllegalStateException ex) {
            System.out.println("Scanner was closed!");
        } catch (IndexOutOfBoundsException ex) {
            System.out.println("Index doesn't exist!");
        }

        if (!Character.isAlphabetic(letter)) {
            System.out.println("Sorry, but only alphabetic characters are allowed. You lose your turn.");
        } else {
            // search for letter to see if it is in
            int numFound = 0;
            for (Letter l : user_input_phrase) {
                if ((l.getLetter() == letter) || (l.getLetter() == Character.toUpperCase(letter))
                        || (l.getLetter() == Character.toLowerCase(letter))) {
                    l.setFound();
                    numFound += 1;
                }
            }
            if (numFound == 0) {
                System.out.println("Sorry, but there are no " + letter + "'s.");
            } else {
                if (numFound == 1) {
                    System.out.println("Congrats! There is 1 letter " + letter + ":");
                } else {
                    System.out.println("Congrats! There are " + numFound + " letter " + letter + "'s:");
                }
                System.out.println();
                showPuzzle();
                System.out.println();

                if (type == Wheel.WedgeType.MONEY) {
                    System.out.println("You earned $" + (numFound * money) + ", and you now have: $" + player.getWinnings());
                    player.incrementScore(numFound * money);
                } else if (type == Wheel.WedgeType.PRIZE) {
                    int randomPrize = (int) (Math.random() * (prize_list.size() - 1));
                    System.out.println("Congratulations! You won a " + prize_list.get(randomPrize).getName());
                    player.addPrize(prize_list.get(randomPrize));
                }

                System.out.println("Would you like to try to solve the puzzle? (Y/N)");
                letter = sc.next().charAt(0);
                System.out.println();
                if ((letter == 'Y') || (letter == 'y')) {
                    solvePuzzleAttempt(player);
                }
            }

        }

    }

    /**
     * Logic for when user tries to solve the puzzle
     *
     * @param player
     */
    private void solvePuzzleAttempt(Player player) {

        if (player.getNumGuesses() >= 3) {
            System.out.println("Sorry, but you have used up all your guesses.");
            return;
        }

        player.incrementNumGuesses();
        System.out.println("What is your solution?");
        Scanner sc = new Scanner(System.in);
        sc.useDelimiter("\n");
        String guess = sc.next();
        if (guess.compareToIgnoreCase(phrase) == 0) {
            System.out.println("Congratulations! You guessed it!");
            puzzleSolved = true;
            player.setWinner(true);
            // Round is over. Write message with final stats
            for (int i = 0; i < player_list.size(); i++) {
                System.out.println(player_list.get(i).getName() + ":");
                System.out.println("You guessed: " + player_list.get(i).getNumGuesses() + " times!");
                System.out.println("You won $" + player_list.get(i).getWinnings() + "!");
                if(player.getPrizes().size() > 0) {
                System.out.print("Prizes: ");
                ArrayList<Prize> prizes = player.getPrizes(); 
                for(int k = 0, last = prizes.size() - 1; k < prizes.size(); k++) {
                    String prizeName = prizes.get(k).getName();
                    if(k < last) {
                        System.out.print(prizeName + ", ");
                    } else {
                       System.out.print(prizeName); 
                    }
                }
                System.out.println();
                }

                if (player_list.get(i).isWinner() == true) {
                    System.out.println(player_list.get(i).getName() + " is the winner!");
                } else {
                    System.out.println(player_list.get(i).getName() + " is a loser!");
                }
                System.out.println("---------------------------------------");
            }
        } else {
            System.out.println("Sorry, but that is not correct.");
        }
    }

    /**
     * Display the puzzle on the console
     */
    private void showPuzzle() {
        System.out.print("\t\t");
        for (Letter l : user_input_phrase) {
            if (l.isSpace()) {
                System.out.print("   ");
            } else {
                if (l.isFound()) {
                    System.out.print(Character.toUpperCase(l.getLetter()) + " ");
                } else {
                    System.out.print(" _ ");
                }
            }
        }
        System.out.println();

    }

    /**
     * For a new game reset player's number of guesses to 0
     */
    public void reset() {
        user_input_phrase.clear();
        for (Player player : player_list) {
            player.reset();
            puzzleSolved = false;
            phraseChoice();

        }
    }

    public void phraseChoice() {
        //prompts the user to see if they would like to type their own phrase or not, adds the components to Letter ArrayList
        System.out.println("Would you like to use a custom phrase? (Y/N)");
        Scanner yN = new Scanner(System.in);

        char promptAnswer = '\0';
        try {
            promptAnswer = yN.next().charAt(0);
        } catch (NoSuchElementException ex) {
            System.out.println("No tokens are available!");
        } catch (IllegalStateException ex) {
            System.out.println("Scanner was closed!");
        } catch (IndexOutOfBoundsException ex) {
            System.out.println("Index doesn't exist!");
        }

        System.out.println();
        if ((promptAnswer == 'Y') || (promptAnswer == 'y')) {
            System.out.println("Please input your custom phrase!");
            Scanner userInput = new Scanner(System.in);
            String tempString = userInput.nextLine();
            phrase = tempString;
        } else {
            int randIndex = (int) (Math.random() * (random_phrase.size() - 1));
            phrase = random_phrase.get(randIndex);
        }
    }

    public void addRandomPhrases() {
        random_phrase.add("Java is easy!");
        random_phrase.add("Java is hard!");
        random_phrase.add("Java takes time to learn.");
        random_phrase.add("Java is cooler than lua!");
        random_phrase.add("Programming is fufilling!");
        random_phrase.add("Problem solving is fun!");
        random_phrase.add("Get to know your professors!");
        random_phrase.add("Get to know your TA's!");
        random_phrase.add("Make sure you attend your classes!");
        random_phrase.add("Keep up with all your assignments!");
    }

    public void randomPrize() {

        Prize apple = new Prize("Apple");
        Prize orange = new Prize("Orange");
        Prize pear = new Prize("Pear");
        Prize grapefruit = new Prize("Grapefruit");

        prize_list.add(apple);
        prize_list.add(orange);
        prize_list.add(pear);
        prize_list.add(grapefruit);
    }

}
