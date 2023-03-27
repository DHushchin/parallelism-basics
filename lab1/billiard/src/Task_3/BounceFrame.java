package Task_3;

import javax.swing.*;
import java.awt.*;


public class BounceFrame extends JFrame {
    private final BallCanvas canvas;
    public static final int WIDTH = 450;
    public static final int HEIGHT = 350;

    public BounceFrame() {
        this.setSize(WIDTH, HEIGHT);
        this.setTitle("Task_3.Bounce program");
        this.canvas = new BallCanvas();

        System.out.println("In Frame Thread name = "
                + Thread.currentThread().getName());

        Container content = this.getContentPane();
        content.add(this.canvas, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.lightGray);

        JButton addTenButton = new JButton("Add 10");
        JButton addHundredBallsButton = new JButton("Add 100");
        JButton addThousandBallsButton = new JButton("Add 1000");
        JButton stopButton = new JButton("Stop");

        addTenButton.addActionListener(e -> {
            for( int i = 0; i < 9; i++) {
                addBall("blue");
            }
            addBall("red");
        });

        addHundredBallsButton.addActionListener(e -> {
            for( int i = 0; i < 99; i++) {
                addBall("blue");
            }
            addBall("red");
        });

        addThousandBallsButton.addActionListener(e -> {
            for( int i = 0; i < 999; i++) {
                addBall("blue");
            }
            addBall("red");
        });

        stopButton.addActionListener(e -> System.exit(0));

        buttonPanel.add(addTenButton);
        buttonPanel.add(addHundredBallsButton);
        buttonPanel.add(addThousandBallsButton);
        buttonPanel.add(stopButton);

        content.add(buttonPanel, BorderLayout.SOUTH);
    }

    public void addBall(String color) {
        Ball b = new Ball(canvas, color);
        canvas.addBall(b);

        BallThread t = new BallThread(b);
        if (color.equals("red")) {
            t.setPriority(Thread.MAX_PRIORITY);
        } else {
            t.setPriority(Thread.MIN_PRIORITY);
        }
        t.start();

        canvas.repaint();
        System.out.println("Thread name = "
                + Thread.currentThread().getName());
    }

}