import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class gamePanel extends JPanel implements ActionListener {
    final int panel_Width = 600;
    final int panel_Height = 480;
    Image backgroundImage;
    Image fish1mirror;
    Image[] fishImages;
    int[] fishX = {400, 250, 500, 280, 350, 560};
    int[] fishY = {300, 300, 280, 350, 250, 260};
    boolean[] movingLeft = {true, true, true, true, true, true};
    Timer timer;
    final int numFish = 6;

    gamePanel() {
        this.setPreferredSize(new Dimension(panel_Width, panel_Height));
        try {
            backgroundImage = new ImageIcon(getClass().getResource("/frame.png")).getImage();
            fishImages = new Image[numFish];
            for (int i = 0; i < numFish; i++) {
                fishImages[i] = new ImageIcon(getClass().getResource("/fish1.png")).getImage();
            }
            fish1mirror = new ImageIcon(getClass().getResource("/fish1mirror.png")).getImage();
        } catch (Exception e) {
            System.out.println("Error loading image: " + e.getMessage());
        }
        this.setBackground(Color.black);

        timer = new Timer(100, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        if (backgroundImage != null) {
            g2D.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        }
        for (int i = 0; i < numFish; i++) {
            if (fishImages[i] != null) {
                g2D.drawImage(fishImages[i], fishX[i], fishY[i], 60, 40, this);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < numFish; i++) {
            if (movingLeft[i]) {
                fishX[i] -= 5;
                if (fishX[i] < getLeftBoundary(fishY[i])) {
                    fishX[i] = getLeftBoundary(fishY[i]);
                    movingLeft[i] = false;
                    fishImages[i] = fish1mirror;
                }
            } else {
                fishX[i] += 5;
                if (fishX[i] > getRightBoundary(fishY[i])) {
                    fishX[i] = getRightBoundary(fishY[i]);
                    movingLeft[i] = true;
                    fishImages[i] = new ImageIcon(getClass().getResource("/fish1.png")).getImage();
                }
            }
        }
        repaint();
    }

    private int getLeftBoundary(int y) {
        if (y <= 260) {
            return 160;
        } else if (y <= 300) {
            return 200;
        } else {
            return 260;
        }
    }

    private int getRightBoundary(int y) {
        return panel_Width;
    }
}
