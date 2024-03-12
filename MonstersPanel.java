import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;

import static javax.swing.JOptionPane.*;

public class MonstersPanel extends JPanel implements ActionListener {

    private JList<String> allMonstersList = new JList<String>();
    private JList<String> selectedMonstersList = new JList<String>();

    private ArrayList<String> allMonstersData = new ArrayList<String>();
    private ArrayList<String> selectedMonstersData = new ArrayList<String>();

    private JButton transferButton1;
    private JButton transferButton2;
    private JButton nextButton;
    private JPanel buttonGroupPanel;

    public ArrayList<String> getSelectedMonstersData() {
        return selectedMonstersData;
    }

    public MonstersPanel(Monster[] MonstersArray) {
        //Monster[] MonstersArray = GameUtilities.createMonster();
        for (int i = 0; i < MonstersArray.length; i++) {
            allMonstersData.add(MonstersArray[i].toString());
        }

        buttonGroupPanel = new JPanel();
        buttonGroupPanel.setPreferredSize(new Dimension(75,100));
        buttonGroupPanel.setLayout(new BoxLayout(buttonGroupPanel, BoxLayout.Y_AXIS));

        JPanel fillerPanel = new JPanel();
        transferButton1 = new JButton(">>");
        transferButton1.setActionCommand("Monster.Transfer.Right");
        transferButton1.setPreferredSize(new Dimension(50, 30));
        transferButton1.setAlignmentX(Component.CENTER_ALIGNMENT);
        transferButton1.addActionListener(this);
        buttonGroupPanel.add(transferButton1);

        transferButton2 = new JButton("<<");
        transferButton2.setActionCommand("Monster.Transfer.Left");
        transferButton2.setPreferredSize(new Dimension(50, 30));
        transferButton2.setAlignmentX(Component.CENTER_ALIGNMENT);
        transferButton2.addActionListener(this);
        buttonGroupPanel.add(transferButton2);

        JLabel topLabel = new JLabel("Select Monsters to add to the game");
        topLabel.setPreferredSize(new Dimension(200, 50));
        nextButton = new JButton("NEXT");
        nextButton.setActionCommand("Monster.Next");
        nextButton.setPreferredSize(new Dimension(50, 30));
        nextButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        nextButton.addActionListener(this);
        buttonGroupPanel.add(nextButton);


        String[] data = new String[allMonstersData.size()];
        data = allMonstersData.toArray(data);

        allMonstersList.setListData(data);
        allMonstersList.setAutoscrolls(true);
        JScrollPane scrollPane1 = new JScrollPane(allMonstersList);
        scrollPane1.setViewportView(allMonstersList);
        scrollPane1.setPreferredSize(new Dimension(125, 200));
        allMonstersList.setLayoutOrientation(JList.VERTICAL);
        allMonstersList.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

        data = new String[allMonstersData.size()];
        data = selectedMonstersData.toArray(data);

        selectedMonstersList.setListData(data);

        selectedMonstersList.setAutoscrolls(true);
        JScrollPane scrollPane2 = new JScrollPane(selectedMonstersList);
        scrollPane2.setViewportView(selectedMonstersList);
        scrollPane2.setPreferredSize(new Dimension(125, 200));
        selectedMonstersList.setLayoutOrientation(JList.VERTICAL);
        selectedMonstersList.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

        JLabel bottomLabel = new JLabel("Select Monsters to add to the game");
        bottomLabel.setPreferredSize(new Dimension(200, 50));

        JPanel middlePanel = new JPanel();
        middlePanel.setPreferredSize(new Dimension(50, 200));
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
        buttonGroupPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        middlePanel.add(buttonGroupPanel);

//        this.setLayout(new FlowLayout());
        this.setLayout(new BorderLayout());

//        this.add(scrollPane1);
//        this.add(buttonGroupPanel);
//        this.add(scrollPane2);
        this.add(topLabel, BorderLayout.PAGE_START);
        this.add(scrollPane1, BorderLayout.LINE_START);

        this.add(middlePanel, BorderLayout.CENTER);

        this.add(scrollPane2, BorderLayout.LINE_END);
        this.add(bottomLabel, BorderLayout.PAGE_END);
        this.revalidate();
        this.repaint();

//        this.add(selectedMonstersList, BorderLayout.LINE_END);
    }


    // Converts monsters to combat into monsters array
    public static Monster[] listToArray(ArrayList list) {
        String[] SelectedMonsterArray = (String[]) list.toArray();
        Monster[] monstersToCombat = new Monster[SelectedMonsterArray.length];
        for (int i = 0; i < SelectedMonsterArray.length; i++) {
            monstersToCombat[i] = GameUtilities.createMonsterObject(SelectedMonsterArray[i]);
        }
        return monstersToCombat;
    }

    public void resetSelectedMonsterData(){
        selectedMonstersData.clear();
        String[] data = new String[selectedMonstersData.size()];
        data = selectedMonstersData.toArray(data);
        selectedMonstersList.setListData(data);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn;
        String[] selectedList;
        String[] data;

        btn = (JButton) e.getSource();
        switch (e.getActionCommand()) {
            case "Monster.Transfer.Right":
                System.out.println("Inside action performed Right");
                selectedList = allMonstersList.getSelectedValuesList().toArray(new String[0]);
                for (var x:selectedList) {
                    selectedMonstersData.add(x);
                }
                data = new String[selectedMonstersData.size()];
                data = selectedMonstersData.toArray(data);
                selectedMonstersList.setListData(data);
                break;
            case "Monster.Transfer.Left":
                System.out.println("Inside action performed Left");
                selectedList = selectedMonstersList.getSelectedValuesList().toArray(new String[0]);
                for(String selected : selectedList) {
                    System.out.println(selected);
                    Iterator<String> itr;
                    for(itr = selectedMonstersData.iterator(); itr.hasNext();){
                        String name = itr.next();
                        if (name.equals(selected)) {
                            System.out.println("Match found");
                            itr.remove();
                            data = new String[selectedMonstersData.size()];
                            data = selectedMonstersData.toArray(data);
                            selectedMonstersList.setListData(data);
                        }
                    }
                }
                break;
            case "Monster.Next":
                System.out.println("in Monster.Next");
                for(var x:selectedMonstersData){
                    System.out.println(x.toString());
                }
                //GUIController.showCombatViewPanel(selectedMonstersData);

                // multi threading start
                Thread t1 = new Thread(new CombatView(selectedMonstersData));
                t1.start();
                // multi threading end

                break;
            default:
                break;
        }

    }
}
