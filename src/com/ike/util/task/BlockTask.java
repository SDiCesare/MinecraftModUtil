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
public class BlockTask extends Task {

    private static final String BLOCK_OBJECT = "//Block Object\n";
    private static final String BLOCK_INIT = "//Blocks\n";

    private final String name;

    public BlockTask(ModHandler handler, String name) {
        super(handler);
        this.name = name;
    }

    @Override
    public int execute() {
        try {
            mkBlock();
            mkJson();
        } catch (IOException ex) {
            ex.printStackTrace();
            return -1;
        }
        return 0;
    }

    private void mkBlock() throws IOException {
        File mainFile = ResourceHandler.loadResource("templates\\block", "class.txt");
        String textFromFile = FileUtil.getTextFromFile(mainFile);
        textFromFile = textFromFile.replace("@name", this.name);
        textFromFile = textFromFile.replace("@package", this.getHandler().getPackageName());
        String path = this.getHandler().getPath() + "\\java\\" + this.getHandler().getPackageName().replace(".", "\\") + "\\block";
        FileUtil.saveFile(path, this.name.substring(0, 1).toUpperCase() + this.name.substring(1) + ".java", textFromFile);
        System.out.println("Created Block Class");
        File file = new File(this.getHandler().getPath() + "\\java\\" + this.getHandler().getPackageName().replace(".", "\\") + "\\init\\RegistryHandler.java");
        String registryText = FileUtil.getTextFromFile(file);
        String s = FileUtil.appendAt(registryText, "\tpublic static final RegistryObject<Block> " + getCapsName() + ";\n", registryText.indexOf(BLOCK_OBJECT) + BLOCK_OBJECT.length());
        s = FileUtil.appendAt(s, "\t\t" + getCapsName() + " = registerBlock(\"" + getCapsName().toLowerCase() + "\", () -> new " + this.name + "());\n", s.indexOf(BLOCK_INIT) + BLOCK_INIT.length());
        FileUtil.saveFile(file.getParent(), file.getName(), s);
        System.out.println("Added Block To Registry");
    }

    private void mkJson() throws IOException {
        String path = this.getHandler().getPath() + "\\resources\\assets\\" + this.getHandler().getModid();
        String name = getCapsName().toLowerCase();
        //Item Model
        File item = ResourceHandler.loadResource("templates\\block", "item_model.txt");
        String itemString = FileUtil.getTextFromFile(item);
        itemString = itemString.replace("@modid", this.getHandler().getModid()).replace("@name", name);
        FileUtil.saveFile(path + "\\models\\item", name + ".json", itemString);
        System.out.println("Created ItemBlock Model");
        //Block Model
        File block = ResourceHandler.loadResource("templates\\block", "block_model.txt");
        String blockString = FileUtil.getTextFromFile(block);
        blockString = blockString.replace("@modid", this.getHandler().getModid()).replace("@name", name);
        FileUtil.saveFile(path + "\\models\\block", name + ".json", blockString);
        System.out.println("Created Block Model");
        //Block State
        File state = ResourceHandler.loadResource("templates\\block", "blockstate.txt");
        String stateString = FileUtil.getTextFromFile(state);
        stateString = stateString.replace("@modid", this.getHandler().getModid()).replace("@name", name);
        FileUtil.saveFile(path + "\\blockstates", name + ".json", stateString);
        System.out.println("Created BlockStates");
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
