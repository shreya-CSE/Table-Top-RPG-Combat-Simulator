import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class GameUtilities implements Runnable{
    private ArrayList<Creature> creatureList;

    public GameUtilities(ArrayList<Creature> arrayList) {
        creatureList = arrayList;
    }

    public boolean checkCreatureListHP(Creature[] c, int creatureCount) {
        boolean reachedZeroHP = false;
        int count = 0;

        for (var x : c) {
            if (x != null && x.getHP() == 0) {
                count++;
            }
        }
        if (count == creatureCount) {
            reachedZeroHP = true;
        }
        return reachedZeroHP;
    }

    public static int gameEnds(ArrayList<Creature> Creatures) {
        int PlayerWon = 0;

        ArrayList<Player> playersList = new ArrayList<>();
        ArrayList<Monster> monstersList = new ArrayList<>();

        for (var x : Creatures) {
            if (x instanceof Player) {
                playersList.add((Player) x);
            }
            else if (x instanceof Monster) {
                monstersList.add((Monster) x);
            }
        }

        int[] zeroHpPlayers = new int[playersList.size()];
        int j = 0;
        for (int i = 0; i < playersList.size(); i++) {
            if (playersList.get(i).getHP() == 0){
                zeroHpPlayers[j] = 0;
                j++;
            }
        }
        if (zeroHpPlayers.length == playersList.size()) {
            PlayerWon = 2;  // players lost
        }
        int[] zeroHpMonsters = new int[monstersList.size()];
        j = 0;
        for (int k = 0; k < monstersList.size(); k++) {
            if (monstersList.get(k).getHP() == 0){
                zeroHpMonsters[j] = 0;
                j++;
            }
        }
        if (zeroHpMonsters.length == monstersList.size()) {
            PlayerWon = 1;  // Players won
        }
        return PlayerWon;
    }

    public static String[][] csv_read(String file_address, int choice) {
        File file = new File(file_address);

        int r = 0;
        int c = 0;
        if (choice == 1) {
            r = 5;
            c = 3;
        }
        else if (choice == 2) {
            r = 6;
            c = 7;
        }
        String[][] arr = new String[r][c];

        int k = 0;
        if (file.exists()) {
            //System.out.println("in if statement");
            try {
                Scanner input = new Scanner(file);
                while (input.hasNextLine()) {
                    String line_phrase = input.nextLine();
                    //System.out.println(line_phrase);
                    String[] parts = line_phrase.split(",");
                    for (int i = 0; i < parts.length; i++) {
                        arr[k][i] = parts[i];
                    }
                    k++;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("File not found");
        }
        return arr;
    }

    public static Monster[] createMonster() {
        String[][] monster_arr = new String[6][7];
        Monster monster = null;
        int choice = 2;
        String file_Address = "src\\monsters.csv";
        monster_arr = csv_read(file_Address, choice);
        Monster[] monstersArray = new Monster[monster_arr.length];
        Monster m;
        for (int i = 0; i < monster_arr.length; i++) {
            m = new Monster(monster_arr[i][0], monster_arr[i][1], Integer.parseInt(monster_arr[i][2]), Integer.parseInt(monster_arr[i][3]), Integer.parseInt(monster_arr[i][4]), Integer.parseInt(monster_arr[i][5]), Integer.parseInt(monster_arr[i][6]));
            monstersArray[i] = m;
        }
        return monstersArray;
    }


    public static Monster[] createMonstersFromStringArray(String[] monsterData) {

        Monster m=null;
        Monster[] monstersArray = new Monster[monsterData.length];
        for (int i = 0; i < monsterData.length; i++) {
            String[] values = monsterData[i].split(",");
            m = new Monster(values[0], values[1], Integer.parseInt(values[2]), Integer.parseInt(values[3]), Integer.parseInt(values[4]), Integer.parseInt(values[5]), Integer.parseInt(values[6]));
            monstersArray[i] = m;
        }
        return monstersArray;
    }

    public static Monster createMonsterObject(String monsterInfo) {
        Monster m;
        String[] parts = monsterInfo.split(",");
        m = new Monster(parts[0], parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), Integer.parseInt(parts[4]), Integer.parseInt(parts[5]), Integer.parseInt(parts[6]));
        return m;
    }

    public static ArrayList<Creature> creatureList(Monster[] monsterArray, Player[] playerArray) {
        System.out.println("In Game Utilities monsterArray");
        for (var x : monsterArray) {
            System.out.println(x.toString());
        }

        Creature[] creaturesArray = new Creature[GUIController.getNumMonstersInGame() + GUIController.getNoPlayersInParty()];
        System.arraycopy(monsterArray, 0, creaturesArray, 0, monsterArray.length);
        System.arraycopy(playerArray, 0, creaturesArray, monsterArray.length, playerArray.length);
        ArrayList<Creature> creatureArrayList = new ArrayList<Creature>();
        for (int i = 0; i < creaturesArray.length; i++){
            creatureArrayList.add(creaturesArray[i]);
        }
        System.out.println("In Game Utilities");
        for (var x : creatureArrayList) {
            System.out.println(x.toString());
        }
        return creatureArrayList;
    }

    public static Weapon[] createWeapon() {

        String[][] weapon_arr = new String[5][3];
        Weapon weapon = null;
        int choice = 1;

        String file_address = "src\\weapons.csv";
        weapon_arr = csv_read(file_address, choice);
        Weapon[] weaponsCB = new Weapon[5];
        Weapon w;
        for (int i = 0; i < weapon_arr.length; i++) {
            w = new Weapon(weapon_arr[i][0], weapon_arr[i][1], Integer.parseInt(weapon_arr[i][2]));
            weaponsCB[i] = w;
        }
        return weaponsCB;
    }

    public static int[] randomStats() {
        int[] stats = new int[3];
        int remaining = 15;

        Random rn = new Random();
        stats[0] = rn.nextInt(remaining);
        remaining = remaining - stats[0];
        stats[1] = rn.nextInt(remaining);
        remaining = remaining - stats[1];
        stats[2] = rn.nextInt(remaining);

        return stats;
    }

    public static int getModifier(int n) {
        int modifier=0;

        if (n > 5) {
            for (int i = 6; i <= n; i++) {
                modifier++;
            }
        } else if (n < 5) {
            for (int j = n; j < 5; j++) {
                modifier--;
            }
        } else {      // for str = 5
            modifier = 0;
        }

        return modifier;
    }

    public static int rollDice(String input) {

        String part1 = "";
        String part2 = "";
        int numdice = 0;
        int dicetype = 0;
        int total = 0;
        if (input.contains("d")) {
            String[] parts = input.split("d");
            String i = "";

            if (parts[0].equals(i)) {
                // if the input does not have [NUMDICE]
                part1 = "1";
            }
            else {
                if(!Character.isDigit(parts[0].charAt(0))) {
                    // if the user gives an invalid [NUMDICE] (character)
                    System.out.println("Invalid input");
                    return 0;
                }
                else {
                    part1 = parts[0];
                }
            }
            part2 = parts[1];

            numdice = Integer.parseInt(part1);
            dicetype = Integer.parseInt(part2);

            Random rn = new Random();
            int sum = 0;
            int rnum = 0;

            for (int j = 0; j < numdice; j++) {
                rnum = rn.nextInt(dicetype) + 1;
                sum = sum +rnum;
            }
            total = sum;
        }
        else {
            System.out.println("Invalid Input");;
        }
        return total;
    }

    public static Creature rollInitiative(ArrayList<Creature> arrayList) {
        Creature creatureToPlay = null;
        int l = arrayList.size();
        Integer[] rolls = new Integer[arrayList.size()];
        Integer[] rollsCopy = new Integer[rolls.length];
        int sortedIndex = 0;

        System.out.println("In roll initiative\n");
        for (var x : arrayList) {
            System.out.println(x.toString());
        }
        for (int i = 0; i < l; i++) {
            rolls[i] = rollDice("d20") + getModifier(arrayList.get(i).getDEX());
        }
        System.out.println("Roll values (d20+DEX modifier)" + Arrays.stream(rolls).toList().toString());
        String statusString="Roll values (d20+DEX modifier)" + Arrays.stream(rolls).toList().toString();
        GUIController.updateStatusBar(statusString);

        for (int j = 0; j < l; j++) {
            rollsCopy[j] = rolls[j];
        }
        Arrays.sort(rollsCopy, Collections.reverseOrder());

        sortedIndex = Arrays.asList(rolls).indexOf(rollsCopy[0]);
        creatureToPlay = arrayList.get(sortedIndex);

        Attack.setAttCre(creatureToPlay);
        return creatureToPlay;
    }

    public static int Combat_Disarming(Creature p_1, Creature p_2) {
        int p_1_roll = 0;     // attacker
        int p_2_roll = 0;     // target
        int disarm;
        p_1_roll = GameUtilities.rollDice("d20") + GameUtilities.getModifier(p_1.getSTR());
        p_2_roll = GameUtilities.rollDice("d20") + GameUtilities.getModifier(p_2.getSTR());

        if (p_1_roll > p_2_roll) {
            // target is disarmed for 2 rounds of combat
            System.out.println("Disarm successful");
            disarm = 1;
        }
        else {
            System.out.println("Disarm NOT successful");
            disarm = 0;
        }
        return disarm;
    }

    public static int testFunction(){
        System.out.println("Called by multi thread");
        Attack.setAttackInt(100);
        return 1;
    }

    @Override
    public void run() {
       // creatureList
       rollInitiative(creatureList);
    }
}
