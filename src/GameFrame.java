import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame implements ActionListener {
   frontPage panel;
    fishingOnStand panel1;       // First panel
    fishingOnBoat panel2; // Second panel
    JButton button1;
    JButton button2;
    JButton button3;
    JButton startbutton;
    JButton exitbutton1;
    JButton exitbutton2;
    JButton gameoverback1;
    JButton playagainbutton1;
   int[] isgameover;
    GameFrame() {

        panel = new frontPage();
        panel1=new fishingOnStand();
        panel2 = new fishingOnBoat();

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        button1 = panel.getButton1();
        button2=panel.getButton2();
        button3=panel.getButton3();
        startbutton=panel.getStartButton();
        exitbutton1=panel1.getExitbutton();
        exitbutton2=panel2.getExitButton();
        gameoverback1=panel2.getBackButton();
        playagainbutton1=panel2.getPlayAgainButton();
       isgameover=panel2.getisgameover();
        button1.addActionListener(this);
        button2.addActionListener(this);
        button3.addActionListener(this);
        exitbutton1.addActionListener(this);
        exitbutton2.addActionListener(this);
        gameoverback1.addActionListener(this);
        playagainbutton1.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == button1) {
            this.getContentPane().removeAll();
            this.add(panel1);
            this.revalidate();
            this.repaint();
            panel1.requestFocusInWindow();
        }
        if(e.getSource()==button2)
        {
            this.getContentPane().removeAll();
            this.add(panel2);
            this.revalidate();
            this.repaint();
            panel2.requestFocusInWindow();
        }
        if(e.getSource()==button3)
        {
            this.getContentPane().removeAll();
            this.add(panel);
            this.revalidate();
            this.repaint();
            button1.setVisible(false);
           button2.setVisible(false);
           button3.setVisible(false);
           startbutton.setVisible(true);
        }
        if(e.getSource()==exitbutton1){
            this.getContentPane().removeAll();
            this.add(panel);
            this.revalidate();
            this.repaint();
            panel.requestFocusInWindow();
        }
        if(e.getSource()==exitbutton2){
            this.getContentPane().removeAll();
            this.add(panel);
            this.revalidate();
            this.repaint();
            panel.requestFocusInWindow();
        }
        if(e.getSource()==gameoverback1){
            isgameover[0]=0;
            playagainbutton1.setVisible(false);
            gameoverback1.setVisible(false);
            exitbutton2.setVisible(true);
            this.getContentPane().removeAll();
            this.add(panel);
            this.revalidate();
            this.repaint();
            panel.requestFocusInWindow();
        }
        if(e.getSource()==playagainbutton1){
            isgameover[0]=0;
            playagainbutton1.setVisible(false);
            gameoverback1.setVisible(false);
            exitbutton2.setVisible(true);
            this.getContentPane().removeAll();
            this.add(panel2);
            this.revalidate();
            this.repaint();
            panel2.requestFocusInWindow();
        }
    }

    // Main method to run the GameFrame
    public static void main(String[] args) {
        new GameFrame();  // Create an instance of GameFrame
    }
}
