public class Monster extends Creature{
    private String type;

    public Monster(String n) {
       super(n);
    }

    public Monster(String n, String Type, int h, int a, int s, int d, int c) {
        super(n, h, a, s, d, c);
        this.type = Type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void attack (Creature creature){
        int rollHit = 0;
        int rollDamage = 0;

        rollHit = GameUtilities.rollDice("d20") + GameUtilities.getModifier(getDEX());
        System.out.println("Roll hit: " + rollHit);
        System.out.println("Target's AC value: " + creature.getAC());
        if (rollHit >= creature.getAC()) {
            rollDamage = GameUtilities.rollDice("d6") + GameUtilities.getModifier(this.getSTR());
        }
        else {
            rollDamage = 0;
        }

        if (rollDamage < 0) {
            // if rollDamage is less than 0
            rollDamage = 0;
        }
        creature.takeDamage(rollDamage);

    }

    @Override
    public String toString() {
        return String.format("%s,%s,%d,%d,%d,%d,%d", getName(), getType(), getHP(), getAC(), getSTR(), getDEX(), getCON());
    }
}
