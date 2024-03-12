public class Player extends Creature{

    private Weapon weapon;
    private String avatar;
    private String filename;

    public Player(String name) {
        super(name);
    }

    public Player(String name, int h, int a,  int s, int d, int c, Weapon w, String avatarName) {
        super(name, h, a, s, d, c);
        this.weapon = w;
        this.avatar = avatarName;
    }


    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatarName) {
        this.avatar = avatarName;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }


    public void updatePlayer(int s, int d, int c, Weapon w, String avatarName){
        setSTR(s);
        setDEX(d);
        setCON(c);
        this.weapon = w;
        this.avatar = avatarName;
    }

    public void attack (Creature creature){
        int rollDamage = 0;
        int rollHit_value = 0;
        int rollHit = GameUtilities.rollDice("d20") + GameUtilities.getModifier(this.getDEX()) + this.weapon.getBonus();
        rollHit_value = rollHit;
        System.out.printf(" with %s (%d to hit)\n", this.weapon.getName_w(), rollHit_value);
        System.out.println("Target's AC value: " + creature.getAC());
        if (rollHit_value >= creature.getAC()) {
            rollDamage = GameUtilities.rollDice(this.weapon.getDamage()) + GameUtilities.getModifier(this.getSTR());
            System.out.printf("...HITS!\n");
            if (rollDamage < 0) {
                rollDamage = 0;
            }
            creature.takeDamage(rollDamage);
            System.out.printf("%s takes %d points of damage.\n", creature.getName(), rollDamage);
        }
        else {
            rollDamage = 0;
            System.out.printf("...MISSES!\n");
        }
    }

    public String getToolTipString() {
        return "<html>" + getName() + "<br>" +"HP = " + getHP() + "<br>" + "AC = " + getAC() + "<br>" +"STR = " + getSTR() + "<br>" +"DEX = " + getDEX() + "<br>" +"CON = " + getCON() + "</html>" ;

    }

    @Override
    public String toString() {
        return String.format("Name: %s HP: %d AC: %d STR: %d DEX: %d CON: %d Weapon: %s Avatar: %s", getName(), getHP(), getAC(), getSTR(), getDEX(), getCON(), getWeapon().toString(), getAvatar());
    }

}
