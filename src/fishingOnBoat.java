import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.util.Random;

public class fishingOnBoat  extends JPanel implements ActionListener, KeyListener {
    private static final int PANEL_WIDTH = 600;
    private static final int PANEL_HEIGHT = 480;
    private static final int NUM_FISH = 7;
    private static final int FISH_WIDTH = 60;
    private static final int FISH_HEIGHT = 40;
    private static final int BIRD_WIDTH = 25;
    private static final int BIRD_HEIGHT = 25;
    private static final int BOAT_WIDTH = 140;
    private static final int BOAT_HEIGHT = 160;

    private int backgroundImage1X = 0;
    private int backgroundImage2X = PANEL_WIDTH;
    private int fishermanX = 140;
    private int fishermanY=80;
    private int boatX=90;
    private int boatY=120;
    private int stickX=200;
    private int stickY=fishermanY+60;
    private int fishermanImageIndex = 1;
    private Random random = new Random();
    private int crocodileX = 600 + random.nextInt(1201);
    private int crocodileY=160+random.nextInt(60);
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
    private Image stick;
    private Image fishermannew;
    private boolean isSpaceButtonClicked=false;
    private boolean isStickMoveing=false;
    private  int isgameover[]={0};

    private int[] fishX = {400, 250, 500, 280, 340, 650, 100};
    private int[] fishY = {300, 300, 280, 350, 250, 255, 248};
    private int[] birdX = {10, 120, 200, 280, 320, 650, 420};
    private int[] birdY = {30, 10, 30, 40, 20, 5, 20};
    private boolean[] movingLeft = {true, true, true, true, true, true, true};
    private Timer timer;

    private boolean isScrolling = false;
    private int score = 0;
    private JButton exitButton;
    private JButton playAgainButton;
    private JButton backButton;
    public fishingOnBoat() {
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setLayout(null);

        loadImages();
        exitButton = new JButton("Exit");
        exitButton.setBounds((PANEL_WIDTH - 100) / 2, PANEL_HEIGHT - 50, 100, 30);  // Center the button and place it at the bottom
        this.add(exitButton);
        exitButton.setVisible(true);

        playAgainButton = new JButton("Play Again");
        backButton = new JButton("Back");

        playAgainButton.setBounds((PANEL_WIDTH - 200) / 2, PANEL_HEIGHT / 2 - 30, 150, 40);
        backButton.setBounds((PANEL_WIDTH - 200) / 2, PANEL_HEIGHT / 2 + 30, 150, 40);
        this.add(playAgainButton);
        this.add(backButton);

        playAgainButton.setVisible(false);
        backButton.setVisible(false);

        this.setBackground(Color.black);
        timer = new Timer(30, this);  // Start a faster timer for smoother animation
        timer.start();  // Start the timer immediately for animation

        this.setFocusable(true);  // Make JPanel focusable to receive key events
        this.addKeyListener(this); // Add KeyListener to the panel
    }

    private void loadImages() {
        try {
            backgroundImage1 = new ImageIcon(getClass().getResource("/backgroundimage1.png")).getImage();
            backgroundImage2 = new ImageIcon(getClass().getResource("/backgroundimage2.png")).getImage();
            boat = new ImageIcon(getClass().getResource("/boat.png")).getImage();
            cocordile=new ImageIcon(getClass().getResource("/cocordile.png")).getImage();
            stick=new ImageIcon(getClass().getResource("/stick.png")).getImage();
            fishermannew=new ImageIcon(getClass().getResource("/fishermannew.png")).getImage();
            fisherman1[1] = new ImageIcon(getClass().getResource("/fisherup1.png")).getImage();
            fisherman1[2] = new ImageIcon(getClass().getResource("/fisherup2.png")).getImage();
            fisherman1[3] = new ImageIcon(getClass().getResource("/fisherup3.png")).getImage();
            fisherman1[4] = new ImageIcon(getClass().getResource("/fisherup4.png")).getImage();
            fisherman1[5] = new ImageIcon(getClass().getResource("/fisherup5.png")).getImage();
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

           if(isgameover[0]==1) {
               this.setBackground(new Color(40, 200, 230));
               g2D.setFont(new Font("Arial", Font.BOLD, 50));
               g2D.setColor(Color.WHITE);
               g2D.drawString("Game Over", PANEL_WIDTH / 2 - 150, PANEL_HEIGHT / 2 - 100);
               g2D.drawString("Score:"+score, PANEL_WIDTH / 2 - 150, PANEL_HEIGHT / 2 - 50);
               playAgainButton.setVisible(true);
               backButton.setVisible(true);
               exitButton.setVisible(false);
               return;
           }


        g2D.drawImage(backgroundImage1, backgroundImage1X, 0, this.getWidth(), this.getHeight(), this);
        g2D.drawImage(backgroundImage2, backgroundImage2X, 0, this.getWidth(), this.getHeight(), this);


       if(!isStickMoveing){
           if (fisherman1 != null) {
               g2D.drawImage(fisherman1[fishermanImageIndex], fishermanX, fishermanY, 160, 200, this);
           }
       }
       if(isStickMoveing){
           g2D.drawImage(fishermannew, fishermanX, fishermanY, 160, 200, this);
       }
        if(cocordile!=null){
            g2D.drawImage(cocordile, crocodileX, crocodileY, 100, 120, this);
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
        if(isStickMoveing)
        {
            int rotation=-20;
            if(fishermanImageIndex==1)
                rotation=-20;
            if(fishermanImageIndex==2)
                rotation=-10;
            if(fishermanImageIndex==3)
                rotation=0;
            if(fishermanImageIndex==4)
                rotation=10;
            if(fishermanImageIndex==5)
                rotation=20;

            double rotationAngle = Math.toRadians(rotation);
            AffineTransform originalTransform = g2D.getTransform();
            int imageCenterX = stickX + 80 / 2;
            int imageCenterY = stickY + 100 / 2;

            g2D.rotate(rotationAngle, imageCenterX, imageCenterY);
            g2D.drawImage(stick, stickX, stickY, 60, 80, this);
            g2D.setTransform(originalTransform);

        }

        g2D.drawImage(boat, boatX, boatY, BOAT_WIDTH, BOAT_HEIGHT, this);
        g2D.setColor(Color.WHITE);
        g2D.setFont(new Font("Arial", Font.BOLD, 20));
        g2D.drawString("Score: " + score, 10, 20);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
            backgroundImage1X -= 5;
            backgroundImage2X -= 5;
            if (backgroundImage1X <= -PANEL_WIDTH) {
                backgroundImage1X = PANEL_WIDTH;
            }
            if (backgroundImage2X <= -PANEL_WIDTH) {
                backgroundImage2X = PANEL_WIDTH;
            }

        for (int i = 0; i < NUM_FISH; i++) {

                fishImages[i]=fishimage;
                fishX[i]-=5;
                if(fishX[i]<-100)
                    fishX[i]=600;

        }

        for (int i = 0; i < NUM_FISH; i++) {
            birdX[i] -= 5;
            if (birdX[i] < 0) {
                birdX[i] = PANEL_WIDTH;
            }
        }

        crocodileX -= 5;
        if (crocodileX < -160) {
            crocodileX = 600 + random.nextInt(1201);
           crocodileY = 160+random.nextInt(80);
        }


         if(isSpaceButtonClicked){
                if(boatY>-10) {
                    boatY -= 4;
                    fishermanY -= 4;
                }
                else {
                    isSpaceButtonClicked=false;
                }
         }
         if(!isSpaceButtonClicked){
             if(boatY<120)
             {
                 boatY+=4;
                 fishermanY+=4;
             }

         }
        if(isStickMoveing)
        {
            if(stickY<300){
                if(fishermanImageIndex==1){
                    stickY+=5;
                    stickX+=8;
                }
                if(fishermanImageIndex==2)
                {
                    stickY+=5;
                    stickX+=5;
                }
                if(fishermanImageIndex==3){
                    stickY+=6;
                    stickX+=4;
                }
                if(fishermanImageIndex==4){
                    stickY+=6;
                    stickX+=3;
                }
                if(fishermanImageIndex==5){
                    stickY+=6;
                    stickX+=2;
                }
            }
            else {
                stickX=230;
                stickY=fishermanY+60;
                isStickMoveing=false;
            }

        }

        if(isStickMoveing){
            for (int i = 0; i < NUM_FISH; i++) {
                if ((fishX[i]-stickX)<=40 && (fishX[i]-stickX )>=10) {
                    if((fishY[i]-stickY)<=60&&(fishY[i]-stickY)>=40){
                        fishX[i]=600+random.nextInt(801);
                        score+=10;
                    }
                }
            }
        }

        if(crocodileX-boatX<=120&&crocodileX-boatX>=-80)
        {
            if(crocodileY-boatY<75) {
                isgameover[0] = 1;
            }
        }

        repaint();
    }




    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            fishermanImageIndex++;
            if (fishermanImageIndex >= fisherman1.length) {
                fishermanImageIndex = fisherman1.length - 1; // Keep it at the last image
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            fishermanImageIndex--;
            if (fishermanImageIndex < 1) {
                fishermanImageIndex = 1; // Keep it at the first image
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if(boatY>=115)
           isSpaceButtonClicked=true;
        }
        if(e.getKeyCode()==KeyEvent.VK_RIGHT){
           isStickMoveing=true;
        }
        repaint();  // Repaint to show the updated fisherman image
    }
    public void keyReleased(KeyEvent e) {
        // You can leave this empty or add functionality later if needed
    }
    public void keyTyped(KeyEvent e) {
        // You can leave this empty or add functionality later if needed
    }

   public JButton getExitButton(){
        return exitButton;
   }
   public JButton getBackButton(){
        return backButton;
   }
   public JButton getPlayAgainButton(){
        return playAgainButton;
   }

   public int[] getisgameover(){
        return isgameover;
   }



}