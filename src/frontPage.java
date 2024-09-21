import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


    public class frontPage  extends JPanel implements ActionListener {
        private static final int PANEL_WIDTH = 600;
        private static final int PANEL_HEIGHT = 480;
        private static final int NUM_FISH = 7;
        private static final int FISH_WIDTH = 60;
        private static final int FISH_HEIGHT = 40;
        private static final int BIRD_WIDTH = 25;
        private static final int BIRD_HEIGHT = 25;
        private int fishermanX = 30;
        private int fishermanY=90;

        private Image backgroundImage;
        private Image backgroundImage1;
        private Image backgroundImage2;
        private Image fishimage;
        private Image fish1mirror;
        private Image fisherman1[] = new Image[6];
        private Image fisherman1TookPosition;
        private Image boat;
        private Image[] bird;
        private Image[] fishImages;
        private Image cocordile;
        private int crocodileX=460;
        private int crocodileY=400;

        private int[] fishX = {400, 250, 500, 280, 340, 650, 100,500};
        private int[] fishY = {300, 300, 280, 350, 250, 255, 248,400};
        private int[] birdX = {10, 120, 200, 280, 320, 650, 420};
        private int[] birdY = {30, 10, 30, 40, 20, 5, 20};
        private boolean[] movingLeft = {true, true, true, true, true, true, true};

        private Timer timer;
        public JButton startButton;
        public JButton button1;
        public JButton button2;

        public JButton button3;
        private boolean isScrolling = false;

        private int score = 0;

        public frontPage() {
            this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
            this.setLayout(null);
            loadImages();
            this.setBackground(Color.black);

            startButton = new JButton("Start");
            button1=new JButton("level1");
            button2=new JButton("level2");
            button3=new JButton("Back");
            startButton.setBounds(PANEL_WIDTH / 2 - 50, PANEL_HEIGHT / 2 - 25, 100, 50);
            button1.setBounds(PANEL_WIDTH / 2 - 50, PANEL_HEIGHT / 2 - 25-60, 100, 50);
            button2.setBounds(PANEL_WIDTH / 2 - 50, PANEL_HEIGHT / 2 - 25, 100, 50);
            button3.setBounds(PANEL_WIDTH / 2 - 50, PANEL_HEIGHT / 2 - 25+60, 100, 50);
            startButton.addActionListener(new ButtonClickListener());
            button1.addActionListener(new ButtonClickListener());
            button2.addActionListener(new ButtonClickListener());
            button3.addActionListener(new ButtonClickListener());
            this.add(startButton);
            this.add(button1);
            this.add(button2);
            this.add(button3);
            button1.setVisible(false);
            button2.setVisible(false);
            button3.setVisible(false);
            timer = new Timer(50, this);  // Start a faster timer for smoother animation
            timer.start();  // Start the timer immediately for animation

            this.setFocusable(true);  // Make JPanel focusable to receive key events
        }

        private void loadImages() {
            try {
                backgroundImage1 = new ImageIcon(getClass().getResource("/frontpage.jpg")).getImage();
                fisherman1TookPosition = new ImageIcon(getClass().getResource("/fisher.png")).getImage();
                cocordile=new ImageIcon(getClass().getResource("/cocordile.png")).getImage();
                fisherman1[0] = fisherman1TookPosition;
                fishimage=new ImageIcon(getClass().getResource("/fish1.png")).getImage();
                fishImages = new Image[NUM_FISH];
                bird = new Image[NUM_FISH];
                for (int i = 0; i < NUM_FISH; i++) {
                    fishImages[i] = fishimage;
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
            if (backgroundImage1 != null) {
                g2D.drawImage(backgroundImage1, 0, 0, PANEL_WIDTH, PANEL_HEIGHT, this);
            }

            if (fisherman1 != null) {
                g2D.drawImage(fisherman1[0], fishermanX, fishermanY, 200, 250, this);
            }
            if(cocordile!=null){
                g2D.drawImage(cocordile, crocodileX, crocodileY, 160, 120, this);
            }

            for (int i = 0; i < NUM_FISH; i++) {
                if (fishImages[i] != null) {
                    g2D.drawImage(fishImages[i], fishX[i], fishY[i], FISH_WIDTH, FISH_HEIGHT, this);
                }
            }
            for (int i = 0; i < NUM_FISH; i++) {
                if (bird[i] != null) {
                    g2D.drawImage(bird[i], birdX[i], birdY[i], BIRD_WIDTH, BIRD_HEIGHT, this);
                }
            }
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
            crocodileX -= 5;
            if (crocodileX < -260) {
                crocodileX = PANEL_WIDTH;
            }

            repaint();
        }

        private int getLeftBoundary(int y) {
            if (y <= 260) {
                return 20;
            } else if (y <= 300) {
                return 100;
            } else {
                return 50;
            }
        }

        private int getRightBoundary(int y) {
            return PANEL_WIDTH;
        }
        public JButton getButton1() {
            return button1;
        }

        public JButton getButton2() {
            return button2;
        }
        public JButton getButton3() {
            return button3;
        }
        public JButton getStartButton() {
            return startButton;
        }


        // ButtonClickListener to start background scrolling
        private class ButtonClickListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == startButton) {
                    startButton.setVisible(false);
                    button1.setVisible(true);
                    button2.setVisible(true);
                    button3.setVisible(true);
                }
            }
        }

        public JButton getbutton1(){
            return button1;
        }




}
