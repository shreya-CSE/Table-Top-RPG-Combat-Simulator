import java.util.*;
import java.util.stream.IntStream;

/**
 * Tracks the position of the players
 */
public class Map {

    final static int max = 10;
    final static int min = 0;

    /* Instance Fields */
    private static int[] x_coordinates;
    private static int[] y_coordinates;
    private static Creature[] creature_arr;
    private static int num_of_creatures = 0;

    /* Constructors */
    /**
     * Map Constructor
     */
    public Map() {
        creature_arr = new Creature[30];
        x_coordinates = new int[25];
        y_coordinates = new int[25];
    }

    /*Accessors and Mutators */

    /**
     * Accessor method for x_coordinates
     * @return array of x coordinates
     */
    public int[] getX_coordinates() {
        return x_coordinates;
    }

    /**
     * Accessor method for y_coordinates
     * @return array of y coordinates
     */
    public int[] getY_coordinates() {
        return y_coordinates;
    }

    /**
     * Accessor method for creature_arr
     * @return Array of creatures
     */
    public Creature[] getCreature_arr() {
        return creature_arr.clone();
    }

    /**
     * Accessor method for num_of_creatures
     * @return number of creatures
     */
    public int getNum_of_creatures() {
        return num_of_creatures;
    }

    /* methods */

    /**
     * This method adds a new player to the map
     * @param p Player to be added to the map
     */
    public void add_creature(Creature p) {
        int collision_status = 1;
        while (collision_status == 1) {
            collision_status = 0;
            creature_arr[num_of_creatures] = p;

            Random rn = new Random();
            int x_cord = rn.nextInt(9) + 1;
            int y_cord = rn.nextInt(9) + 1;

            collision_status = check_collision(creature_arr[num_of_creatures], x_cord, y_cord);
            if(collision_status == 0){
                x_coordinates[num_of_creatures] = x_cord;
                y_coordinates[num_of_creatures] = y_cord;
            }

        }
        num_of_creatures++;
    }

    /**
     * Changes position of the Player on the map based on user input
     * @param up Number of units upwards from the existing position
     * @param down Number of units downwards from the existing position
     * @param left Number of units to the left from the existing position
     * @param right Number of units to the right from the existing position
     * @param p Player whose position has to be updated
     */
    public static void creatureOnMap(int up, int down, int left, int right, Creature p) {
        int x = 0;
        int y = 0;
        int i = 0;
        int index = 0;
        // traversing through the array
        while (i < creature_arr.length) {
            if (creature_arr[i] == p) {
                index = i;
            }
            i = i + 1;
        }

        x = x_coordinates[index] - left + right;
        y = y_coordinates[index] - down + up;

        if (x < max && x >= min && y < max && y >= min) {
            int collision_status = check_collision(creature_arr[index], x, y);
            if(collision_status == 0){
                x_coordinates[index] = x;
                y_coordinates[index] = y;
            } else {
                System.out.println("Position collision occurred");
            }

        }
        else {
            System.out.println("X and/or Y position(s) is out of bounds");
        }

    }

    public static void changeCreatureLocationOnMap(Creature p, int x, int y) {
        int i = 0;
        int index = 0;

        // traversing through the array
        while (i < num_of_creatures) {
            if (creature_arr[i] == p) {
                index = i;
            }
            i = i + 1;
        }

        if (x < max && x >= min && y < max && y >= min) {
            int collision_status = check_collision(creature_arr[index], x, y);
            if(collision_status == 0){
                x_coordinates[index] = x;
                y_coordinates[index] = y;
            } else {
                System.out.println("Position collision occurred");
            }

        }
        else {
            System.out.println("X and/or Y position(s) is out of bounds");
        }

    }

    public void checkHPandRemovePlayersFromMap(){
        int i;
        Creature[] temp_cre_arr = creature_arr;
        int temp_cre_size = num_of_creatures;

        for (i = 0; i < temp_cre_size; i++) {
            if(temp_cre_arr[i] != null) {
                if(temp_cre_arr[i].getHP() ==0){
                    removeCreatureOnMap(temp_cre_arr[i]);
                }
            }
        }
    }

    public void removeCreatureOnMap(Creature targetCreature){

        if(num_of_creatures == 0){
            return;
        }
        Creature[] temp_cre_arr = new Creature[num_of_creatures-1];
        int[] temp_x = new int[num_of_creatures-1];
        int[] temp_y = new int[num_of_creatures-1];
        int i, k;

        for (i = 0, k=0; i < num_of_creatures; i++) {
            if(creature_arr[i] != null) {
                if(creature_arr[i].getName().equals(targetCreature.getName())){
                    continue;
                }
                temp_cre_arr[k] = creature_arr[i];
                temp_x[k] = x_coordinates[i];
                temp_y[k] = y_coordinates[i];
                k++;
            }
        }
        creature_arr = temp_cre_arr;
        x_coordinates = temp_x;
        y_coordinates = temp_y;
        num_of_creatures = k;
        return;
    }

    public void replaceCreatureOnMap(String sourcePlayerName, Creature targetCreature){

        for (int i = 0; i < num_of_creatures; i++) {
            if(creature_arr[i] != null && creature_arr[i] instanceof Player) {
                if(creature_arr[i].getName().equals(sourcePlayerName)){
                    creature_arr[i] = targetCreature;
                }
            }
        }
    }
    /**
     * Checks collision between any two players on the map
     * @param p Player whose position has to checked for collision with other players
     * @param x_cord x coordinate of the Player
     * @param y_cord Y coordinate of the Player
     * @return determines if the player collides with other players
     */
    public static int check_collision(Creature p, int x_cord, int y_cord) {
        int i = 0;

        for(i=0; i<num_of_creatures; i++){
            if((x_coordinates[i] == x_cord) && (y_coordinates[i] == y_cord) ){
                System.out.println("Collision occurred");
                return 1;
            }
        }
        return 0;
    }

    public Creature Creature(String coordinates) {
        int[] xyCoord = new int[2];
        boolean creatureFound = false;
        Creature creatureAtCoordinate = null;

        String[] coords = coordinates.split(",");
        for (int i = 0; i < coords.length; i++) {
            xyCoord[i] = Integer.parseInt(coords[i]);
        }
        for (int j = 0; j < x_coordinates.length; j++) {
            if((xyCoord[0] == x_coordinates[j]) && (xyCoord[1] == y_coordinates[j])){
               creatureFound = true;
               creatureAtCoordinate = creature_arr[j];
            }
        }
       return creatureAtCoordinate;
    }

    /**
     * Takes user input for the Player's new position
     * @param p1 Player 1
     * @param p2 Player 2
     * @param p1_start Start value for Player p1
     * @param p2_start Start value for Player p2
     */
    public static void map_position(Creature p1, Creature p2, int p1_start, int p2_start) {
        int up = 0;
        int down = 0;
        int right = 0;
        int left = 0;
        int remaining_steps = 5;
        int steps_done = 0;
        int steps_choice = 0;

        while (steps_done != 1) {
            System.out.printf("Up: %d\n", up);
            System.out.printf("Down: %d\n", down);
            System.out.printf("Left: %d\n", left);
            System.out.printf("Right: %d\n", right);
            System.out.printf("Remaining Steps: %d\n", remaining_steps);

            Scanner steps_input = new Scanner(System.in);

            switch (steps_choice) {
                case 1: //up
                    System.out.printf("> ");
                    if (steps_input.hasNextInt()) {
                        int x = 0;
                        int up_temp = up;
                        x = steps_input.nextInt();
                        up = x;
                        remaining_steps = remaining_steps - up + up_temp;
                    } else {
                        System.out.println("Invalid input");
                    }
                    break;
                case 2: //down
                    System.out.printf("> ");
                    if (steps_input.hasNextInt()) {
                        int x = 0;
                        int down_temp = down;
                        x = steps_input.nextInt();
                        down = x;
                        remaining_steps = remaining_steps - down + down_temp;
                    } else {
                        System.out.println("Invalid input");
                    }
                    break;
                case 3: //left
                    System.out.printf("> ");
                    if (steps_input.hasNextInt()) {
                        int x = 0;
                        int left_temp = left;
                        x = steps_input.nextInt();
                        left = x;
                        remaining_steps = remaining_steps - left + left_temp;
                    } else {
                        System.out.println("Invalid input");
                    }
                    break;
                case 4: //right
                    System.out.printf("> ");
                    if (steps_input.hasNextInt()) {
                        int x = 0;
                        int right_temp = right;
                        x = steps_input.nextInt();
                        right = x;
                        remaining_steps = remaining_steps - right + right_temp;
                    } else {
                        System.out.println("Invalid input");
                    }
                    break;
                case 5: //reset
                    up = 0;
                    down = 0;
                    left = 0;
                    right = 0;
                    remaining_steps = 5;
                    break;
                case 6: //finish
                    if((up+down+left+right) != 0 && remaining_steps >= 0 && remaining_steps <= 5 ){
                        if (p1_start > p2_start) {
                            creatureOnMap(up, down, left, right, p1);
                        } else if (p2_start > p1_start) {
                            creatureOnMap(up, down, left, right, p2);
                        }
                        steps_done = 1;
                    } else if((up+down+left+right) == 0){
                        steps_done = 1;
                    }
                    break;
                default:
                    System.out.println("Invalid input");
                    break;
            }
        }
    }


    /**
     * Finds the x and y coordinates of the Creature
     * @param c Creature's whose coordinates have to be found
     * @return x and y coordinates of a Creature in an array
     */
    public static int[] getCreatureCoordinates(Creature c){
        int[] xy = new int[2];

        for (int i=0; i<creature_arr.length; i++ ) {
            if(creature_arr[i]!= null && creature_arr[i].equals(c)){
                xy[0]=x_coordinates[i];
                xy[1]=y_coordinates[i];
            }
        }
        return xy;
    }

    public Creature getCreatureFromCoordinates(int x,  int y){

        for (int i=0; i<num_of_creatures; i++ ) {
            if(creature_arr[i]!= null && x_coordinates[i] == x && y_coordinates[i]==y){
                return creature_arr[i];
            }
        }
        return null;
    }

    /**
     * This method finds the target Creature
     * @param targetCreatureType the type of target Creature to be found
     * @param arrayList List of Creatures
     * @param creatureToPlay The Creature that will attack
     * @return Target Creature
     */
    public static ArrayList<Creature> findTargetCreature(String targetCreatureType, ArrayList<Creature> arrayList, Creature creatureToPlay) {
        ArrayList<Creature> Target = new ArrayList<Creature>();
        int creatureToPlayIndex = arrayList.indexOf(creatureToPlay);
        int distance = 0;
        int[] attackerCoord = getCreatureCoordinates(creatureToPlay);
        int[] targetCoord = new int[2];

        //while((arrayList.size() > count) && (!targetFound)) {
        for (int count = 0; count < arrayList.size(); count++) {
            if (arrayList.get(count).getClass().getName().equals(targetCreatureType)) {
                targetCoord = getCreatureCoordinates(arrayList.get(count));
                if (((Math.abs(targetCoord[0] - attackerCoord[0]) == 1) && (Math.abs(targetCoord[1] - attackerCoord[1]) == 0))
                        || ((Math.abs(targetCoord[1] - attackerCoord[1]) == 1) && (Math.abs(targetCoord[0] - attackerCoord[0]) == 0))
                        || ((Math.abs(targetCoord[1] - attackerCoord[1]) == 1) && (Math.abs(targetCoord[0] - attackerCoord[0]) == 1))) {
                    Target.add(arrayList.get(count));
                }
            }
        }
        return Target;
    }

    /**
     * Clears the Map
     */
    public void clearMap(){
        Arrays.fill(this.creature_arr, null);
        Arrays.fill(this.x_coordinates, 0);
        Arrays.fill(this.y_coordinates, 0);
        this.num_of_creatures = 0;
    }

    /**
     * Prints the 2D Map
     */
    public void print2DMap() {
        char[][] mapArr;
        mapArr = new char[25][25];
        int p = 0;
        int q = 0;

        for (int i = 0; i < mapArr.length; i++) {
            Arrays.fill(mapArr[i], ' ');
        }

        for(int i=0; i<creature_arr.length; i++){
            if(creature_arr[i] != null && creature_arr[i].getClass().getName().equals("Player")){
                mapArr[y_coordinates[i]][x_coordinates[i]] = 'P';
            }
            if(creature_arr[i] != null && creature_arr[i].getClass().getName().equals("Monster")){
                mapArr[y_coordinates[i]][x_coordinates[i]] = 'M';
            }
        }
        for (int j = 0; j < mapArr[0].length; j++) {
            System.out.printf("\t%2d", j);
        }
        System.out.println();
        for (int i = 0; i < mapArr.length; i++) {
            System.out.print(i);
            for (int j = 0; j < mapArr[i].length; j++) {
                System.out.printf("\t%2c", mapArr[i][j]);
            }
            System.out.println();
        }
    }

    /**
     * Determines the number of Players from the creature_arr
     * @return The number of Players
     */
    public static int NumberOfPlayers() {
        int Playercount = 0;
        for (int i = 0; i < num_of_creatures; i++) {
            if(creature_arr[i] != null && creature_arr[i] instanceof Player) {
                Playercount++;
            }
        }
        return Playercount;
    }

    public static int NumberOfMonsters() {
        int Monstercount = 0;
        for (int i = 0; i < num_of_creatures; i++) {
            if(creature_arr[i] != null && creature_arr[i] instanceof Monster) {
                Monstercount++;
            }
        }
        return Monstercount;
    }

    /**
     * Enables Monster's movement
     * @param M the monster whose position has to be changed
     */
    public static void MonsterMovement(Monster M) {
        int playerCount = NumberOfPlayers();
        Creature[] playersOnly = new Creature[playerCount];
        int j = 0;
        int[][] coordinates = new int[playerCount][2];
        int[] MonsterCoordinates = getCreatureCoordinates(M);
        Integer[] distance = new Integer[playerCount];

        for (int i = 0; i < num_of_creatures; i++) {
            if (creature_arr[i] != null && creature_arr[i] instanceof Player) {
                playersOnly[j] = creature_arr[i];
                coordinates[j] = getCreatureCoordinates(playersOnly[j]);
                distance[j] = (int) Math.sqrt((Math.pow((coordinates[j][0] - MonsterCoordinates[0]), 2)) + (Math.pow((coordinates[j][1] - MonsterCoordinates[1]), 2)));
                j++;
            }
        }
        Integer[] distanceCopy = distance.clone();

        Arrays.sort(distanceCopy);

        int playerIndex = Arrays.asList(distance).indexOf(distanceCopy[0]);
        Player playerClosest = (Player) playersOnly[playerIndex];

        // to find the distance between two points:
        if(playerClosest != null) {
            Random rn = new Random();
            int numStepsLR = rn.nextInt(6);
            int numStepsUpDown = 5 - numStepsLR;

            int up = 0, down = 0, right = 0, left = 0; //new convention
            if (MonsterCoordinates[0] > coordinates[playerIndex][0]) {  //move up
                up = (Math.min(MonsterCoordinates[0] - coordinates[playerIndex][0], numStepsLR));
            }
            if (MonsterCoordinates[0] < coordinates[playerIndex][0]) {  //move down
                down = (Math.min(coordinates[playerIndex][0] - MonsterCoordinates[0], numStepsLR));
            }
            if (MonsterCoordinates[1] > coordinates[playerIndex][1]) {  //move left
                left = (Math.min(MonsterCoordinates[1] - coordinates[playerIndex][1], numStepsUpDown));
            }
            if (MonsterCoordinates[1] < coordinates[playerIndex][1]) {  //move right
                right = (Math.min(coordinates[playerIndex][1] - MonsterCoordinates[1], numStepsUpDown));
            }
            System.out.println("Monster will move towards Player: " + playerClosest.getName() + " with steps: " + " Left: " + left + " Right: " + right + " Up: " + up + " Down: " + down);

            creatureOnMap(right, left, up, down, M);
        }
    }

    @Override
    public String toString() {
        return "Map{ " +
                "x_coordinates = " + Arrays.toString(x_coordinates) +
                ", y_coordinates = " + Arrays.toString(y_coordinates) +
                ", creature_arr = " + Arrays.toString(creature_arr) +
                ", num_of_creatures = " + num_of_creatures +
                '}';
    }
}
