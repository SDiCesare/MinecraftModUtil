package com.ike.util.swing.menu;

import com.ike.util.Main;
import com.ike.util.swing.window.ProjectWindow;
import com.ike.util.swing.window.RecentModWindow;
import com.ike.util.util.IniFile;
import com.ike.util.util.ModHandler;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;

/**
 * @author Ike
 * @version 1.0A
 **/
public class Menu extends JMenuBar {

    public Menu() {
        super();
        JMenu projectMenu = new JMenu("File");
        JMenuItem newProject = new JMenuItem("New Project");
        newProject.addActionListener((e) -> {
            ProjectWindow window = new ProjectWindow(Main.frame);
            window.setVisible(true);
        });
        projectMenu.add(newProject);
        JMenuItem openProject = new JMenuItem("Open Project");
        openProject.addActionListener((e) -> {
            IniFile properties = Main.frame.getProperties();
            JFileChooser chooser = new JFileChooser(properties.getPropertyFromName("lastDir").getValue("value"));
            chooser.setFileFilter(new FileNameExtensionFilter("mdh files", "mdh"));
            int status = chooser.showOpenDialog(Main.frame);
            if (status == JFileChooser.APPROVE_OPTION) {
                File selectedFile = chooser.getSelectedFile();
                Main.frame.setHandler(ModHandler.fromFile(selectedFile));
                properties.getPropertyFromName("lastDir").setValueNamed("value", selectedFile.getParent());
            }
        });
        projectMenu.add(openProject);
        JMenuItem recentProject = new JMenuItem("Recent Projects");
        recentProject.addActionListener((e) -> {
            RecentModWindow window = new RecentModWindow(Main.frame);
            window.setVisible(true);
        });
        projectMenu.add(recentProject);
        JMenu editMenu = new JMenu("Edit");
        JMenuItem editHandler = new JMenuItem("Edit Mod");
        editHandler.addActionListener((e) -> {
            if (Main.frame.getHandler() == null) {
                System.out.println("Cant modify a null Handler");
                return;
            }
            ProjectWindow window = new ProjectWindow(Main.frame, Main.frame.getHandler());
            window.setVisible(true);
        });
        editMenu.add(editHandler);
        this.add(projectMenu);
        this.add(editMenu);
    }

}
