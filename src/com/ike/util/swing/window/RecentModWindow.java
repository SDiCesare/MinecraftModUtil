package com.ike.util.swing.window;

import com.ike.util.swing.Frame;
import com.ike.util.util.IniFile;
import com.ike.util.util.ModHandler;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

/**
 * @author Ike
 * @version 1.0A
 **/
public class RecentModWindow extends JDialog {

    private IniFile.IniProperty property;

    public RecentModWindow(Frame frame) {
        super(frame);
        this.setSize(450, 350);
        this.setLocationRelativeTo(null);
        this.property = frame.getProperties().getPropertyFromName("recent");
        this.setLayout(null);
        ArrayList<String> names = this.property.getNames();
        this.setSize(450, names.size() * 50 + 39);
        for (int i = 0; i < names.size(); i++) {
            String value = this.property.getValue(i);
            ModPanel panel = new ModPanel(i, value);
            panel.setLocation(0, 50 * i);
            this.add(panel);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    private class ModPanel extends JPanel {

        private ModPanel(int index, String path) {
            super();
            this.setSize(434, 50);
            this.setLayout(null);
            JLabel label = new JLabel(path);
            label.setForeground(new Color(200, 200, 200));
            label.setBounds(10, 15, 380, 20);
            label.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    Container parent = RecentModWindow.this.getParent();
                    if (parent instanceof com.ike.util.swing.Frame) {
                        com.ike.util.swing.Frame frame = (com.ike.util.swing.Frame) parent;
                        frame.setHandler(ModHandler.fromFile(new File(path)));
                        RecentModWindow.this.dispose();
                    }
                }
            });
            JLabel delete = new JLabel("X");
            delete.setBounds(400, 20, 10, 15);
            delete.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    System.out.println("Remove " + path);
                    RecentModWindow.this.property.removeValue(index);
                    RecentModWindow.this.dispose();
                }
            });
            this.add(label);
            this.add(delete);
            this.setBackground(new Color(0, 0, 0, 150));
            Border lineBorder = BorderFactory.createLineBorder(Color.BLACK);
            this.setBorder(lineBorder);
            delete.setBorder(lineBorder);
        }

    }

}
