import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public class FileOpUtilities {

    public static int writePlayerData(ActionListener listener, String fName, Player p) {
        Thread thread = new Thread(new FileSaveOpThread(listener, fName, p));
        thread.start();
        return 0;
    }

    public static int loadPlayerData(ActionListener listener, String[] parts) {
        Thread thread = new Thread(new FileLoadOpThread(listener, parts));
        thread.start();
        return 0;
    }
}

class FileSaveOpThread implements Runnable {
    private ActionListener listener;
    private String fileName;
    private Player player;

    public FileSaveOpThread(ActionListener listener, String fName, Player p) {
        this.listener = listener;
        this.fileName = fName;
        this.player = p;
    }

    public void writeToFile(Player p, String fileName) {
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
                JOptionPane.showMessageDialog(null, "Player " + p.getName() + " saved successfully using parallel thread");
                writer.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                p.setFilename(null);
            }
        }
    }

    @Override
    public void run() {
        long then = System.nanoTime();
        System.out.println("New Save thread " + Thread.currentThread());

        writeToFile(this.player, this.fileName);

        System.out.printf("Save took %d ms.\n", TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - then));
        listener.actionPerformed(new ActionEvent(this, 0, "Character Saved"));
    }
}

class FileLoadOpThread implements Runnable {
    private ActionListener listener;
    private String[] parts;

    public FileLoadOpThread(ActionListener listener, String[] playerDetails) {
        this.listener = listener;
        this.parts = playerDetails;
    }

    public void loadFromFile() {
        String documentName = "Unnamed.txt";
        boolean playerExist=false;
        for (int i=0; i<GUIController.getPlayerCount(); i++){
            if((GUIController.getPlayersInMemory()[i] != null) && ((GUIController.getPlayersInMemory()[i]).getName().equals(parts[0]))){
                playerExist = true;
                GUIController.setP(null);
            }
        }

        if(!playerExist){
            Weapon w = new Weapon(parts[4], parts[5], Integer.parseInt(parts[6]));
            GUIController.setP( new Player(parts[0], GUIController.hp, GUIController.ac, Integer.parseInt(parts[1]), Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), w, parts[7]));
            GUIController.setFileName(documentName);
            GUIController.setHasFile(true);
            GUIController.setIsSaved(true);
            JOptionPane.showMessageDialog(null, "Parallel Thread: Player " + parts[0] + " loaded successfully");

        } else{
            JOptionPane.showMessageDialog(null, "Parallel Thread: Player " + parts[0] + " already loaded");
        }
    }

    @Override
    public void run() {
        long then = System.nanoTime();
        System.out.println("New file load thread " + Thread.currentThread());

        loadFromFile();

        System.out.printf("Load took %d ms.\n", TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - then));
        listener.actionPerformed(new ActionEvent(this, 0, "Character Loaded"));
    }
}

