package com.ike.util;

import com.ike.util.swing.Frame;
import com.ike.util.util.Logger;

import java.io.InputStream;

/**
 * @author Ike
 * @version 1.0A
 **/
public class Main {

    public static Frame frame;

    public static void main(String[] args) {
        Logger.init();
        frame = new Frame();
        frame.setVisible(true);
        InputStream resourceAsStream = Main.class.getResourceAsStream("util\\src\\background.png");
        /*
        ModHandler handler = new ModHandler();
        handler.setName("PopIt");
        handler.setModid("popit");
        handler.setPackageName("com.ike.popit");
        handler.setPath("D:\\Test");
        new CreateModTask(handler).execute();
        */
    }

}
