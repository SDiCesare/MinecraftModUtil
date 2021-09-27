package com.ike.util.swing;

import javax.swing.*;

/**
 * @author Ike
 * @version 1.0A
 **/
public class TextField extends JPanel {

    private JLabel name;
    private JTextField textField;

    public TextField(String name) {
        super();
        this.name = new JLabel(name);
        this.name.setBounds(0, 0, 100, 10);
        this.textField = new JTextField();
        this.textField.setBounds(0, 10, 100, 10);
        this.setLayout(null);
        this.add(this.name);
        this.add(this.textField);
        //this.setBackground(Color.RED);
    }

    public void resizeComp(int width, int height) {
        int h = height / 3;
        this.name.setBounds(0, 0, width, h);
        this.textField.setBounds(0, h, width, h * 2);
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        this.resizeComp(width, height);
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        this.resizeComp(width, height);
    }

    public String getText() {
        return this.textField.getText();
    }

    public void setText(String text) {
        this.textField.setText(text);
    }
}
