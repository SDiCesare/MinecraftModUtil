package com.ike.util.task;

import com.ike.util.util.FileUtil;
import com.ike.util.util.ModHandler;
import com.ike.util.util.ResourceHandler;

import java.io.File;
import java.io.IOException;

/**
 * @author Ike
 * @version 1.0A
 **/
public class ItemTask extends Task {

    private static final String ITEM_OBJECT = "//Item Object\n";
    private static final String ITEM_INIT = "//Items\n";

    private String name;

    public ItemTask(ModHandler handler, String name) {
        super(handler);
        this.name = name;
    }

    @Override
    public int execute() {
        try {
            mkClass();
            mkModel();
            return 1;
        } catch (IOException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    private void mkClass() throws IOException {
        //Item Class
        System.out.println("Creating Item Class");
        String path = this.getHandler().getPath() + "\\java\\" + this.getHandler().getPackageName().replace(".", "\\") + "\\item";
        File classFile = ResourceHandler.loadResource("templates\\item", "class.txt");
        String classText = FileUtil.getTextFromFile(classFile);
        classText = classText.replace("@name", this.name).replace("@package", this.getHandler().getPackageName());
        FileUtil.saveFile(path, this.name + ".java", classText);
        System.out.println("Saved Item Class");
        //Registry Item
        File file = new File(this.getHandler().getPath() + "\\java\\" + this.getHandler().getPackageName().replace(".", "\\") + "\\init\\RegistryHandler.java");
        String registryText = FileUtil.getTextFromFile(file);
        String s = FileUtil.appendAt(registryText, "\tpublic static final RegistryObject<Item> " + getCapsName() + ";\n", registryText.indexOf(ITEM_OBJECT) + ITEM_OBJECT.length());
        s = FileUtil.appendAt(s, "\t\t" + this.getCapsName() + " = ITEMS.register(\"" + this.getCapsName().toLowerCase() + "\", () -> new " + this.name + "(new Item.Properties().tab(" + getHandler().getName() + "Mod.MOD_GROUP)));\n", s.indexOf(ITEM_INIT) + ITEM_INIT.length());
        FileUtil.saveFile(file.getParent(), file.getName(), s);
        System.out.println("Added Item To Registry");
    }

    private void mkModel() throws IOException {
        System.out.println("Creating Item Model");
        String path = this.getHandler().getPath() + "\\resources\\assets\\" + this.getHandler().getModid() + "\\models\\item";
        File modelFile = ResourceHandler.loadResource("templates\\item", "item_model.txt");
        String modelText = FileUtil.getTextFromFile(modelFile);
        modelText = modelText.replace("@modid", this.getHandler().getModid()).replace("@name", this.getCapsName().toLowerCase());
        FileUtil.saveFile(path, this.getCapsName().toLowerCase() + ".json", modelText);
        System.out.println("Saved Item Model");
    }

    private String getCapsName() {
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < this.name.length(); i++) {
            char c = this.name.charAt(i);
            if (c >= 'A' && c <= 'Z' && i != 0) {
                out.append("_");
            }
            out.append(Character.toUpperCase(c));
        }
        return out.toString();
    }

    public String getName() {
        return name;
    }
}
