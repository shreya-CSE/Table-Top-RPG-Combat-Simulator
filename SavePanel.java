import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class SavePanel extends JPanel implements ActionListener {

    public SavePanel(ActionListener listener ) {
        Player[] possiblePlayers = GUIController.getPlayersInMemory();
        Player selectedValue = (Player) JOptionPane.showInputDialog(null,"Choose Player to be saved from the list", "Player Save", JOptionPane.INFORMATION_MESSAGE, null, possiblePlayers, possiblePlayers[0]);
        if(selectedValue != null) {
            System.out.println(selectedValue.toString());

            if (selectedValue.getFilename() == null) {
                JFileChooser fileChooser = new JFileChooser(".");
                fileChooser.setFileFilter(new FileNameExtensionFilter("Comma Separated Value files", "csv"));
                int result = fileChooser.showSaveDialog(this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    selectedValue.setFilename(fileChooser.getSelectedFile().getName());
                    FileOpUtilities.writePlayerData(listener, selectedValue.getFilename(), selectedValue);
                }

            } else {
                FileOpUtilities.writePlayerData(listener, selectedValue.getFilename(), selectedValue);
            }
        }
    }

    public void writePlayertoFile(Player p, String fileName) {
        if(fileName != null) {
            try (PrintWriter writer = new PrintWriter(fileName)) {
                writer.append(p.getName());
                writer.append(',');
                writer.append(String.valueOf(p.getSTR()));
                writer.append(',');
                writer.append(String.valueOf(p.getDEX()));
                writer.append(',');
                writer.append(String.valueOf(p.getCON()));
                writer.append(',');
                writer.append(p.getWeapon().getName_w());
                writer.append(',');
                writer.append(p.getWeapon().getDamage());
                writer.append(',');
                writer.append(String.valueOf(p.getWeapon().getBonus()));
                writer.append(',');
                writer.append(p.getAvatar());
                JOptionPane.showMessageDialog(null, "Player " + p.getName() + " saved successfully");
                writer.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                p.setFilename(null);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
