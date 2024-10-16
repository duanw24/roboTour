package v1.gui;

import v1.Pathfinder;
import v1.util.guiUtils;

import javax.swing.*;
import java.awt.*;

public class controlPanel extends JPanel {

    public controlPanel(Pathfinder pathfinder) {
        this.setLayout(new FlowLayout(FlowLayout.CENTER,3,30));

        JTextArea jtstartpos = guiUtils.textAreaify(new JTextArea("Enter startpos x,y"));

        JTextArea jtendpos = guiUtils.textAreaify(new JTextArea("Enter endpos x,y"));

        JTextArea jtwalls = guiUtils.textAreaify(new JTextArea("Enter walls id,dir:id,dir..."));

        JTextField sliderInfo = new JTextField("Pick resolution");
        sliderInfo.setPreferredSize(new Dimension(220, 30));

        sliderInfo.setBackground(new Color(0, 45, 120));
        sliderInfo.setForeground(new Color(205, 205, 205));
        sliderInfo.setFont(guiUtils.customFont(14f));
        sliderInfo.setBorder(BorderFactory.createLineBorder(new Color(17, 23, 41), 2));

        JSlider slider = new JSlider(0, 0, 200, 100);
        JButton start = guiUtils.buttonify(new JButton("Run"){
            @Override
            protected void paintComponent(Graphics g) {
                //was used for custom color
                super.paintComponent(g);
            }
        });
        start.setPreferredSize(new Dimension(220, 60));
        start.addActionListener(e -> {
            System.out.println("Run button pressed");
        });



        setSize(400, 400);
        add(jtstartpos);
        add(jtendpos);
        add(jtwalls);

        add(slider);

        add(start);
    }




    @Override
    protected void paintComponent(Graphics g) {
        this.setBackground(new Color(17,23,41));
        super.paintComponent(g);
        //cum
        g.setColor(new Color(0, 255, 145));
        g.fillRect(15,15,370,840);
    }
}
