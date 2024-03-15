package headless.gui;

import headless.Pathfinder;
import headless.ai.Node;
import headless.util.guiUtils;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class guiFrame extends JFrame {

    private static guiFrame instance;
    private guiFrame() {}
    public static guiFrame getInstance() {
        if(instance == null) {
            instance = new guiFrame();
        }
        return instance;
    }

    private int mx,my;
    @Getter
    private static guiPanel gp;
    private static controlPanel cp;
    private Pathfinder pathfinder;

    //im so fucking done with this shit bruh
    public  JTextArea stats;



    public void init(Pathfinder pathfinder, int mx, int my) {
        this.setLayout(null);
        this.pathfinder = pathfinder;
        //cp=new controlPanel(pathfinder);
        //cp.setLocation(800,400);
        gp=new guiPanel(mx,my, pathfinder,this);


        stats = guiUtils.textAreaify(new JTextArea());
        stats.setEditable(false);
        stats.setLineWrap(true);
        stats.setSize(350,375);
        stats.setLocation(825,25);

        JButton sb = new JButton("Rerun");
        sb.setBackground(new Color(7,27,72));
        sb.setForeground(new Color(205, 205, 205));
        sb.setFont(guiUtils.customFont(20f));
        sb.setBorder(BorderFactory.createRaisedSoftBevelBorder());
        sb.setSize(220, 60);
        sb.setLocation(825,400);
        sb.setOpaque(true);
        sb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("rerun");
                pathfinder.rerun();
            }
        });
        this.add(sb);


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(stats);
        this.add(gp);
        //this.add(cp);
        this.setSize(1200,900);
        this.setVisible(true);
        this.setResizable(false);
        repaint();
    }

    public void paint(Graphics g) {
        setBackground(new Color(17,23,41));
    }



    public void updateCurrent(ArrayList<Node> current) {
        if(current==null) {
            gp.getCurrent().clear();
            gp.repaint();
            return;
        }
        gp.getCurrent().clear();
        gp.getCurrent().addAll(current.stream().map(n -> new Point(n.getX(),n.getY())).toList());
    }

    public void updateGraph(Point p, int i) {
        gp.getMapData()[p.x][p.y]=i;
        gp.repaint();
    }

    public void gpRepaint() {
        gp.repaint();
    }

}
