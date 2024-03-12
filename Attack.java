import java.util.ArrayList;

public class Attack {
    private static Creature attCre=null;
    private static int attackInt;

    public static Creature getAttCre() {
        return attCre;
    }

    public static void setAttCre(Creature attCre) {
        Attack.attCre = attCre;
    }

    public static int getAttackInt() {
        return attackInt;
    }

    public static void setAttackInt(int attackInt) {
        Attack.attackInt = attackInt;
    }

    //private static Map map = new Map();
    public static void Combat_Attacking(){

        // multi threading start
        Thread t1 = new Thread(new GameUtilities(GUIController.getCreaturesInCombat()));
        System.out.println("Starting new thread for Roll Initiative");

        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Ended new thread for Roll Initiative");

        Creature attackCreature=null;
        while(attCre == null){
            System.out.println("Attack creature from parallel thread");
            attackCreature = attCre;
        }
        attackCreature = attCre;
        attCre = null;

        // multi threading end

        //attackCreature = GameUtilities.rollInitiative(GUIController.getCreaturesInCombat());
        Creature targetCreature = null;
        ArrayList<Creature> TargetCreatures = new ArrayList<>();

        if ((attackCreature instanceof Monster) && (GUIController.getDisarmFlag() > 0 )) {
            Map.MonsterMovement((Monster) attackCreature);
            GUIController.setDisarmFlag(GUIController.getDisarmFlag() - 1);
            return;
        }
        if ((attackCreature instanceof Player) && (GUIController.getDisarmFlag() > 0 )) {
            GUIController.setDisarmFlag(GUIController.getDisarmFlag() - 1);
        }
        String statusText;
        if (attackCreature instanceof Player) {
            TargetCreatures = Map.findTargetCreature("Monster", GUIController.getCreaturesInCombat(), attackCreature);
            statusText = "Player " + attackCreature.getName() + " is attacking";
            GUIController.updateStatusBar(statusText);
        }
        else {
            TargetCreatures = Map.findTargetCreature("Player", GUIController.getCreaturesInCombat(), attackCreature);
            statusText = "Monster " + attackCreature.getName() + " is attacking";
            GUIController.updateStatusBar(statusText);
        }

        if (TargetCreatures.size() > 0) {
            targetCreature = TargetCreatures.get(0);
        }
        else {
            targetCreature = null;
        }
        if (targetCreature != null) {
            attackCreature.attack(targetCreature);
        }
        else{
            System.out.println("No target in the vicinity for " + attackCreature);
        }
        if(attackCreature.getClass().getName().equals("Player")){
            GUIController.getNewPlayerPosition((Player) attackCreature);
        }
        if(attackCreature.getClass().getName().equals("Monster")){
            System.out.println("Monster will try to move towards nearest Player.");
            Map.MonsterMovement((Monster) attackCreature);
        }
    }
}
