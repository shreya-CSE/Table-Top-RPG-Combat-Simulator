import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import static javax.swing.JOptionPane.YES_NO_CANCEL_OPTION;

public class CombatViewPanel extends JPanel implements ActionListener {

    private ArrayList<JButton> buttons = new ArrayList<JButton>();

    public CombatViewPanel(int xCount, int yCount) {

        this.setLayout(new GridLayout(xCount,yCount));
        int bCount = 0;
        for(int i=0; i<xCount; i++) {
            for (int j = 0; j < yCount; j++) {
 //               JButton btn = new JButton(i + ","+ j);
                JButton btn = new JButton();
                btn.setName(i + ","+ j);
                this.buttons.add(btn);
                this.buttons.get(bCount).addActionListener(this);
                this.add(buttons.get(bCount));
                bCount++;
            }
        }
    }

    public void updateCombatViewPanel(int x, int y, Icon icon, String toolTipText){

        for(JButton b : buttons){
            String name = x + "," + y;
            if(b.getName().equals(name)){
                b.setText("");
                b.setIcon(icon);
                b.setToolTipText(toolTipText);
            }
        }
        this.revalidate();
        this.repaint();
    }

    public void resetCombatViewPanel(){

        for(JButton b : buttons){
            b.setIcon(null);
            b.setText(null);
            b.setToolTipText(null);
        }
        this.revalidate();
        this.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JButton btn;
        btn = (JButton) e.getSource();

        if(Objects.equals(btn.getToolTipText(), "Player")) {
            Object[] possibleValues = {"Attack", "Disarm", "End turn", "Cancel", "OK"};
            int retValue = JOptionPane.showInternalOptionDialog(null, "Choose Player Action", "Player Action", YES_NO_CANCEL_OPTION, QUESTION_MESSAGE, null, possibleValues, possibleValues[0]);
            System.out.println(retValue);

            String name = btn.getName();
            String[] xy = name.split(",");

            GUIController.combat((String) possibleValues[retValue], Integer.parseInt(xy[0]), Integer.parseInt(xy[1]));

        }
    }
}
