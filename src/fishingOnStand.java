import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class fishingOnStand extends JPanel implements ActionListener, KeyListener {
    private static final int PANEL_WIDTH = 600;
    private static final int PANEL_HEIGHT = 480;
    private static final int NUM_FISH = 7;

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
    private Clip backgroundMusic;

    private int[] fishX = {400, 250, 500, 280, 340, 650, 100};
    private int[] fishY = {300, 300, 280, 350, 250, 255, 248};
    private int[] birdX = {10, 120, 200, 280, 320, 650, 420};
    private int[] birdY = {30, 10, 30, 40, 20, 5, 20};
    private boolean[] movingLeft = {true, true, true, true, true, true, true};

    private Timer timer;
    private Timer countdownTimer;
    private int score = 0;
    public JButton exitbutton,button1Min,button2Min,button3Min,playagainbutton;
    private int gameTimeRemaining,gameTime=0;

    public fishingOnStand() {

        exitbutton = new JButton("Exit");
        exitbutton.setBounds(PANEL_WIDTH / 2 - 50, PANEL_HEIGHT - 60, 100, 50);
        this.add(exitbutton);
        exitbutton.addActionListener(this);
        exitbutton.setVisible(true);

        playagainbutton = new JButton("Play Again");
        playagainbutton.setBounds(200, 200, 200, 50);
        playagainbutton.addActionListener(this);
        this.add(playagainbutton);
        playagainbutton.setVisible(false);

        button1Min = new JButton("1 Minute play");
        button1Min.setBounds(200, 100, 200, 50);
        button1Min.addActionListener(this);
        this.add(button1Min);
        button1Min.setVisible(true);

        button2Min = new JButton("2 Minutes play");
        button2Min.setBounds(200, 160, 200, 50);
        button2Min.addActionListener(this);
        this.add(button2Min);
        button2Min.setVisible(true);

        button3Min = new JButton("3 Minutes play");
        button3Min.setBounds(200, 220, 200, 50);
        button3Min.addActionListener(this);
        this.add(button3Min);
        button3Min.setVisible(true);


        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setLayout(null);

        loadImages();
        loadMusic();

        this.setBackground(Color.black);

        // Timer for movement
        timer = new Timer(100, this);
        timer.start();

        countdownTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gameTimeRemaining > 0) {
                    gameTimeRemaining--;
                    repaint();
                } else {
                    countdownTimer.stop();
                }
            }
        });
        countdownTimer.start();

        // Enable key events
        setFocusable(true);
        addKeyListener(this);
        requestFocusInWindow();  // Ensure the panel gets focus for key events
    }

    private void loadImages() {
        try {
            backgroundImage = new ImageIcon(getClass().getResource("/frame.png")).getImage();
            fisherman1TookPosition = new ImageIcon(getClass().getResource("/fisherman1tookposition.png")).getImage();
            fisherman1CaughtFish = new ImageIcon(getClass().getResource("/fisherman1catchedfish.png")).getImage();
            fisherman1MissedFish = new ImageIcon(getClass().getResource("/fisherman1missedfish.png")).getImage();
            fisherman1WithoutBait = new ImageIcon(getClass().getResource("/fisherman1withoutbait.png")).getImage();
            bucket = new ImageIcon(getClass().getResource("/bucket.png")).getImage();
            fisherman1 = fisherman1TookPosition;

            fishImages = new Image[NUM_FISH];
            bird = new Image[NUM_FISH];
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
          if(gameTime==0){
              this.setBackground(new Color(40, 200, 230));
              g2D.setFont(new Font("Arial", Font.BOLD, 50));
              g2D.setColor(Color.WHITE);
              return;
          }

          if(gameTime!=0&&gameTimeRemaining==0){
              this.setBackground(new Color(40, 200, 230));
              g2D.setFont(new Font("Arial", Font.BOLD, 50));
              g2D.drawString("Time is Over", PANEL_WIDTH/4, 100);
              g2D.drawString("Score "+score, PANEL_WIDTH/3+10, 150);
              playagainbutton.setVisible(true);
              return;

          }


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

        g2D.setColor(Color.WHITE);
        g2D.setFont(new Font("Arial", Font.BOLD, 20));
        g2D.drawString("Score: " + score, 10, 20);

        int minutes = gameTimeRemaining / 60;
        int seconds = gameTimeRemaining % 60;
        String timeString = String.format("Time: %02d:%02d", minutes, seconds);
        g2D.drawString(timeString, PANEL_WIDTH - 150, 20);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==playagainbutton){
            score=0;
            gameTime=0;
            button1Min.setVisible(true);
            button2Min.setVisible(true);
            button3Min.setVisible(true);
            playagainbutton.setVisible(false);
            fishingOnStand playagain=new fishingOnStand();
        }

        if (e.getSource() == button1Min) {
            button1Min.setVisible(false);
            button2Min.setVisible(false);
            button3Min.setVisible(false);
            gameTime = 1;
            gameTimeRemaining = gameTime*60;
            countdownTimer.start();
        } else if (e.getSource() == button2Min) {
            button1Min.setVisible(false);
            button2Min.setVisible(false);
            button3Min.setVisible(false);
            gameTime = 2;
            gameTimeRemaining = gameTime*60;
            countdownTimer.start();
        } else if (e.getSource() == button3Min) {
            button1Min.setVisible(false);
            button2Min.setVisible(false);
            button3Min.setVisible(false);
            gameTime = 3;
            gameTimeRemaining = gameTime*60;
            countdownTimer.start();
        }


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
    public JButton getExitbutton(){
        return exitbutton;
    }

    private int getRightBoundary(int y) {
        return PANEL_WIDTH;
    }

    private void loadMusic() {
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File("/backgroundmusic.wav"));
            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(audioStream);
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
            backgroundMusic.start();
        } catch (Exception e) {
            System.out.println("Error loading music: " + e.getMessage());
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

    // KeyListener methods
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            catchingFish();
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            usingBait();
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }



}