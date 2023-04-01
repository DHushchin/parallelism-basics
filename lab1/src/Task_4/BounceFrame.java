package Task_4;

import javax.swing.*;
import java.awt.*;

public class BounceFrame extends JFrame {
    private final BallCanvas canvas;
    public static final int WIDTH = 450;
    public static final int HEIGHT = 350;
    private static int caughtBalls = 0;
    private static final JLabel caughtLabel = new JLabel("Caught: 0");
    private static BallThread prevThread = null;

    public BounceFrame() {
        this.setSize(WIDTH, HEIGHT);
        this.setTitle("Task_4.Bounce program");
        this.canvas = new BallCanvas();

        System.out.println("In Frame Thread name = "
                + Thread.currentThread().getName());

        Container content = this.getContentPane();
        content.add(this.canvas, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.lightGray);

        JButton addHoleButton = new JButton("Add hole");
        JButton addBallButton = new JButton("Add 1");
        JButton stopButton = new JButton("Stop");

        addHoleButton.addActionListener(e -> {
            Hole h = new Hole(canvas);
            canvas.addHole(h);
            canvas.repaint();
        });

        addBallButton.addActionListener(e -> addBall());
        stopButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(addHoleButton);
        buttonPanel.add(addBallButton);
        buttonPanel.add(stopButton);
        buttonPanel.add(caughtLabel);

        content.add(buttonPanel, BorderLayout.SOUTH);
    }

    public void addBall() {
        Ball b = new Ball(canvas);
        canvas.addBall(b);

        BallThread t;
        if (prevThread == null) {
            t = new BallThread(b, null);
        } else {
            t = new BallThread(b, prevThread);
        }

        t.start();
        b.setThread(t);
        prevThread = t;

        canvas.repaint();
        System.out.println("Thread name = "
                + t.getName());
    }

    public static synchronized void addCaught() {
        caughtBalls++;
        caughtLabel.setText("Caught: " + caughtBalls);
    }

}