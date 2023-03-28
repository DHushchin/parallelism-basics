package Task_4;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class BallCanvas extends JPanel {
    public static final ArrayList<Ball> balls = new ArrayList<>();
    public static final ArrayList<Hole> holes = new ArrayList<>();

    public void addBall(Ball b){
        balls.add(b);
    }

    public void addHole(Hole h){
        holes.add(h);
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        for (Ball b: balls) {
            b.draw(g2);
        }

        for (Hole h: holes) {
            h.draw(g2);
        }

        balls.removeIf(Ball::isInHole);
    }

}