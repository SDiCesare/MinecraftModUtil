package com.ike.util.util;

import java.io.*;

/**
 * @author Ike
 * @version 1.0A
 **/
public class ModHandler implements Serializable {

    public static ModHandler fromFile(File file) {
        try {
            FileInputStream i = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(i);
            return ((ModHandler) in.readObject());
        } catch (IOException | ClassNotFoundException | ClassCastException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private String name;
    private String modid;
    private String path;
    private String packageName;

    public ModHandler() {
        super();
    }

    public void saveToFile() {
        try {
            File file = new File(this.getPath(), this.getName() + ".mdh");
            FileOutputStream o = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(o);
            out.writeObject(this);
            out.flush();
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String getModid() {
        return modid;
    }

    public void setModid(String modid) {
        this.modid = modid.toLowerCase();
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        String s = super.toString();
        return s.substring(s.lastIndexOf(".") + 1) + "{" +
                "name='" + name + '\'' +
                ", modid='" + modid + '\'' +
                ", path='" + path + '\'' +
                ", packageName='" + packageName + '\'' +
                '}';
    }
}
