package v3.util;

import v3.ai.Node;

public class pathUtils {
    public static int nTheta(Node n) {
        if(n.prev == null) return 0;
        return (int) Math.atan((double) (n.getY() - n.prev.getY()) /(n.getX()-n.prev.getX()));
    }

    public static double hD4(Node n) {
        return nTheta(n);
    }
}
