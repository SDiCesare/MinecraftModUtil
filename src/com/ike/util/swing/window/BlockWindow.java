package com.ike.util.swing.window;

import com.ike.util.Main;
import com.ike.util.swing.Frame;
import com.ike.util.swing.TextField;
import com.ike.util.task.BlockTask;

import javax.swing.*;


/**
 * @author Ike
 * @version 1.0A
 **/
public class BlockWindow extends JDialog {

    public BlockWindow(Frame frame) {
        super(frame);
        this.setSize(250, 100);
        this.setLocationRelativeTo(null);
        TextField nameField = new TextField("Name");
        nameField.setBounds(10, 10, 100, 30);
        com.ike.util.swing.Button button = new com.ike.util.swing.Button("Add");
        button.setBounds(130, 10, 70, 30);
        button.addActionListener((e) -> {
            TaskWindow window = new TaskWindow(frame, new BlockTask(Main.frame.getHandler(), nameField.getText()));
            this.dispose();
            window.run();
        });
        this.setLayout(null);
        this.add(nameField);
        this.add(button);
    }
}
