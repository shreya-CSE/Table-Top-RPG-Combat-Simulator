import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartGame extends JPanel implements ActionListener {

    private JComboBox monsterSelected;
    public StartGame(ActionListener listener) {
        if(GUIController.getNoPlayersInParty() < 1) {
            JOptionPane.showMessageDialog(null,"Require atleast one player");
            System.out.printf("Not enough players in player.\nCurrent number of Players: %d\nMaximum number of Players: %d", GUIController.getNoPlayersInParty(), GUIController.getPlayersInParty().length);
            GUIController.showCenterPanel();
        }
        else {
            GUIController.showMonsterPanel();
        }

    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
