package com.ike.util.util;

import java.io.*;

/**
 * @author Ike
 * @version 1.0A
 **/
public class FileUtil {

    public static String getTextFromFile(File f) throws IOException {
        StringBuilder out = new StringBuilder();
        FileReader r = new FileReader(f);
        BufferedReader reader = new BufferedReader(r);
        String ln = reader.readLine();
        while (ln != null) {
            out.append(ln).append("\n");
            ln = reader.readLine();
        }
        return out.substring(0, out.length() - 1);
    }

    public static void saveFile(String path, String name, String content) throws IOException {
        File file = new File(path, name);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter w = new FileWriter(file);
        BufferedWriter writer = new BufferedWriter(w);
        writer.write(content);
        writer.close();
        w.close();
    }

    public static String appendAt(String s1, String s2, int index) {
        StringBuilder out = new StringBuilder();
        out.append(s1, 0, index).append(s2).append(s1.substring(index));
        return out.toString();
    }

}
