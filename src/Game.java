import models.Funnel;
import models.Weapon;

import java.awt.*;
import java.awt.geom.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JPanel {

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);

        //Funnel funnel = new Funnel(new Point2D.Double(300, 200), Weapon.getWeapon("halulan"). withFactor(10));
        //funnel.g = g2;
        //Funnel expanded = funnel.expand(40,40);
        //expanded.draw(g2);

        FunnelClassifier.g = g2;
        Boolean out = FunnelClassifier.isFunnelAllowed(new Point2D.Double(300, 400), "halulan", 80, 80);

        System.out.println("out: " + out.toString());


    }
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(750, 750);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Game game = new Game();
        frame.setContentPane(game);

        frame.setVisible(true);
        frame.invalidate();
    }

}