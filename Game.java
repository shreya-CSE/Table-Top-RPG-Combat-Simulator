import javax.swing.*;
import java.lang.management.ThreadInfo;

public class Game {
    public static void main(String[] args) {
        System.out.println("Thread class: " + ThreadInfo.class);
        System.out.println("Current thread " + Thread.currentThread());
        SwingUtilities.invokeLater(() -> {
            new GUIController();
        });
    }
}
