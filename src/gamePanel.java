import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
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
    private Image[] fishImages;

    // Fish positions and directions
    private int[] fishX = {400, 250, 500, 280, 340, 650, 100};
    private int[] fishY = {300, 300, 280, 350, 250, 255, 248};
    private boolean[] movingLeft = {true, true, true, true, true, true, true};

    // Timer for animation
    private Timer timer;

    // Buttons
    private JButton button1, button2, playAgainButton,startButton;

    // Score
    private int score = 10;
    private int coin=0;
    //music
    private Clip clip;
    private boolean gameover=false;
    private boolean gameStarted=false;
    private int fishSpeed=5;
    // Constructor
    public gamePanel() {
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setLayout(null);

        // Load images
        loadImages();
        // Load and play background music
        loadMusic();
        // Set background color
        this.setBackground(Color.black);

        // Create and add buttons
        button1 = new JButton("PULL");
        button2 = new JButton("USE Bait");
        playAgainButton=new JButton("Play Again");
        startButton=new JButton("Start Game");

        button1.setBounds(200, 440, 80, 30);
        button2.setBounds(320, 440, 100, 30);
        playAgainButton.setBounds(250,350,120,50);
        startButton.setBounds(250,350,120,50);
        playAgainButton.setVisible(false);
        startButton.setVisible(true);

        button1.addActionListener(new ButtonClickListener());
        button2.addActionListener(new ButtonClickListener());
        playAgainButton.addActionListener(new ButtonClickListener());
        startButton.addActionListener(new ButtonClickListener());

        this.add(button2);
        this.add(button1);
        this.add(playAgainButton);
        this.add(startButton);

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
            fisherman1 = fisherman1TookPosition;
            fishImages = new Image[NUM_FISH];
            for (int i = 0; i < NUM_FISH; i++) {
                fishImages[i] = new ImageIcon(getClass().getResource("/fish1.png")).getImage();
            }
            fish1mirror = new ImageIcon(getClass().getResource("/fish1mirror.png")).getImage();
        } catch (Exception e) {
            System.out.println("Error loading image: " + e.getMessage());
        }
    }
    // Load and play background music
    private void loadMusic() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResource("/backgroundmusic.wav"));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Error playing background music: " + e.getMessage());
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
        if(gameStarted){
        for (int i = 0; i < NUM_FISH; i++) {
            if (fishImages[i] != null) {
                g2D.drawImage(fishImages[i], fishX[i], fishY[i], 60, 40, this);
            }
        }
        }
        // Draw the score
        g2D.setColor(Color.WHITE);
        g2D.setFont(new Font("Arial", Font.BOLD, 20));
        g2D.drawString("Score: " + score, 10, 20);
        g2D.drawString("Coins: "+coin,PANEL_WIDTH/2-40,20);
        if(gameover)
        {
            g2D.setColor(Color.BLUE);
            g2D.setFont(new Font("Arial",Font.BOLD,60));
            g2D.drawString("GAME OVER",PANEL_HEIGHT/2-100,PANEL_WIDTH/2-100);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(gameStarted) {
            updateFishSpeed();

            for (int i = 0; i < NUM_FISH; i++) {
                if (movingLeft[i]) {
                    fishX[i] -= fishSpeed;
                    if (fishX[i] < getLeftBoundary(fishY[i])) {
                        fishX[i] = getLeftBoundary(fishY[i]);
                        movingLeft[i] = false;
                        fishImages[i] = fish1mirror;
                    }
                } else {
                    fishX[i] += fishSpeed;
                    if (fishX[i] > getRightBoundary(fishY[i])) {
                        fishX[i] = getRightBoundary(fishY[i]);
                        movingLeft[i] = true;
                        fishImages[i] = new ImageIcon(getClass().getResource("/fish1.png")).getImage();
                    }
                }
            }
        }
        repaint();
    }

    private void updateFishSpeed(){
        if(score>=50)
            fishSpeed=12;
        else if(score>=30)
            fishSpeed=10;
        else if(score>=20)
            fishSpeed=7;
        else
            fishSpeed=5;
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
            if(e.getSource()==playAgainButton){
                resetGame();
            }
            else if(e.getSource()==startButton){
                startGame();
            }
           else if (!gameover && gameStarted) {
                if (e.getSource() == button1) {
                    if (fisherman1 == fisherman1TookPosition) {
                        catchingFish();
                    }
                } else if(e.getSource()==button2) {
                    usingBait();
                }
            }
        }
    }
    public void startGame(){
        gameStarted=true;
        startButton.setVisible(false);
        repaint();
    }

    public void catchingFish() {
        boolean checkFishCatching = true;
        for (int i = 4; i < 7; i++) {
            if ((fishX[i] >= 220 && fishX[i] <= 230) && movingLeft[i]) {
                fisherman1 = fisherman1CaughtFish;
                fishX[i] = 650;
                score += 10;
                coin+=1;
                checkFishCatching = false;
            }
        }
        if (checkFishCatching) {
            fisherman1 = fisherman1MissedFish;
        }
        repaint();
    }

    public void usingBait() {
        score -=5;
        if (score <= 0) {
            score = 0;
            gameover = true;
            clip.stop();
            playAgainButton.setVisible(true);
        }
        fisherman1 = fisherman1TookPosition;
        repaint();
    }
    public void resetGame(){
        score=10;
        coin=0;
        fishX=new int[]{400,250,500,280,340,650,100};
        fishY=new int[]{300,300,280,350,250,255,248};
        movingLeft=new boolean[]{true,true,true,true,true,true,true};
        fisherman1=fisherman1TookPosition;
        gameover=false;
        gameStarted=false;
        startButton.setVisible(true);
        playAgainButton.setVisible(false);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        repaint();
    }
}
