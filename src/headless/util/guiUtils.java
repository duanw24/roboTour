package headless.util;

import headless.ai.Direction;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class guiUtils {

    private static Font customFont = null;
    static {
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, new File("src/resources/fonts/Inter-Bold.ttf")).deriveFont(12f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        //register the font
        ge.registerFont(customFont);
    }

    public static Font customFont(float size) {
        return customFont.deriveFont(size);
    }
    public static Color gradientGen(int value, int max) {
        //normalize
        //log bad
        Color c1 = new Color(96, 239, 255);
        Color c2 = new Color(0, 97, 255);

        //linear gradient between c1 and c2 using maxDist as max
        int r = (int) ((c2.getRed() - c1.getRed()) * ((double) value / max) + c1.getRed());
        int g = (int) ((c2.getGreen() - c1.getGreen()) * ((double) value / max) + c1.getGreen());
        int b = (int) ((c2.getBlue() - c1.getBlue()) * ((double) value / max) + c1.getBlue());
        return new Color(r, g, b);
    }
    public static JTextArea textAreaify(JTextArea textArea) {
        textArea.setPreferredSize(new Dimension(220, 30));

        textArea.setBackground(new Color(0, 28, 75));
        textArea.setForeground(new Color(205, 205, 205));
        textArea.setFont(guiUtils.customFont(14f));
        textArea.setBorder(BorderFactory.createLineBorder(new Color(17, 23, 41), 2));
        return textArea;
    }

    public static JButton buttonify(JButton button) {
        button.setBackground(new Color(0, 255, 145));
        button.setForeground(new Color(205, 205, 205));
        button.setFont(guiUtils.customFont(20f));
        button.setBorder(BorderFactory.createRaisedSoftBevelBorder());
        return button;
    }

    public static Point idToPos(String str) {
        int id=Integer.parseInt(str);
        return new Point(id%4,id/4);
    }

    public static Direction balls(String str) {
        return switch (str) {
            case "NORTH" -> Direction.NORTH;
            case "EAST" -> Direction.EAST;
            case "SOUTH" -> Direction.SOUTH;
            case "WEST" -> Direction.WEST;
            default -> null;
        };
    }



    //public static String summary() {}
}
