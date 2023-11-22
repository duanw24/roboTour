package framework;

import framework.util.msgType;

public class Console {
    private static Console theConsole = new Console();
    private Console() {

    }

    public static Console getConsole() {
        return theConsole;
    }

    public static void log(String s, msgType m) {
        System.out.println(m.toString() + ": " + s);
    }

}
