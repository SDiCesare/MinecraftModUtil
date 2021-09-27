package com.ike.util.swing;

import com.ike.util.Main;
import com.ike.util.swing.menu.Menu;
import com.ike.util.swing.window.BlockWindow;
import com.ike.util.swing.window.TaskWindow;
import com.ike.util.task.CreateModTask;
import com.ike.util.util.IniFile;
import com.ike.util.util.ModHandler;
import com.ike.util.util.ResourceHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;

/**
 * @author Ike
 * @version 1.0A
 **/
public class Frame extends JFrame {

    private Container contentPane;
    private ModHandler handler;
    private JLabel modLabel;
    private IniFile properties;

    public Frame() {
        super("Minecraft Util");
        this.setSize(750, 500);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setJMenuBar(new Menu());
        this.initProperties();
        this.initGUI();
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try {
                    Frame.this.properties.save();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    private void initProperties() {
        this.properties = new IniFile(new File("option.ini"));
        if (!this.properties.getFile().exists()) {
            this.properties.addProperty("recent", new IniFile.IniProperty());
            this.properties.addProperty("lastDir", new IniFile.IniProperty());
            try {
                this.properties.save();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void initGUI() {
        this.contentPane = this.getContentPane();
        this.contentPane.setLayout(null);
        this.contentPane.setBackground(Color.BLUE);
        JLabel backgroundLabel = new JLabel("[Load a Mod]");
        backgroundLabel.setIcon(new ImageIcon(ResourceHandler.getImage("background.png")));
        backgroundLabel.setBounds(0, 0, 750, 500);
        modLabel = new JLabel("[Load a Mod]");
        modLabel.setForeground(Color.WHITE);
        modLabel.setFont(modLabel.getFont().deriveFont(15.0f));
        modLabel.setBounds(325, 0, 100, 30);
        modLabel.setHorizontalTextPosition(JLabel.CENTER);
        backgroundLabel.add(modLabel);
        Button initializeMod = new Button("Initialize Mod");
        initializeMod.addActionListener((e) -> {
            if (this.handler == null) {
                System.out.println("Can't initialize a mod without an Handler");
                return;
            }
            System.out.println("Initialize Mod " + this.handler);
            TaskWindow taskWindow = new TaskWindow(this, new CreateModTask(this.handler));
            taskWindow.run();
        });
        initializeMod.setBounds(modLabel.getX() - 200, 50, 200, 30);
        Button addBlock = new Button("Add Block");
        addBlock.addActionListener((e) -> {
            if (this.handler == null) {
                return;
            }
            System.out.println("Adding Block");
            BlockWindow window = new BlockWindow(Main.frame);
            window.setVisible(true);
        });
        int x = initializeMod.getX() + initializeMod.getWidth() + modLabel.getWidth();
        addBlock.setBounds(x, 50, 200, 30);
        //this.contentPane.add(modLabel);
        this.contentPane.add(addBlock);
        this.contentPane.add(initializeMod);
        this.contentPane.add(backgroundLabel);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }

    public ModHandler getHandler() {
        return handler;
    }

    public void setHandler(ModHandler handler) {
        this.handler = handler;
        this.modLabel.setText(this.handler.getName());
        this.updateRecent();
    }

    private void updateRecent() {
        String value = this.handler.getPath() + "\\" + this.handler.getName() + ".mdh";
        IniFile.IniProperty recent = this.properties.getPropertyFromName("recent");
        int numberOfProperties = recent.getNumberOfProperties();
        for (int i = 0; i < numberOfProperties; i++) {
            String[] property = recent.getProperty(i);
            if (property[1].equals(value))
                return;
        }
        if (numberOfProperties < 5) {
            recent.addProperty(String.valueOf(numberOfProperties), value);
        } else {
            for (int i = numberOfProperties - 1; i >= 0; i--) {
                if (i == 0) {
                    recent.setValue(i, value);
                    continue;
                }
                String[] property = recent.getProperty(i - 1);
                recent.setValue(i, property[1]);
            }
        }
    }

    public IniFile getProperties() {
        return properties;
    }
}
