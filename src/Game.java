import java.awt.*;
import java.awt.geom.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Game extends JPanel {

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.BLACK);

        Funnel funnel = new Funnel(new Point2D.Double(300, 200), 37, 100, 105, 140, 40);
        funnel.g = g2;
        //funnel.draw(g2);

        Funnel expanded = funnel.expand(40,40);
        expanded.draw(g2);
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