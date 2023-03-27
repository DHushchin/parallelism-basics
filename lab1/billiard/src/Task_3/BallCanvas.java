package Task_3;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BallCanvas extends JPanel {
    public static final ArrayList<Ball> balls = new ArrayList<>();

    public void addBall(Ball b){
        balls.add(b);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        for (Ball b: balls) {
            b.draw(g2);
        }
    }
}