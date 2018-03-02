package woffortune;

import java.util.ArrayList;

/**
 * Class that defines a player for a game with monetary winnings and 
 * a limited number of choices
 * @author william beard
 */
public class Player {
    private int winnings = 0; // amount of money won
    private String name; // player's name
    private int numGuesses = 0; // number of times player has guessed
    private boolean winner = false; // whether or not the player is a winner
    private final ArrayList<Prize> prizes; // player's prizes
    private boolean correctAnswer = false;

    /**
     * Constructor
     * @param name String that is the player's name
     */
    public Player(String name) {
        this.prizes = new ArrayList<>();
        this.name = name;
    }

    /**
     * Getter
     * @return int amount of money won so far 
     */
    public int getWinnings() {
        return winnings;
    }

    /**
     * Adds amount to the player's winnings
     * @param amount int money to add
     */
    public void incrementScore(int amount) {
        if (amount < 0) return;
        this.winnings += amount;
    }

    /**
     * Getter 
     * @return String player's name 
     */
    public String getName() {
        return name;
    }

    public int getNumGuesses() {
        return numGuesses;
    }

    /**
     * Add 1 to the number of guesses used up
     */
    public void incrementNumGuesses() {
        this.numGuesses++;
    }
    
    
    /**
     * Resets for a new game (only number of guesses)
     * This does not reset the winnings.
     */
    public void reset() {
      winnings = 0;
      numGuesses = 0;
      winner = false;
    }
    
    public void bankrupt() {
        this.winnings = 0;
        prizes.clear();
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinnings(int winnings) {
        this.winnings = winnings;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumGuesses(int numGuesses) {
        this.numGuesses = numGuesses;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    public void addPrize(Prize prize) {
        this.prizes.add(prize);
    }

    public ArrayList<Prize> getPrizes() {
        return prizes;
    }

    public boolean isCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(boolean correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
    
    
    
    
    
}
