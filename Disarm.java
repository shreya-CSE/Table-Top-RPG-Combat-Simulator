import java.util.ArrayList;

public class Disarm {

    public static void Combat_Disarming(){
        // TODO:
        Creature disarmCreature = GameUtilities.rollInitiative(GUIController.getCreaturesInCombat());
        Creature targetCreature = null;

        ArrayList<Creature> TargetCreatures = new ArrayList<>();
        if ((disarmCreature instanceof Monster) && (GUIController.getDisarmFlag() > 0 )) {
            GUIController.setDisarmFlag(GUIController.getDisarmFlag() - 1);
            return;
        }
        if ((disarmCreature instanceof Player) && (GUIController.getDisarmFlag() > 0 )) {
            GUIController.setDisarmFlag(GUIController.getDisarmFlag() - 1);
        }

        if (disarmCreature instanceof Player) {
            TargetCreatures = Map.findTargetCreature("Monster", GUIController.getCreaturesInCombat(), disarmCreature);
        }else {
            TargetCreatures = Map.findTargetCreature("Player", GUIController.getCreaturesInCombat(), disarmCreature);
        }

        if (TargetCreatures.size() > 0) {
            targetCreature = TargetCreatures.get(0);
        }
        else {
            targetCreature = null;
        }

        if (targetCreature != null) {
            disarmCreature.attack(targetCreature);
        }
        else{
            System.out.println("No target in the vicinity for " + disarmCreature);
        }
        if(disarmCreature.getClass().getName().equals("Player")){
            GUIController.getNewPlayerPosition((Player) disarmCreature);
        }
    }
}
