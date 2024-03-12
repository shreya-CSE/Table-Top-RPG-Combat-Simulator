import java.util.Date;

public abstract class Creature {
    private String Name = "NoName";
    private int HP = 50;  // from phase 1 data
    private int AC = 15;  // from phase 1 data
    private int STR = 0;
    private int DEX = 0;
    private int CON = 0;

    public Creature() {}
    public Creature(String n) {
        this.Name = n;
    }
    public Creature(String n, int h, int a, int s, int d, int c) {
        this.Name = n;
        this.HP = h;
        AC = a;
        STR = s;
        DEX = d;
        CON = c;
    }

    public String getName() {
        return Name;
    }
    public int getHP() {
        return HP;
    }
    public int getAC() {
        return AC;
    }
    public int getSTR() {
        return STR;
    }
    public int getDEX() {
        return DEX;
    }
    public int getCON() {
        return CON;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }
    public void setAC(int AC) {
        this.AC = AC;
    }
    public void setSTR(int STR) {
        this.STR = STR;
    }
    public void setDEX(int DEX) {
        this.DEX = DEX;
    }
    public void setCON(int CON) {
        this.CON = CON;
    }

    public abstract void attack (Creature creature);

    public void takeDamage (int hp_lower) {
        System.out.println("takeDamage value: " + hp_lower);
        if ((HP > hp_lower) && ((HP - hp_lower) > 0)) {
            HP = HP - hp_lower;
        }
        else {
            HP = 0;
        }
    }
}
