package com.ike.util.swing.window;

import com.ike.util.swing.Button;
import com.ike.util.task.Task;
import com.ike.util.util.Logger;

import javax.swing.*;
import java.awt.*;

/**
 * @author Ike
 * @version 1.0A
 **/
public class TaskWindow extends JWindow {

    private Task task;
    private JTextArea area;

    public TaskWindow(com.ike.util.swing.Frame frame, Task task) {
        super(frame);
        this.task = task;
        this.setSize(450, 350);
        this.setBackground(new Color(0, 0, 0, 0));
        this.setLocationRelativeTo(frame);
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                if (g instanceof Graphics2D) {
                    Color c = new Color(150, 200, 150, 100);
                    Paint p = new GradientPaint(0.0f, 0.0f, c, 0.0f, getHeight(), c, true);
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setPaint(p);
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                }
            }
        };
        setContentPane(panel);
        this.setLayout(null);
        area = new JTextArea();
        area.setEditable(false);
        area.setForeground(new Color(200, 200, 200));
        area.setBackground(Color.BLACK);
        JScrollPane pane = new JScrollPane(area);
        pane.setBounds(10, 10, 410, 250);
        this.add(pane);
        com.ike.util.swing.Button button = new Button("Close");
        button.addActionListener((e) -> this.dispose());
        button.setBounds(175, 270, 100, 30);
        //this.setOpacity(0.55f);
        this.add(button);
    }

    public void run() {
        this.setVisible(true);
        Logger.init(area);
        int execute = this.task.execute();
        System.out.println("Task return code: " + execute);
    }

}
