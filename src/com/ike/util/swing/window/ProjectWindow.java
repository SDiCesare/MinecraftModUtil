package com.ike.util.swing.window;

import com.ike.util.util.ModHandler;
import com.sun.istack.internal.NotNull;

import javax.swing.*;
import java.awt.*;

/**
 * @author Ike
 * @version 1.0A
 **/
public class ProjectWindow extends JDialog {

    private ModHandler resultHandler;

    public ProjectWindow(Frame frame, @NotNull ModHandler handler) {
        this(frame);
        Component[] c = this.getContentPane().getComponents();
        com.ike.util.swing.TextField c0 = ((com.ike.util.swing.TextField) c[0]);
        c0.setText(handler.getPath());
        com.ike.util.swing.TextField c1 = ((com.ike.util.swing.TextField) c[1]);
        c1.setText(handler.getName());
        com.ike.util.swing.TextField c2 = ((com.ike.util.swing.TextField) c[2]);
        c2.setText(handler.getModid());
        com.ike.util.swing.TextField c3 = ((com.ike.util.swing.TextField) c[3]);
        c3.setText(handler.getPackageName());
    }

    public ProjectWindow(Frame frame) {
        super(frame);
        this.setSize(450, 350);
        this.setLocationRelativeTo(null);
        com.ike.util.swing.TextField pathField = new com.ike.util.swing.TextField("Path");
        pathField.setEnabled(true);
        pathField.setBounds(10, 10, 400, 40);
        com.ike.util.swing.TextField nameField = new com.ike.util.swing.TextField("Name");
        nameField.setEnabled(true);
        nameField.setBounds(10, 60, 400, 40);
        com.ike.util.swing.TextField idField = new com.ike.util.swing.TextField("Mod ID");
        idField.setEnabled(true);
        idField.setBounds(10, 120, 400, 40);
        com.ike.util.swing.TextField packageField = new com.ike.util.swing.TextField("Package");
        packageField.setEnabled(true);
        packageField.setBounds(10, 180, 400, 40);
        JButton save = new JButton("Save");
        save.addActionListener((e) -> {
            ModHandler handler = new ModHandler();
            handler.setName(nameField.getText());
            handler.setModid(idField.getText());
            handler.setPackageName(packageField.getText());
            handler.setPath(pathField.getText());
            System.out.println("Saving Project");
            System.out.println(handler);
            this.resultHandler = handler;
            this.dispose();
        });
        save.setBounds(10, 250, 100, 30);
        JButton exit = new JButton("Close");
        exit.addActionListener((e) -> {
            System.out.println("Delete New Project Action");
            this.resultHandler = null;
            this.dispose();
        });
        exit.setBounds(130, 250, 100, 30);
        this.setLayout(null);
        this.add(pathField);
        this.add(nameField);
        this.add(idField);
        this.add(packageField);
        this.add(save);
        this.add(exit);
    }

    @Override
    public void dispose() {
        super.dispose();
        if (this.resultHandler == null)
            return;
        Container parent = this.getParent();
        if (parent instanceof com.ike.util.swing.Frame) {
            com.ike.util.swing.Frame frame = (com.ike.util.swing.Frame) parent;
            frame.setHandler(this.resultHandler);
            this.resultHandler.saveToFile();
        }
    }
}
