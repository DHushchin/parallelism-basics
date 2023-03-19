package Task_2;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;

public class Hole {
    public final int XSIZE = 25;
    public final int YSIZE = 25;
    public int x = 0;
    public int y = 0;

    public Hole(Component c){
        // add hole randomly on any side of edge of the canvas
        switch (new Random().nextInt(4)) {
            case 0:
                x = 0;
                y = new Random().nextInt(c.getHeight());
                break;
            case 1:
                x = new Random().nextInt(c.getWidth());
                y = 0;
                break;
            case 2:
                x = c.getWidth() - XSIZE;
                y = new Random().nextInt(c.getHeight());
                break;
            case 3:
                x = new Random().nextInt(c.getWidth());
                y = c.getHeight() - YSIZE;
                break;
        }
    }

    public void draw (Graphics2D g2){
        g2.setColor(Color.black);
        g2.fill(new Ellipse2D.Double(x,y,XSIZE,YSIZE));
    }

    public boolean contains(int x, int y){
        return (x >= this.x) && (x < (this.x + this.XSIZE)) && (y >= this.y) && (y < (this.y + this.YSIZE));
    }
}