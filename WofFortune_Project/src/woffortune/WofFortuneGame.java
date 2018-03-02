package woffortune;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

/**
 * WofFortuneGame class Contains all logistics to run the game
 *
 * @author william beard
 */

/* 
 * This is just to ensure it is noticed, but I did the first two bonuses
 * and the second part of the third bonus (printing out prize data and money)
 */
public class WofFortuneGame {

    private boolean puzzleSolved = false; // whether or not puzzle is solved
    private final Wheel wheel; // game wheel
    private String phrase; // phrase players are trying to guess
    private final ArrayList<Letter> user_input_phrase; // list of letters in user input phrase
    private final ArrayList<String> random_phrase; // list of random phrases
    private final ArrayList<Player> player_list = new ArrayList<>(); // list of players
    private final ArrayList<Prize> prize_list; // list of prizes

    /**
     * Constructor
     *
     * @param wheel Wheel
     * @throws InterruptedException
     */
    public WofFortuneGame(Wheel wheel) throws InterruptedException {
        this.prize_list = new ArrayList<>();
        this.random_phrase = new ArrayList<>();
        this.user_input_phrase = new ArrayList<>();
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

            /*
             * lets players take their turns, allows a player to have repeated turns
             * if they repeatedly get the correct answer
             */
            playTurn(player_list.get(i));
            if (player_list.get(i).isCorrectAnswer() == true) {
                // player takes their turn again
                System.out.println("You were correct! Go again!");
                player_list.get(i).setCorrectAnswer(false);
            } else {
                // turn increments to next player
                i++;
            }
        }
    }

    /**
     * Sets up all necessary information to run the game
     */
    private void setUpGame() {
        randomPrize();
        addRandomPhrases();
        playerSetup();

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
                player.setCorrectAnswer(false);
                return; // doesn't get to guess letter

            case BANKRUPT:
                System.out.println("BANKRUPT");
                player.bankrupt();
                player.setCorrectAnswer(false);
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
                    player.setCorrectAnswer(true);
                } else {
                    System.out.println("Congrats! There are " + numFound + " letter " + letter + "'s:");
                    player.setCorrectAnswer(true);
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
            player.setCorrectAnswer(false);
            // Round is over. Write message with final stats
            for (int i = 0; i < player_list.size(); i++) {
                System.out.println(player_list.get(i).getName() + ":");
                System.out.println("You guessed: " + player_list.get(i).getNumGuesses() + " times!");
                System.out.println("You won $" + player_list.get(i).getWinnings() + "!");
                // checks to see if the player won any prizes
                if (player.getPrizes().size() > 0) {
                    System.out.print("Prizes: ");
                    // gets the list of prizes, outputs it as a final statistic
                    ArrayList<Prize> prizes = player.getPrizes();
                    for (int k = 0, last = prizes.size() - 1; k < prizes.size(); k++) {
                        String prizeName = prizes.get(k).getName();
                        if (k < last) {
                            System.out.print(prizeName + ", ");
                        } else {
                            System.out.print(prizeName);
                        }
                    }
                    System.out.println();
                }
                
                // checks to see if the player was the winner, prints to screen
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
        for (Player plyr : player_list) {
            plyr.reset();
            puzzleSolved = false;
        }

        phraseChoice();
        playerChange();
    }
    
    /**
     * Lets player choose a custom phrase, or let the program choose a random one
     */
    public void phraseChoice() {

        //prompts the user to see if they would like to type their own phrase or not, adds the components to Letter ArrayList
        System.out.println("Would you like to use a custom phrase? (Y/N)");
        Scanner yN = new Scanner(System.in);

        //catches scanner exceptions
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
            String tempString = "";

            //catch exceptions for scanner
            try {
                tempString = userInput.nextLine();
            } catch (NoSuchElementException ex) {
                System.out.println("No tokens are available!");
            } catch (IllegalStateException ex) {
                System.out.println("Scanner was closed!");
            }

            phrase = tempString;

            //for each character in the phrase, create a letter and add to user_input_phrase ArrayList
            for (int i = 0; i < phrase.length(); i++) {
                user_input_phrase.add(new Letter(phrase.charAt(i)));
            }

        } else {

            //user didn't choose a custom phrase, picks a random one from random_phrase
            int randIndex = (int) (Math.random() * (random_phrase.size() - 1));
            phrase = random_phrase.get(randIndex);
            //for each character in the phrase, create a letter and add to user_input_phrase ArrayList
            for (int i = 0; i < phrase.length(); i++) {
                user_input_phrase.add(new Letter(phrase.charAt(i)));
            }
        }
    }

    /**
     * Creates "random" phrases that are then stored in the ArrayList
     * random_phrase
     */
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

    /**
     * Creates prizes and stores them in ArrayList prize_list
     */
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
    
    /**
     * Sets up the players for the game, adds them to player_list
     */
    public void playerSetup() {
        System.out.println("How many people are going to play?");
        Scanner sc = new Scanner(System.in);
        
        // catches exceptions for scanner
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

        // asks players for name, adds to player_list
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

    }

    /**
     * Allows a player to be added mid-game
     */
    public void addPlayer() {
        System.out.println("How many people would like to join?");
        Scanner sc = new Scanner(System.in);

        // catches exceptions from scanner
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

            System.out.println("What is your name player " + (player_list.size() + 1));
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
    }

    /**
     * Allows players to be added or dropped
     */
    public void playerChange() {
        System.out.println("Would you like to add/drop any players? (add/drop/no)");
        Scanner sc = new Scanner(System.in);

        // catch exceptions from scanner
        String answer = "";
        try {
            answer = sc.nextLine();
        } catch (NoSuchElementException ex) {
            System.out.println("No tokens are available!");
        } catch (IllegalStateException ex) {
            System.out.println("Scanner was closed!");
        } catch (IndexOutOfBoundsException ex) {
            System.out.println("Index doesn't exist!");
        }

        // checks to see if the player typed add, Add, drop, Drop, or typed nothing
        if ("add".equals(answer) || "Add".equals(answer)) {

            addPlayer();

        } else if ("drop".equals(answer) || "Drop".equals(answer)) {

            System.out.println("Which player would like to drop? (name)");

            // catches exceptions from scanner
            String name = "";
            try {
                name = sc.nextLine();
            } catch (NoSuchElementException ex) {
                System.out.println("No tokens are available!");
            } catch (IllegalStateException ex) {
                System.out.println("Scanner was closed!");
            } catch (IndexOutOfBoundsException ex) {
                System.out.println("Index doesn't exist!");
            }

            /**
             * Creates a temporary ArrayList to store all of the players, but
             * the one specified then clears the player_list, and copies
             * tempPlayerList to player_list essentially removing a player
             */
            ArrayList<Player> tempPlayerList = new ArrayList<>();

            for (Player player : player_list) {

                if (name.equals(player.getName())) {

                } else {
                    tempPlayerList.add(player);
                }
            }
            
            // clears player_list and rewrites tempPlayerList to it
            player_list.clear();
            for (Player player : tempPlayerList) {
                player_list.add(player);
            }
        }
    }
}
