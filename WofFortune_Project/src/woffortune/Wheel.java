package woffortune;

import java.util.ArrayList;

/**
 * This class defines a Wheel for the Wheel of Fortune game
 *
 * @author william beard
 */ 
public class Wheel {

    public enum WedgeType {MONEY, BANKRUPT, LOSE_TURN, PRIZE} // enumerated type, wheel wedges can be any of these
    private WedgeType spinType; // the type for the current sping
// the type for the current sping
    private int spinDollarAmount;
    // list of wedges
    // if a money wedge, the amount
    private final ArrayList<Wedge> wedges;  

    /**
     * Constructor Creates the wheel
     */
    public Wheel() {
        this.wedges = new ArrayList<>();

    //put a bankrupt wedge on the wheel
    wedges.add(new Wedge(WedgeType.BANKRUPT));

    // put a lose-turn wedge on the wheel
    wedges.add(new Wedge(WedgeType.LOSE_TURN));

    // put 20 money wedges on the wheel
    for (int i = 1; i < 20; i++) {
    wedges.add(new Wedge(WedgeType.MONEY));
    }
    
    //put a prize wedge on the wheel
    wedges.add(new Wedge(WedgeType.PRIZE));
    }

    /**
     * Spins the wheel
     *
     * @return WedgeType
     */
    public WedgeType spin() {
        int index = (int) (Math.random() * wedges.size());
        spinType = wedges.get(index).getType();
        spinDollarAmount = wedges.get(index).getAmount();
        return spinType;
    }

    /**
     * Getter For when the spin lands on a money wedge
     *
     * @return int the amount of money on the wedge
     */
    public int getAmount() {
        return spinDollarAmount;
    }

} //end of Wheel.java
