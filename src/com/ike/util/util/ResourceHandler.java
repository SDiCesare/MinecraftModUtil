package com.ike.util.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author Ike
 * @version 1.0A
 **/
public class ResourceHandler {

    public static File loadResource(String dir, String name) {
        return new File(dir, name);
    }

    public static BufferedImage getImage(String name) {
        try {
            BufferedImage read = ImageIO.read(ResourceHandler.class.getResourceAsStream("src\\" + name));
            return read == null ? ImageIO.read(ResourceHandler.class.getResourceAsStream("src\\background.png")) : read;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
