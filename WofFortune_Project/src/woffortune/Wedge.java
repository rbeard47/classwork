package woffortune;

/**
 * This class defines one wedge of a wheel for the wheel of fortune game
 *
 * @author william beard
 */
public class Wedge {

    private final Wheel.WedgeType type; // type of wedge on the wheel {BANKRUPT, MONEY, PRIZE, LOSE A TURN}
    private int amount = 0; // wheel money amount

    /**
     * Constructor
     *
     * @param type Wheel.WedgeType
     */
    public Wedge(Wheel.WedgeType type) {
        this.type = type;
        if (type == Wheel.WedgeType.MONEY) {
            amount = (int) (Math.random() * 20 + 1) * 25;
        }
    }

    /**
     * Getter
     *
     * @return Wheel.WedgeType
     */
    public Wheel.WedgeType getType() {
        return type;
    }

    /**
     * Getter
     *
     * @return int amount (only for wedges of Wheel.WedgeType.MONEY)
     */
    public int getAmount() {
        return amount;
    }

}
