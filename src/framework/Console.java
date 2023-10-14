package framework;

import framework.map.msgType;

public class Console {
    private static Console theConsole = new Console();
    private Console() {

    }

    public static Console getConsole() {
        return theConsole;
    }

    public void log(String s, msgType m) {
        System.out.println(m.toString() + ": " + s);
    }
}
