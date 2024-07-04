import javax.swing.*;

public class GameFrame extends JFrame {
    gamePanel panel;

    GameFrame() {
        panel = new gamePanel();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
