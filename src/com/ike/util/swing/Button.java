package com.ike.util.swing;

import com.ike.util.util.ResourceHandler;

import javax.swing.*;
import java.awt.*;

/**
 * @author Ike
 * @version 1.0A
 **/
public class Button extends JButton {

    public Button(String text) {
        super(text);
        this.setFocusPainted(false);
        this.setIcon(new ImageIcon(ResourceHandler.getImage("button.png")));
        this.setHorizontalTextPosition(JButton.CENTER);
        this.setForeground(new Color(200, 200, 200));
        this.setFont(this.getFont().deriveFont(15.0f));
    }

}
