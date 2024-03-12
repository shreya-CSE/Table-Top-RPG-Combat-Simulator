import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class CreatePanel extends JPanel implements DocumentListener {
    private JTextField nameTextField;
    private JTextField strTextField;
    private JTextField dexTextField;
    private JTextField conTextField;
    private JComboBox weaponSelected;
    private JComboBox avatarSelected;
    private JButton createButton;

    public CreatePanel(ActionListener listener) {
        ArrayList fileList = new ArrayList<String>();

        File[] files = new File(".\\AvatarFiles\\").listFiles(((dir, name) -> name.endsWith(".png")));

        for (File file : files) {
            if (file.isFile()) {
                fileList.add(file.getName());
            }
        }

        Object[] arr = fileList.toArray();
        String[] avatarArray = Arrays.copyOf(arr, arr.length, String[].class);


        GridBagConstraints constraints = new GridBagConstraints();
        setLayout(new GridBagLayout());

        JLabel namePanel = new JLabel("Name: ");
        nameTextField = new JTextField("");
        JLabel strPanel = new JLabel("STR: ");
        strTextField = new JTextField("");
        JLabel dexPanel = new JLabel("DEX: ");
        dexTextField = new JTextField("");
        JLabel conPanel = new JLabel("CON: ");
        conTextField = new JTextField("");
        JLabel weaponPanel = new JLabel("Weapon: ");
        weaponSelected = new JComboBox(GameUtilities.createWeapon());
        JLabel avatarPanel = new JLabel("Avatar: ");
        avatarSelected = new JComboBox(avatarArray);

        createButton = new JButton("Create");
        JButton cancelButton = new JButton("Cancel");
        JButton randomButton = new JButton("Random Stats");

        JPanel buttonPanel = new JPanel();

        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.NONE;
        add(namePanel, constraints);
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.fill = GridBagConstraints.NONE;
        add(strPanel, constraints);
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.fill = GridBagConstraints.NONE;
        add(conPanel, constraints);
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.fill = GridBagConstraints.NONE;
        add(dexPanel, constraints);
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.fill = GridBagConstraints.NONE;
        add(weaponPanel, constraints);
        constraints.gridx = 0;
        constraints.gridy = 6;
        constraints.fill = GridBagConstraints.NONE;
        add(avatarPanel, constraints);
        constraints.gridx = 0;
        constraints.gridy = 7;
        constraints.fill = GridBagConstraints.NONE;

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        nameTextField.getDocument().addDocumentListener(this);
        nameTextField.setPreferredSize(new Dimension(300, 25));
        add(nameTextField, constraints);
        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        strTextField.getDocument().addDocumentListener(this);
        strTextField.setPreferredSize(new Dimension(300, 25));
        add(strTextField, constraints);
        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        dexTextField.getDocument().addDocumentListener(this);
        dexTextField.setPreferredSize(new Dimension(300, 25));
        add(dexTextField, constraints);
        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        conTextField.getDocument().addDocumentListener(this);
        conTextField.setPreferredSize(new Dimension(300, 25));
        add(conTextField, constraints);

        constraints.gridx = 1;
        constraints.gridy = 5;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        weaponSelected.setPreferredSize(new Dimension(300, 25));
        add(weaponSelected, constraints);
        weaponSelected.addActionListener(listener);

        constraints.gridx = 1;
        constraints.gridy = 6;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        avatarSelected.setPreferredSize(new Dimension(300, 25));
        add(avatarSelected, constraints);
        avatarSelected.addActionListener(listener);

        buttonPanel.add(Box.createRigidArea(new Dimension(60, 20)));

        constraints.gridx = 0;
        constraints.gridy = 0;
        randomButton.addActionListener(listener);
        randomButton.setActionCommand("CreatePanel.Random Stats");
        randomButton.setEnabled(true);
        buttonPanel.add(randomButton, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        createButton.addActionListener(listener);
        createButton.setActionCommand("CreatePanel.Create");
        createButton.setEnabled(false);
        buttonPanel.add(createButton, constraints);

        constraints.gridx = 2;
        constraints.gridy = 0;
        cancelButton.addActionListener(listener);
        cancelButton.setActionCommand("CreatePanel.Cancel");
        buttonPanel.add(cancelButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 10;
        constraints.gridwidth = 2;
        add(buttonPanel, constraints);
    }

    public void clearFields() {
        nameTextField.setText("");
        strTextField.setText("");
        dexTextField.setText("");
        conTextField.setText("");
        weaponSelected.setSelectedIndex(0);
        avatarSelected.setSelectedIndex(0);
    }

    public void randomStatsFields(int[] statArray) {
        strTextField.setText(String.valueOf(statArray[0]));
        dexTextField.setText(String.valueOf(statArray[1]));
        conTextField.setText(String.valueOf(statArray[2]));
    }

    public String getPlayerName() {
        return nameTextField.getText();
    }
    public int getStrValue() {
        return Integer.parseInt(strTextField.getText());
    }
    public int getDexValue() {
        return Integer.parseInt(dexTextField.getText());
    }
    public int getConValue() {
        return Integer.parseInt(conTextField.getText());
    }

    public Weapon getWeapon(){
        return (Weapon) weaponSelected.getSelectedItem();
    }
    public String getAvatar(){
        return (String) avatarSelected.getSelectedItem();
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        createButton.setEnabled(nameTextField.getDocument().getLength() != 0);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        createButton.setEnabled(nameTextField.getDocument().getLength() != 0);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {

    }
}
