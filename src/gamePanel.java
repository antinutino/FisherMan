import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class gamePanel extends JPanel implements ActionListener {
    // Constants
    private static final int PANEL_WIDTH = 600;
    private static final int PANEL_HEIGHT = 480;
    private static final int NUM_FISH = 7;

    // Images
    private Image backgroundImage;
    private Image fish1mirror;
    private Image fisherman1;
    private Image fisherman1TookPosition;
    private Image fisherman1CaughtFish;
    private Image fisherman1MissedFish;
    private Image fisherman1WithoutBait;
    private Image bucket;
    private Image[] bird;
    private Image[] fishImages;

    // Fish positions and directions
    private int[] fishX = {400, 250, 500, 280, 340, 650, 100};
    private int[] fishY = {300, 300, 280, 350, 250, 255, 248};
    private int[] birdX = {10, 120, 200, 280, 320, 650, 420};
    private int[] birdY = {30, 10, 30, 40, 20, 5, 20};
    private boolean[] movingLeft = {true, true, true, true, true, true, true};

    // Timer for animation
    private Timer timer;

    // Buttons
    private JButton button1, button2;

    // Score
    private int score = 0;

    // Constructor
    public gamePanel() {
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setLayout(null);

        // Load images
        loadImages();

        // Set background color
        this.setBackground(Color.black);

        // Create and add buttons
        button1 = new JButton("PULL");
        button2 = new JButton("USE Bait");
        button1.setBounds(200, 440, 80, 30);
        button2.setBounds(320, 440, 100, 30);
        button1.addActionListener(new ButtonClickListener());
        button2.addActionListener(new ButtonClickListener());
        this.add(button2);
        this.add(button1);

        // Start timer for animation
        timer = new Timer(100, this);
        timer.start();
    }

    // Load images method
    private void loadImages() {
        try {
            backgroundImage = new ImageIcon(getClass().getResource("/frame.png")).getImage();
            fisherman1TookPosition = new ImageIcon(getClass().getResource("/fisherman1tookposition.png")).getImage();
            fisherman1CaughtFish = new ImageIcon(getClass().getResource("/fisherman1catchedfish.png")).getImage();
            fisherman1MissedFish = new ImageIcon(getClass().getResource("/fisherman1missedfish.png")).getImage();
            fisherman1WithoutBait = new ImageIcon(getClass().getResource("/fisherman1withoutbait.png")).getImage();
            bucket=new ImageIcon(getClass().getResource("/bucket.png")).getImage();
            fisherman1 = fisherman1TookPosition;
            fishImages = new Image[NUM_FISH];
            bird=new Image[NUM_FISH];
            for (int i = 0; i < NUM_FISH; i++) {
                fishImages[i] = new ImageIcon(getClass().getResource("/fish1.png")).getImage();
            }
            for (int i = 0; i < NUM_FISH; i++) {
                bird[i] = new ImageIcon(getClass().getResource("/bird.png")).getImage();
            }
            fish1mirror = new ImageIcon(getClass().getResource("/fish1mirror.png")).getImage();
        } catch (Exception e) {
            System.out.println("Error loading image: " + e.getMessage());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        if (backgroundImage != null) {
            g2D.drawImage(backgroundImage, 0, 0, this.getWidth(), this.getHeight(), this);
        }
        if (fisherman1 != null) {
            g2D.drawImage(fisherman1, 40, 10, 200, 300, this);
        }
        for (int i = 0; i < NUM_FISH; i++) {
            if (fishImages[i] != null) {
                g2D.drawImage(fishImages[i], fishX[i], fishY[i], 60, 40, this);
            }
        }
        for (int i = 0; i < NUM_FISH; i++) {
            if (bird[i] != null) {
                g2D.drawImage(bird[i], birdX[i], birdY[i], 25, 25, this);
            }
        }
        g2D.drawImage(bucket, -10, 140, 100, 100, this);
        // Draw the score
        g2D.setColor(Color.WHITE);
        g2D.setFont(new Font("Arial", Font.BOLD, 20));
        g2D.drawString("Score: " + score, 10, 20);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < NUM_FISH; i++) {
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

        for (int i = 0; i < NUM_FISH; i++) {
            birdX[i] -= 5;
            if (birdX[i] < 0) {
                birdX[i] = PANEL_WIDTH;
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
        return PANEL_WIDTH;
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == button1) {
                if (fisherman1 == fisherman1TookPosition) {
                    catchingFish();
                }
            } else {
                usingBait();
            }
        }
    }

    public void catchingFish() {
        boolean checkFishCatching = true;
        for (int i = 4; i < 7; i++) {
            if ((fishX[i] >= 220 && fishX[i] <= 230) && movingLeft[i]) {
                fisherman1 = fisherman1CaughtFish;
                fishX[i] = 650;
                score += 10;
                checkFishCatching = false;
            }
        }
        if (checkFishCatching) {
            fisherman1 = fisherman1MissedFish;
        }
        repaint();
    }

    public void usingBait() {
        fisherman1 = fisherman1TookPosition;
        repaint();
    }
}
