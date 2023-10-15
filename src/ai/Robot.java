package ai;

import java.awt.*;

public class Robot {
    private Point pos;
    private Point target;
    private Point start;

    public Robot(Point target, Point start) {
        this.pos = start;
        this.target = target;
        this.start = start;
    }

    public void setPos(Point p) {
        this.pos=new Point(p);
    }

    public Point getPos() {
        return pos;
    }
}
