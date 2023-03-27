package Task_3;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Objects;

class Ball {
    private final Component canvas;
    private static final int XSIZE = 20;
    private static final int YSIZE = 20;
    private int x;
    private int y;
    private int dx = 2;
    private int dy = 2;
    private final String color;

    public Ball(Component c, String color){
        this.canvas = c;
        this.x = 0;
        this.y = 0;
        this.color = color;
    }

    public void draw (Graphics2D g2) {
        if (Objects.equals(this.color, "red")) {
            g2.setColor(Color.red);
        } else if (Objects.equals(this.color, "blue")) {
            g2.setColor(Color.blue);
        } else {
            g2.setColor(Color.white);
        }
        g2.fill(new Ellipse2D.Double(x, y, XSIZE, YSIZE));
    }

    public void move() {
        x += dx;
        y += dy;

        if (x < 0) {
            x = 0;
            dx = -dx;
        }

        if (x + XSIZE >= this.canvas.getWidth()) {
            x = this.canvas.getWidth() - XSIZE;
            dx = -dx;
        }

        if (y < 0) {
            y = 0;
            dy = -dy;
        }

        if (y + YSIZE >= this.canvas.getHeight()) {
            y = this.canvas.getHeight() - YSIZE;
            dy = -dy;
        }

        this.canvas.repaint();
    }

}