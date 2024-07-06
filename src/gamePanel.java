import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class gamePanel extends JPanel implements ActionListener {
    final int panel_Width = 600;
    final int panel_Height = 480;
    Image backgroundImage;
    Image fish1mirror;
    Image fisherman1;
    Image fisherman1tookpositon;
    Image fisherman1catchedfish;
    Image fisherman1missedfish;
    Image fisherman1withoutbait;

    Image[] fishImages;

    int[] fishX = {400, 250, 500, 280, 340, 650,100};
    int[] fishY = {300, 300, 280, 350, 250, 255,248};
    boolean[] movingLeft = {true, true, true, true, true, true,true};
    Timer timer;
    final int numFish = 7;
    JButton button1,button2;

    gamePanel() {
        this.setPreferredSize(new Dimension(panel_Width, panel_Height));
        this.setLayout(null);
        // Use null layout to position components manually
//      JPanel Panel2=new JPanel();
//       Panel2.setLayout(null);
//        Panel2.setBounds(225, 270, 5, 5); // Fixed position and size for nested panel
//        Panel2.setBackground(Color.RED);
//       this.add(Panel2);
        try {
            backgroundImage = new ImageIcon(getClass().getResource("/frame.png")).getImage();
            fisherman1tookpositon = new ImageIcon(getClass().getResource("/fisherman1tookposition.png")).getImage();
            fisherman1catchedfish = new ImageIcon(getClass().getResource("/fisherman1catchedfish.png")).getImage();
            fisherman1missedfish = new ImageIcon(getClass().getResource("/fisherman1missedfish.png")).getImage();
            fisherman1withoutbait = new ImageIcon(getClass().getResource("/fisherman1withoutbait.png")).getImage();
            fisherman1=fisherman1tookpositon;
            fishImages = new Image[numFish];
            for (int i = 0; i < numFish; i++) {
                fishImages[i] = new ImageIcon(getClass().getResource("/fish1.png")).getImage();
            }
            fish1mirror = new ImageIcon(getClass().getResource("/fish1mirror.png")).getImage();
        } catch (Exception e) {
            System.out.println("Error loading image: " + e.getMessage());
        }
        this.setBackground(Color.black);

        // Create and add two button
        button1 = new JButton("PULL");
        button2=new JButton("USE Bait");
        button1.setBounds(200, 440, 80, 30);
        button2.setBounds(320,440,100,30);// Set button position and size
        button1.addActionListener(new ButtonClickListener());
        button2.addActionListener(new ButtonClickListener());
        this.add(button2);
        this.add(button1);

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
        if (fisherman1 != null) {
            g2D.drawImage(fisherman1, 40, 10, 200, 300, this);
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

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Perform action on button click
            if(e.getSource()==button1)
            {  if(fisherman1==fisherman1tookpositon)
                catchingfish();
            }
            else{
                usingbait();
            }
        }
    }

    public void catchingfish() {
        boolean checkfishcatching=true;
        // Toggle fisherman image between fisherman1 and fisherman2
        for(int i=4;i<7;i++)
        {
            if((fishX[i]>=220&&fishX[i]<=230)&&movingLeft[i]) {
                    fisherman1 = fisherman1catchedfish;
                    fishX[i] = 650;
                    checkfishcatching=false;
            }
        }
           if(checkfishcatching)
               fisherman1=fisherman1missedfish;
        repaint();
    }
    public void usingbait(){

         fisherman1=fisherman1tookpositon;
        repaint();
    }

}
