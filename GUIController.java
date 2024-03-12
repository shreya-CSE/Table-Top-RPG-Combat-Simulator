import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import static javax.swing.JOptionPane.*;

public class GUIController implements ActionListener {
    final static int MAX_PLAYERS = 100;
    final static int PARTY_LIMIT = 4;
    final static int hp = 50;
    final static int ac = 15;

    private static JFrame mainFrame;
    private static PartyPanel partyPanel;
    private static MonstersPanel monstersPanel;
    private static CombatViewPanel combatViewPanel;
    private static CreatePanel createPanel;
    private static JPanel fillerPanel;
    private LoadPanel loadPanel;
    private SavePanel savePanel;
    private StartGame startGame;
    private static JMenuBar menuBar;
    private static JPanel centerPanel;
    private static JLabel statusBar;
    private JPanel statusPanel;
    private static Player p;
    private static Player[] playersInMemory = new Player[MAX_PLAYERS];
    private static Player[] playersInParty = new Player[PARTY_LIMIT];
    private static Monster[] monstersToCombat;
    private static int numMonstersInGame =0;
    private static ArrayList<Creature> creaturesInCombat = new ArrayList<Creature>();;
    private static int playerCount = 0;
    private static boolean isSaved = true;
    private static boolean hasFile = false;
    private static String fileName = "";
    private static int noPlayersInParty = 0;
    private static Map gameMap = new Map();
    private static int disarmStatus = 0;
    private static int disarmFlag = 0;

    public static String getFileName() {
        return fileName;
    }
    public static void setFileName(String filename) {
        fileName = filename;
    }
    public static boolean getHasFile() {
        return hasFile;
    }
    public static boolean getIsSaved() {
        return isSaved;
    }
    public static void setIsSaved(boolean saved) {
        isSaved = saved;
    }
    public static void setHasFile(boolean has_file) {
        hasFile = has_file;
    }
    public static Player getP() {
        return p;
    }
    public static void setP(Player p) {
        GUIController.p = p;
    }
    public static void updatePlayerCountBy (int increment){
        GUIController.playerCount = GUIController.playerCount + increment;
    }
    public static Player[] getPlayersInParty() {
        return playersInParty;
    }

    public static int getNoPlayersInParty() {
        return noPlayersInParty;
    }
    public static Player[] getPlayersInMemory() {
        return playersInMemory;
    }

    public static int getPlayerCount() {
        return playerCount;
    }
    public static Monster[] getMonstersToCombat() {
        return monstersToCombat;
    }

    public static Map getGameMap() {
        return gameMap;
    }

    public static int getNumMonstersInGame() {
        return numMonstersInGame;
    }

    public static ArrayList<Creature> getCreaturesInCombat() {
        return creaturesInCombat;
    }
    public static int getDisarmStatus() {
        return disarmStatus;
    }
    public static void setDisarmStatus(int disarmStatus) {
        GUIController.disarmStatus = disarmStatus;
    }
    public static int getDisarmFlag() {
        return disarmFlag;
    }
    public static void setDisarmFlag(int disarmFlag) {
        GUIController.disarmFlag = disarmFlag;
    }

    public static void setPlayersInParty(Player[] playersInParty) {
        GUIController.playersInParty = playersInParty;
    }

    public static void setNoPlayersInParty(int noPlayersInParty) {
        GUIController.noPlayersInParty = noPlayersInParty;
    }

    public static MonstersPanel getMonstersPanel() {
        return monstersPanel;
    }

    public GUIController() {
        mainFrame = new JFrame("Table Top RPG Game");

        mainFrame.setSize(600, 400);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        createPanel = new CreatePanel(new CreatePlayerListener());
        createPanel.setPreferredSize(new Dimension(350, 200));

       partyPanel = new PartyPanel(2, 2);

        partyPanel.setPreferredSize(new Dimension(100, 100));
        partyPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

        mainFrame.add(partyPanel, BorderLayout.CENTER);

        Monster[] monsterArray = GameUtilities.createMonster();
        monstersPanel = new MonstersPanel(monsterArray);
        monstersPanel.setPreferredSize(new Dimension(350, 200));
        monstersPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

        combatViewPanel = new CombatViewPanel(10,10);
        combatViewPanel.setPreferredSize(new Dimension(350, 200));
        combatViewPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

        fillerPanel = new JPanel();
        fillerPanel.setPreferredSize(new Dimension(10, 200));
        mainFrame.add(fillerPanel, BorderLayout.EAST);

        configureMenu();
        configureStatusBar();
        configureCenterPanel();
        mainFrame.setVisible(true);
    }

    public void configureStatusBar() {
        statusPanel = new JPanel();
        statusPanel.setPreferredSize(new Dimension(250, 100));
        statusPanel.setLayout(new BoxLayout(statusPanel, BoxLayout.X_AXIS));
        statusBar = new JLabel("Welcome to the Game");

        statusPanel.add(statusBar);
        mainFrame.add(statusPanel, BorderLayout.SOUTH);
    }

    public static void updateStatusBar(String text){
        GUIController.statusBar.setText(text);
        GUIController.statusBar.repaint();
    }
    public static void showCenterPanel() {
        System.out.println("Inside showCenterPanel");
        mainFrame.remove(createPanel);
        mainFrame.remove(partyPanel);
        mainFrame.remove(monstersPanel);
        mainFrame.remove(fillerPanel);
        mainFrame.remove(combatViewPanel);

        if(playerCount > 0) {
            centerPanel.getComponent(7).setEnabled(true);
            updateMenu();
        }

        mainFrame.add(centerPanel, BorderLayout.WEST);

        mainFrame.revalidate();
        if (p != null) {
            statusBar.setText("Character name: " + p.getName());
        }

        mainFrame.add(partyPanel);
        mainFrame.add(partyPanel, BorderLayout.CENTER);
        mainFrame.add(fillerPanel);
        mainFrame.add(fillerPanel, BorderLayout.EAST);
        centerPanel.setVisible(true);
        mainFrame.revalidate();
        mainFrame.repaint();

    }

    public void showCreatePanel() {
        if (createPanel.isShowing()) {
            return;
        }
        mainFrame.remove(centerPanel);
        mainFrame.remove(partyPanel);
        mainFrame.remove(fillerPanel);
        mainFrame.remove(monstersPanel);
        mainFrame.remove(combatViewPanel);

        mainFrame.add(createPanel);
        mainFrame.add(createPanel, BorderLayout.WEST);
        mainFrame.add(partyPanel);
        mainFrame.add(partyPanel, BorderLayout.CENTER);
        mainFrame.add(fillerPanel);
        mainFrame.add(fillerPanel, BorderLayout.EAST);
        createPanel.setVisible(true);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    public static void showMonsterPanel() {

        if (monstersPanel.isShowing()) {
            return;
        }
        mainFrame.remove(centerPanel);
        mainFrame.remove(createPanel);
        mainFrame.remove(partyPanel);
        mainFrame.remove(fillerPanel);
        mainFrame.remove(combatViewPanel);

        monstersPanel.resetSelectedMonsterData();
        combatViewPanel.resetCombatViewPanel();
        //create new map object every time game starts
        gameMap = new Map();

        mainFrame.add(monstersPanel);
        mainFrame.add(monstersPanel, BorderLayout.WEST);
        mainFrame.add(partyPanel);
        mainFrame.add(partyPanel, BorderLayout.CENTER);
        mainFrame.add(fillerPanel);
        mainFrame.add(fillerPanel, BorderLayout.EAST);
        monstersPanel.setVisible(true);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    public static void showCombatViewPanel(ArrayList<String> monsterList) throws IOException {

        if (combatViewPanel.isShowing()) {
            return;
        }
        mainFrame.remove(centerPanel);
        mainFrame.remove(createPanel);
        mainFrame.remove(partyPanel);
        mainFrame.remove(fillerPanel);
        mainFrame.remove(monstersPanel);

        //clear creaturesInCombat
        creaturesInCombat.clear();
        monstersToCombat = GameUtilities.createMonstersFromStringArray(monsterList.stream().toList().toArray(new String[0]));
        numMonstersInGame=monsterList.size();
        for(int i=0; i < monstersToCombat.length; i++) {
            gameMap.add_creature(monstersToCombat[i]);
            creaturesInCombat.add(monstersToCombat[i]);
        }
        //add Players in Party also to the combat view
        for(int i=0; i < noPlayersInParty; i++) {
            gameMap.add_creature(playersInParty[i]);
            creaturesInCombat.add(playersInParty[i]);
        }
        //update Creatures in Combat list
        for(int i=0; i < gameMap.getNum_of_creatures(); i++){
            if(gameMap.getCreature_arr()[i] !=null){
                int x = gameMap.getX_coordinates()[i];
                int y = gameMap.getY_coordinates()[i];

                System.out.println(gameMap.getCreature_arr()[i]+ " (" + gameMap.getX_coordinates()[i] + "," + gameMap.getY_coordinates()[i] + ")");
                String iconFile;
                if((gameMap.getCreature_arr()[i]).getClass().getName() == "Player") {
                    iconFile = ((Player) gameMap.getCreature_arr()[i]).getAvatar();
                    iconFile = ".\\AvatarFiles\\" +  iconFile;
                    Image img = new ImageIcon(iconFile).getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
                    combatViewPanel.updateCombatViewPanel(x, y, new ImageIcon(img), "Player");
                }
                if((gameMap.getCreature_arr()[i]).getClass().getName() == "Monster") {
                    iconFile = ((Monster) gameMap.getCreature_arr()[i]).getType() + ".png";
                    iconFile = ".\\AvatarFiles\\" +  iconFile;
                    Image img = new ImageIcon(iconFile).getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
                    combatViewPanel.updateCombatViewPanel(x, y, new ImageIcon(img), "Monster");
                }
            }
        }

        mainFrame.add(combatViewPanel);
        mainFrame.add(combatViewPanel, BorderLayout.WEST);
        mainFrame.add(partyPanel);
        mainFrame.add(partyPanel, BorderLayout.CENTER);
        mainFrame.add(fillerPanel);
        mainFrame.add(fillerPanel, BorderLayout.EAST);
        combatViewPanel.setVisible(true);
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    public static void refreshCombatPanelAfterAttack(){

        // reset all combat cells to original
        combatViewPanel.resetCombatViewPanel();

        // update combat cells to the latest creature positions
        for(int i=0; i < gameMap.getNum_of_creatures(); i++){
            if(gameMap.getCreature_arr()[i] !=null){
                int x = gameMap.getX_coordinates()[i];
                int y = gameMap.getY_coordinates()[i];

                System.out.println(gameMap.getCreature_arr()[i]+ " (" + gameMap.getX_coordinates()[i] + "," + gameMap.getY_coordinates()[i] + ")");
                String iconFile;
                if((gameMap.getCreature_arr()[i]).getClass().getName() == "Player") {
                    iconFile = ((Player) gameMap.getCreature_arr()[i]).getAvatar();
                    iconFile = ".\\AvatarFiles\\" +  iconFile;
                    Image img = new ImageIcon(iconFile).getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
                    combatViewPanel.updateCombatViewPanel(x, y, new ImageIcon(img), "Player" );
                }
                if((gameMap.getCreature_arr()[i]).getClass().getName() == "Monster") {
                    iconFile = ((Monster) gameMap.getCreature_arr()[i]).getType() + ".png";
                    iconFile = ".\\AvatarFiles\\" +  iconFile;
                    Image img = new ImageIcon(iconFile).getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
                    combatViewPanel.updateCombatViewPanel(x, y, new ImageIcon(img), "Monster" );
                }
            }
        }

    }

    private static void updateMenu() {
        int iCount = menuBar.getMenu(0).getItemCount();
        for (int i = 0; i < iCount; i++) {
            JMenuItem itemObject = menuBar.getMenu(0).getItem(i);
            if (itemObject != null ) {
                //System.out.println(itemObject.getText());
                if(itemObject.getText().equals("Save Character")){
                    itemObject.setEnabled(true);
                }
            }
        }
    }


    private void configureMenu() {
        menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        JMenuItem startItem = new JMenuItem("Start Game");
        JMenuItem createItem = new JMenuItem("Create Character");
        JMenuItem loadItem = new JMenuItem("Load Character");
        JMenuItem saveItem = new JMenuItem("Save Character");
        JMenuItem exitItem = new JMenuItem("Exit");

        startItem.addActionListener(this);
        createItem.addActionListener(this);
        loadItem.addActionListener(this);
        saveItem.addActionListener(this);
        exitItem.addActionListener(this);

        fileMenu.add(startItem);
        fileMenu.add(createItem);
        fileMenu.add(loadItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);

        saveItem.setEnabled(false);
        if (playerCount > 0) {
            saveItem.setEnabled(true);
        }

        menuBar.add(fileMenu);
        mainFrame.add(menuBar, BorderLayout.NORTH);
    }

    private void configureCenterPanel() {
        centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        JButton startButton = new JButton("Start Game");
        JButton createButton = new JButton("Create Character");
        JButton loadButton = new JButton("Load Character");
        JButton saveButton = new JButton("Save Character");
        JButton exitButton = new JButton("Exit");

        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        createButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        startButton.addActionListener(this);
        createButton.addActionListener(this);
        loadButton.addActionListener(this);
        saveButton.addActionListener(this);
        exitButton.addActionListener(this);

        centerPanel.add(Box.createVerticalGlue());
        centerPanel.add(startButton);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        centerPanel.add(createButton);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        centerPanel.add(loadButton);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        centerPanel.add(saveButton);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        centerPanel.add(exitButton);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        centerPanel.add(Box.createVerticalGlue());

        startButton.setMaximumSize(new Dimension(150, 25));
        Dimension d = startButton.getMaximumSize();
        createButton.setMaximumSize(new Dimension(d));
        loadButton.setMaximumSize(new Dimension(d));
        saveButton.setMaximumSize(new Dimension(d));
        exitButton.setMaximumSize(new Dimension(d));

        saveButton.setEnabled(false);
        if(playerCount > 0) {
            saveButton.setEnabled(true);
            updateMenu();
        }
        centerPanel.setPreferredSize(new Dimension(350, 200));
        mainFrame.add(centerPanel, BorderLayout.WEST);
    }

    public static int updatePlayerInPartyArray(String curPlayer, Object newPlayer){
        int replaced=1;
        //search for newPlayer in PlayersinParty and proceed further otherwise return error
        for (int i=0; i<GUIController.getNoPlayersInParty(); i++){
            if(GUIController.getPlayersInParty()[i].getName().equals(((Player) newPlayer).getName())){
                return 1;
            }
        }

        //search for current player in PlayersInParty array and replace with newPlayer
        for (int i=0; i<GUIController.getNoPlayersInParty(); i++){
            if(GUIController.getPlayersInParty()[i].getName().equals(curPlayer)){
                GUIController.getPlayersInParty()[i]= (Player) newPlayer;
                PartyPanel.updatePlayersInPartyUI();
                gameMap.replaceCreatureOnMap(curPlayer, (Creature) newPlayer);
                GUIController.refreshCombatPanelAfterAttack();
                replaced=0;
            }
        }
        if(replaced == 0) {
            return 0;
        }
        else{
            return 1;
        }
    }

    public static void removePlayerFromPlayerInPartyArray(Player player){

        Player[] temp_players = new Player[PARTY_LIMIT];
        int i, k;

        //search for current player in PlayersInParty array and replace with newPlayer
        for (i=0, k=0; i<GUIController.getNoPlayersInParty(); i++){
            if(GUIController.getPlayersInParty()[i].getName().equals(player.getName())){
                continue;
            }
            temp_players[k] = GUIController.getPlayersInParty()[i];
            k++;
        }
        GUIController.setNoPlayersInParty(k);
        GUIController.setPlayersInParty(temp_players);
    }

    public static void getNewPlayerPosition(Player p){

        String strXY = JOptionPane.showInputDialog("Enter new X,Y coordinates to move" );
        if(strXY != null && !(strXY.isEmpty())) {
            System.out.println(strXY);
            String[] input = strXY.split(",");
            int xCoord = Integer.parseInt(input[0]);
            int yCoord = Integer.parseInt(input[1]);
            gameMap.changeCreatureLocationOnMap(p, xCoord, yCoord);
        }
    }

    public static void combat(String option, int xPosition, int yPosition) {
        switch (option) {
            case "Attack":
                GUIController.statusBar.setText("Attack Initiated");
                Attack.Combat_Attacking();  // input should have both attacker and target
                gameMap.checkHPandRemovePlayersFromMap();
                GUIController.refreshCombatPanelAfterAttack();
                if(gameMap.NumberOfPlayers() == 0 && gameMap.NumberOfMonsters() == 0){
                    JOptionPane.showMessageDialog(null, "No Players or Monsters on the map");
                    break;
                }
                if(gameMap.NumberOfMonsters() == 0){
                    JOptionPane.showMessageDialog(null, "Players WON the game");
                    break;
                }
                if(gameMap.NumberOfPlayers() == 0){
                    JOptionPane.showMessageDialog(null, "Monsters WON the game");
                    break;
                }
                break;
            case "Disarm":
                GUIController.statusBar.setText("Disarm Initiated");
                if(disarmFlag == 0){
                    disarmFlag = 2;
                }
                Disarm.Combat_Disarming();  // input should have both attacker and target
                gameMap.checkHPandRemovePlayersFromMap();
                GUIController.refreshCombatPanelAfterAttack();
                if(gameMap.NumberOfPlayers() == 0 && gameMap.NumberOfMonsters() == 0){
                    JOptionPane.showMessageDialog(null, "No Players or Monsters on the map");
                    break;
                }
                if(gameMap.NumberOfMonsters() == 0){
                    JOptionPane.showMessageDialog(null, "Players WON the game");
                    break;
                }
                if(gameMap.NumberOfPlayers() == 0){
                    JOptionPane.showMessageDialog(null, "Monsters WON the game");
                    break;
                }
                break;
            case "End turn":
                Creature removeCreature;
                removeCreature=gameMap.getCreatureFromCoordinates(xPosition, yPosition);
                gameMap.removeCreatureOnMap(removeCreature);
                GUIController.refreshCombatPanelAfterAttack();
                GUIController.removePlayerFromPlayerInPartyArray((Player) removeCreature);
                partyPanel.resetPlayersInPartyPanelUI();
                partyPanel.updatePlayersInPartyUI();
                break;
        }
    }
    public static boolean isPlayerExistsInMemory(String name){

        boolean playerExist=false;
        for (int i=0; i<GUIController.getPlayerCount(); i++){
            if((GUIController.getPlayersInMemory()[i] != null) && ((GUIController.getPlayersInMemory()[i]).getName().equals(name))){
                playerExist = true;
                GUIController.setP(null);
            }
        }
        return playerExist;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        String actionName = actionEvent.getActionCommand();
        switch (actionName) {
            case "Start Game":
                startGame = new StartGame(this);
                break;
            case "Create Character":
                showCreatePanel();
                break;
            case "Load Character":
                System.out.println("Main thread " + Thread.currentThread());
                try {
                    loadPanel = new LoadPanel(this);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                System.out.println("Returned to main thread " + Thread.currentThread());
                break;
            case "Character Loaded":  // for multi threading action listener
                System.out.println("Player loaded in background thread!");
                if(GUIController.getP() != null) {
                    playersInMemory[playerCount] = p;
                    playerCount++;
                    if (noPlayersInParty < 4) {
                        playersInParty[noPlayersInParty] = p;
                        noPlayersInParty++;
                        partyPanel.resetPlayersInPartyPanelUI();
                        partyPanel.updatePlayersInPartyUI();
                    }
                }
                createPanel.clearFields();
                showCenterPanel();
                break;
            case "Save Character":
                System.out.println("Main thread " + Thread.currentThread());
                savePanel = new SavePanel(this);
                System.out.println("Returned to main thread " + Thread.currentThread());
                break;
            case "Character Saved":  // for multi threading action listener
                System.out.println("Player saved from background thread!");
                break;
            case "Exit":
                if (!getIsSaved()) {
                    int exitOption = JOptionPane.showConfirmDialog(null, "Do you want to save current player?", "Exit Game", JOptionPane.YES_NO_CANCEL_OPTION);
                    if (exitOption == YES_OPTION) {
                        savePanel = new SavePanel(this);
                    }
                    else if (exitOption == CANCEL_OPTION) {
                        break;
                    }
                }
                mainFrame.dispose();;
                break;
        }
    }

    private class CreatePlayerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            String actionName = actionEvent.getActionCommand();
            switch (actionName) {
                case "CreatePanel.Random Stats" :
                    //System.out.println("Random stats selected");
                    int[] randomStatArray = GameUtilities.randomStats();
                    createPanel.randomStatsFields(randomStatArray);
                    break;
                case "CreatePanel.Create":
                    int statsSum = createPanel.getConValue() + createPanel.getDexValue() + createPanel.getStrValue();
                    if ((createPanel.getStrValue() >= 0) && (createPanel.getConValue() >= 0) && (createPanel.getDexValue() >= 0) && (statsSum <= 15)) {
                        //check for player name and accordingly create or update the player
                        if(!GUIController.isPlayerExistsInMemory(createPanel.getPlayerName())) {
                            p = new Player(createPanel.getPlayerName(), hp, ac, createPanel.getStrValue(), createPanel.getDexValue(), createPanel.getConValue(), createPanel.getWeapon(), createPanel.getAvatar());
                            setIsSaved(false);
                            setHasFile(false);
                            setFileName(null);
                            JOptionPane.showMessageDialog(createPanel, "New Player " + p.getName() + " created");
                            playersInMemory[playerCount] = p;
                            playerCount++;
                            if (noPlayersInParty < 4) {
                                playersInParty[noPlayersInParty] = p;
                                noPlayersInParty++;
                                PartyPanel.updatePlayersInPartyUI();
                            }
                            System.out.println("No of players in party" + noPlayersInParty);
                            System.out.println("No of players in memory" + playerCount);

                        } else{
                            JOptionPane.showMessageDialog(null, "Player " + createPanel.getPlayerName() + " already exists");
                        }

                        System.out.println();
                        createPanel.clearFields();
                        showCenterPanel();

                    } else {
                        JOptionPane.showMessageDialog(createPanel, "The sum of STR, DEX, and CON stats exceeds 15");
                    }
                    setIsSaved(false);
                    break;
                case "CreatePanel.Cancel":
                    createPanel.clearFields();
                    showCenterPanel();
                    break;
                case "StartGame.OK":
                    System.out.println("StartGame.OK");
                    showCenterPanel();
                    break;
            }
        }
    }
}

class CombatView implements Runnable {

    private ArrayList<String> monstersToCombat;

    public CombatView(ArrayList<String> monsterData ) {
        monstersToCombat = monsterData;
    }

    @Override
    public void run() {
        System.out.println("New thread to initiate creature locations on combat map");
        try {
            GUIController.showCombatViewPanel(monstersToCombat);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
