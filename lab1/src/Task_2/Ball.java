package Task_2;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;

class Ball {
    private final Component canvas;
    private static final int XSIZE = 20;
    private static final int YSIZE = 20;
    private int x;
    private int y;
    private int dx = 2;
    private int dy = 2;
    private BallThread thread;

    public Ball(Component c){
        this.canvas = c;

        if (Math.random() < 0.5) {
            x = new Random().nextInt(this.canvas.getWidth());
            y = 0;
        } else {
            x = 0;
            y = new Random().nextInt(this.canvas.getHeight());
        }
    }

    public void draw (Graphics2D g2) {
        g2.setColor(Color.darkGray);
        g2.fill(new Ellipse2D.Double(x,y,XSIZE,YSIZE));

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

        if (isInHole()) {
            removeBall(this);
        }

        this.canvas.repaint();
    }

    public boolean isInHole(){
        int x = this.x + XSIZE/2;
        int y = this.y + YSIZE/2;

        for (Hole h: BallCanvas.holes) {
            if (h.contains(x,y)) return true;
        }
        return false;
    }

    public void setThread (BallThread t){
        this.thread = t;
    }

    public void removeBall(Ball b){
        BallCanvas.balls.remove(b);
        this.thread.stopRunning();
        BounceFrame.addCaught();
    }
}