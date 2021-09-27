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
public class CreateModTask extends Task {

    private String javaPath;
    private String srcPath;

    public CreateModTask(ModHandler handler) {
        super(handler);
    }

    public int execute() {
        try {
            if (this.handler == null) {
                return -2;
            }
            this.mkdirs();
            this.mkfiles();
            return 1;
        } catch (IOException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    private void mkdirs() {
        System.out.println("Creating Directories");
        this.javaPath = "java\\" + handler.getPackageName().replace(".", "\\");
        this.srcPath = "resources\\assets\\" + handler.getModid();
        File java = new File(this.handler.getPath(), this.javaPath);
        File src = new File(this.handler.getPath(), this.srcPath);
        boolean javaSuccess = java.mkdirs();
        if (!javaSuccess) {
            System.out.println("Can't create java directories");
        }
        boolean srcSuccess = src.mkdirs();
        if (!srcSuccess) {
            System.out.println("Can't create src directories");
        }
        new File(this.getHandler().getPath(), "resources\\META-INF").mkdirs();
        String[] srcSub = {"block", "item", "init"};
        for (String sub : srcSub) {
            boolean mkdirs = new File(java, sub).mkdirs();
            if (!mkdirs) {
                System.out.println("Can't create java directory: " + sub);
            }
        }
        srcSub = new String[]{"blockstates", "models\\block", "models\\item", "lang", "textures\\block", "textures\\item"};
        for (String sub : srcSub) {
            boolean mkdirs = new File(src, sub).mkdirs();
            if (!mkdirs) {
                System.out.println("Can't create src directory: " + sub);
            }
        }
        System.out.println("Directories successfully created");
    }

    private void mkfiles() throws IOException {
        System.out.println("Creating Main File");
        File mainFile = ResourceHandler.loadResource("templates\\init", "main.txt");
        String mainText = FileUtil.getTextFromFile(mainFile);
        mainText = mainText.replace("@package", this.handler.getPackageName());
        mainText = mainText.replace("@modid", this.handler.getModid());
        mainText = mainText.replace("@name", this.handler.getName());
        FileUtil.saveFile(this.handler.getPath() + "\\" + this.javaPath, this.handler.getName() + "Mod.java", mainText);
        System.out.println("Main file created");

        System.out.println("Creating Registry File");
        File registryFile = ResourceHandler.loadResource("templates\\init", "registry.txt");
        String registryText = FileUtil.getTextFromFile(registryFile);
        registryText = registryText.replace("@package", this.handler.getPackageName());
        registryText = registryText.replace("@modid", this.handler.getModid());
        registryText = registryText.replace("@name", this.handler.getName());
        FileUtil.saveFile(this.handler.getPath() + "\\" + this.javaPath + "\\init", "RegistryHandler.java", registryText);
        System.out.println("Registry file created");

        System.out.println("Creating mods.toml");
        File tomlFile = ResourceHandler.loadResource("templates\\init", "toml.txt");
        String tomlText = FileUtil.getTextFromFile(tomlFile);
        tomlText = tomlText.replace("@modid", this.getHandler().getModid()).replace("@name", this.getHandler().getName());
        FileUtil.saveFile(this.handler.getPath() + "\\resources\\META-INF", "mods.toml", tomlText);
        System.out.println("Toml Created");

        System.out.println("Creating pack.mcdata");
        File packFile = ResourceHandler.loadResource("templates\\init", "pack.txt");
        String packText = FileUtil.getTextFromFile(packFile);
        packText = packText.replace("@name", this.getHandler().getName());
        FileUtil.saveFile(this.handler.getPath() + "\\resources", "pack.mcmeta", packText);
        System.out.println("Pack Created");
    }
}
