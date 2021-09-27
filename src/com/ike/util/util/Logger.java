package com.ike.util.util;

import javax.swing.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Ike
 * @version 1.0A
 **/
public class Logger {

    private static PrintStream OLD_OUT;
    private static PrintStream OLD_ERR;

    public static void init() {
        reset();
        LoggerStream OUTPUT = new LoggerStream(System.out);
        LoggerStream ERROR = new LoggerStream(System.err);
        OLD_OUT = System.out;
        OLD_ERR = System.err;
        System.setOut(OUTPUT);
        System.setErr(ERROR);
    }

    private static void reset() {
        if (OLD_OUT != null) {
            System.setOut(OLD_OUT);
        }
        if (OLD_ERR != null) {
            System.setErr(OLD_ERR);
        }
    }

    public static void init(JTextArea area) {
        reset();
        LoggerStream OUTPUT = new LoggerStream(System.out, area);
        LoggerStream ERROR = new LoggerStream(System.err, area);
        OLD_OUT = System.out;
        OLD_ERR = System.err;
        System.setOut(OUTPUT);
        System.setErr(ERROR);
    }

    public static PrintStream getOldErr() {
        return OLD_ERR;
    }

    public static PrintStream getOldOut() {
        return OLD_OUT;
    }

    public static class LoggerStream extends PrintStream {

        LoggerStream(OutputStream out) {
            super(new LoggerOutputStream(out, null));
        }

        LoggerStream(OutputStream out, JTextArea area) {
            super(new LoggerOutputStream(out, area));
        }

        private String getTime() {
            SimpleDateFormat formatter = new SimpleDateFormat("[dd/MM/yyyy HH:mm:ss] ");
            Date date = new Date();
            return formatter.format(date);
        }

        @Override
        public void println(int x) {
            this.print(getTime());
            super.println(x);
        }

        @Override
        public void println(char x) {
            this.print(getTime());
            super.println(x);
        }

        @Override
        public void println(long x) {
            this.print(getTime());
            super.println(x);
        }

        @Override
        public void println(float x) {
            this.print(getTime());
            super.println(x);
        }

        @Override
        public void println(char[] x) {
            this.print(getTime());
            super.println(x);
        }

        @Override
        public void println(double x) {
            this.print(getTime());
            super.println(x);
        }

        @Override
        public void println(Object x) {
            this.print(getTime());
            super.println(x);
        }

        @Override
        public void println(String x) {
            this.print(getTime());
            super.println(x);
        }

        @Override
        public void println(boolean x) {
            this.print(getTime());
            super.println(x);
        }
    }

    private static class LoggerOutputStream extends OutputStream {

        private OutputStream stream;
        private JTextArea streamArea;

        LoggerOutputStream(OutputStream stream, JTextArea streamArea) {
            this.stream = stream;
            this.streamArea = streamArea;
        }

        @Override
        public void write(int b) throws IOException {
            stream.write(b);
            if (this.streamArea != null) {
                this.streamArea.append(String.valueOf((char) b));
                this.streamArea.setCaretPosition(this.streamArea.getDocument().getLength());
            }
        }
    }

}
