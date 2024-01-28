import framework.Robot;
import framework.gui.guiPanel;
import framework.map.Map;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
     
    }

/*
    public static void main(String[] args) {
        */
/*
        0 = empty
        1 = gridline
        2 = wood
        3 = boundary
         *//*


        //String t="1000:0000:0000:0000.0000:0000:0000:0000.0000:0000:0000:0000.0000:0000:0000:0000";
        // String[] m = t.split("\\.");
        //Map.readMap(t);
        Robot r = new Robot(new Point(0, 0), new Point(0, 0));
        Map theMap = new Map(r);

        JFrame frame = new JFrame("Track Editor Alpha 0.1");
        frame.getContentPane().add(new guiPanel(theMap));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setVisible(true);
        //controlPanel cp = new controlPanel(theMap);
    }
*/
}