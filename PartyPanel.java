import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import static javax.swing.JOptionPane.OK_OPTION;
import static javax.swing.JOptionPane.YES_OPTION;

public class PartyPanel extends JPanel implements ActionListener {

    private static ArrayList<JButton> buttons = new ArrayList<JButton>();

    public PartyPanel(int xCount, int yCount) {

        this.setLayout(new GridLayout(xCount,yCount));

        for(int i = 0; i<(xCount*yCount); i++){
            if (GUIController.getPlayersInParty()[i] != null) {
                this.buttons.add((new JButton("Player: " + GUIController.getPlayersInParty()[i].getName())));  //+ "Avatar: " + GUIController.getPlayersInParty()[i].getAvatar())));
                System.out.println( GUIController.getPlayersInParty()[i].getName());
            }
            else {
                this.buttons.add((new JButton("Player Not Set")));
            }
            this.buttons.get(i).addActionListener(this);
            this.buttons.get(i).setToolTipText("Player Details");
            this.add(buttons.get(i));
        }

    }

    public static Player[] addPartyPlayer(Player p) {
        Player[] InParty = new Player[GUIController.getPlayersInParty().length];
        InParty[GUIController.getNoPlayersInParty()] = p;
        return InParty;
    }
    public void resetPlayersInPartyPanelUI(){

        for(JButton b : buttons){
            b.setIcon(null);
            b.setText("Player Not Set");
            b.setToolTipText("Player Details");
        }
        this.revalidate();
        this.repaint();
    }

    public static void updatePlayersInPartyUI(){

        for(int i=0; i < GUIController.getNoPlayersInParty(); i++) {
            String name;
            String avatar;
            String info;
            name = (GUIController.getPlayersInParty()[i]).getName();
            avatar =  (GUIController.getPlayersInParty()[i]).getAvatar();
            info = (GUIController.getPlayersInParty()[i]).getName();
            buttons.get(i).setText(info);
            info = (GUIController.getPlayersInParty()[i]).getToolTipString();
            buttons.get(i).setToolTipText(info);
            String iconFile;
            iconFile = (GUIController.getPlayersInParty()[i]).getAvatar();
            iconFile = ".\\AvatarFiles\\" +  iconFile;
            Image img = new ImageIcon(iconFile).getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH);
            buttons.get(i).setIcon(new ImageIcon(img));

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();

        System.out.println("Inside action performed");
        int partyPlayerChangeOption = JOptionPane.showConfirmDialog(null, "Do you want to remove the player from the party?", "Players in party", JOptionPane.YES_NO_OPTION);
        if (partyPlayerChangeOption == YES_OPTION) {
            // TODO: have to remove player

            if (GUIController.getPlayerCount() > 0) {
                Object[] possiblePlayers = new Object[GUIController.getPlayerCount()];
                for (int i = 0; i < GUIController.getPlayerCount(); i++) {
                    possiblePlayers[i] = GUIController.getPlayersInMemory()[i];
                }
                // TODO: have to add the player to party
                Object selectedValue = JOptionPane.showInputDialog(null, "Choose Player to be added", "Player Action", JOptionPane.INFORMATION_MESSAGE, null, possiblePlayers, possiblePlayers[0]);
                if (selectedValue != null) {
                    System.out.println(selectedValue.toString());
                    int playerReplaced = GUIController.updatePlayerInPartyArray(btn.getText(), selectedValue);
                    if(playerReplaced == 0){
                        JOptionPane.showMessageDialog(null, "Player replaced.");
                    } else{
                        JOptionPane.showMessageDialog(null, "Unable to replace Player. Player may already exists in Party Panel.");
                    }
                }
            }
            else {
                int noPlayersInMemory = JOptionPane.showConfirmDialog(null, "No players in memory", "Players", JOptionPane.OK_OPTION);
                if (noPlayersInMemory == OK_OPTION) {
                    return;
                }

            }
        }
    }
}
