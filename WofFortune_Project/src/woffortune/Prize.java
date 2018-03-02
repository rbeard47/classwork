package woffortune;

/**
 * Used to construct prize objects for player to win
 *
 * @author william beard
 */
public class Prize {

    private final String name; //name of prize

    /**
     *
     * @param name
     */
    public Prize(String name) {
        this.name = name;
    }

    /**
     * Getter
     *
     * @return string name
     */
    public String getName() {
        return name;
    }

}
