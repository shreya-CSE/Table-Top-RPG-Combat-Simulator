import java.util.Objects;

/**
 * Defines a player-controlled {@code weapon} object
 */
public class Weapon {

    /* Instance Fields */
    private String name_w;
    private String damage;
    private int bonus;

    /* Constructors */

    /**
    * Weapon Constructor
    * @param n weapon name
    */
    public Weapon(String n) {
        name_w = Objects.requireNonNullElse(n, "NoName");
        damage = "None";
        bonus = 0;
    }

    /**
     * Weapon Constructor
     * @param n Weapon name
     * @param m Dice Type
     * @param b weapon bonus
     */
    public Weapon(String n, String m, int b) {
        this(n);
        damage = Objects.requireNonNullElse(m, "None");
        bonus = b;
    }
    /* Mutators and Accessors */
    /**
     * Accessor for name_w
     * @return Weapon name
     */
    public String getName_w() {
            return name_w;
        }
    /**
     * Accessor for damage
     * @return Damage of the weapon
     */
    public String getDamage() {
        return damage;
    }

    /**
     * Accessor for bonus
     * @return bonus of the weapon
     */
    public int getBonus() {
            return bonus;
    }
    /**
     * Mutator for bonus
     * @param bonus Weapon bonus
     */
    public void setBonus(int bonus) {
            this.bonus = bonus;
    }
    /**
     *
     * @return formatted Weapon details
     */
    @Override
    public String toString() {
            return String.format("Name: %s Damage: %s Bonus: %d", name_w, damage, bonus);
    }
}
