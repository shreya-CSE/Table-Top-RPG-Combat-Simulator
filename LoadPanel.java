import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.Scanner;

public class LoadPanel extends JPanel{

    private String documentName = "Unnamed.txt";
    private String fileName = "";

    public LoadPanel(ActionListener listener) throws FileNotFoundException {
        JFileChooser fileChooser = new JFileChooser(".");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Comma Separated Value files", "csv"));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            fileName = fileChooser.getSelectedFile().getPath();
            Path p = Path.of(fileName);
            documentName = p.getFileName().toString();
            System.out.println(fileName);

        }

        try{
            File Csvfile = new File(fileName);
            String[] parts = new String[10];
            if (Csvfile.exists()) {
                try {
                    Scanner input = new Scanner(Csvfile);
                    while (input.hasNextLine()) {
                        String line = input.nextLine();
                        parts = line.split(",");
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else{
                JOptionPane.showMessageDialog(null, "File not found");
                GUIController.setP(null);
                return;
            }

            FileOpUtilities.loadPlayerData(listener, parts);  // new thread

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
    }

}
