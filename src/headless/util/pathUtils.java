package headless.util;

import headless.ai.Graph;
import headless.ai.Node;
import javafx.util.Pair;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class pathUtils {
    /* public static void main(String[] aa) {
     System.out.println(getAngle(
             new Node(100,0,0),
             new Node(99,99,1),
             new Node(0,100,2)));
 }*/
    public static int nTheta(Node n) {
        if (n.prev == null) return 0;
        return (int) Math.atan((double) (n.getY() - n.prev.getY()) / (n.getX() - n.prev.getX()));
    }

    public static double hD4(Node n) {
        return nTheta(n);
    }


    //"lossy" nodelist compression -> polar conversion w 10 degree tolerance
    public static ArrayList<Pair<Double, Double>> polarCoords(Node[] path) {
        ArrayList<Pair<Double, Double>> temp = new ArrayList<>();
        double tr = 0D;
        temp.add(new Pair<>(0d,Math.toDegrees(Math.atan((double) (path[1].getY() - path[0].getY()) / (path[1].getX() - path[0].getX())))));
        for (int i = 1; i < path.length - 1; i++) {
            Node n0 = path[i - 1];
            Node n1 = path[i];
            Node n2 = path[i + 1];
            double theta1 = Math.toDegrees(Math.atan((double) (n1.getY() - n0.getY()) / (n1.getX() - n0.getX())));
            double theta2 = Math.toDegrees(Math.atan((double) (n2.getY() - n1.getY()) / (n2.getX() - n1.getX())));
            double r = Graph.distance(n0, n1);
            double theta = theta2 - theta1;
            System.out.println(Math.round(theta));

            if (Math.abs(theta) > 10) {
                System.out.println("Theta: " + theta + " new tr" + tr);
                temp.add(new Pair<>(tr + r, theta));
                tr = 0;
            } else {
                tr += r;
            }
        }
        temp.add(new Pair<>(tr, 0D));
        return temp;

    }


    //FUCKKKKK YOUUUUU DOT PRODUCT
    public static double getAngle(Node p0, Node p1, Node p2) {
        Point v1 = new Point(p0.getX() - p1.getX(), p0.getY() - p1.getY());
        Point v2 = new Point(p2.getX() - p1.getX(), p2.getY() - p1.getY());

        double dprod = v1.getX() * v2.getX() + v1.getY() * v2.getY();

        double m1 = Point.distance(0, 0, v1.getX(), v1.getY());
        double m2 = Point.distance(0, 0, v2.getX(), v2.getY());

        return Math.toDegrees(Math.acos(dprod / (m1 * m2)));
    }

    public static double speedConv(Node[] path, int time) {
        double dist = path[path.length - 1].getDistance() / 100D;
        System.out.println("Distance: " + dist);
        return dist / time;
    }

    //#TODO: currently turns to degree, should be against lastpos

    //#TODO WHY THE FUCK DO I NEED TO SWITCH THE ORDER?!?1? MAKES NO FUCKING SENSE bruh
    public static String sketchPath(ArrayList<Pair<Double, Double>> pPath) {
        StringBuilder sb = new StringBuilder();
        for (Pair<Double, Double> pCoord : pPath) {
            if(pCoord.getKey()<3)
                sb.append("tTR(").append(Math.round(pCoord.getValue())).append(");\n");
            else {
                //if(pPath.get(i).getKey()<10||pPath.get(i).getValue()==0) {continue;}
                sb.append("tF(").append(Math.round(pCoord.getKey() * 2)).append(");\n");
                sb.append("tTR(").append(Math.round(pCoord.getValue())).append(");\n");
            }
        }

        /*
        pPath.iterator().forEachRemaining(n->{
            if(n.getValue()==0) {return;}
            sb.append("tTR(").append(Math.round(n.getValue())).append(");\n");
            sb.append("tF(").append(Math.round(n.getKey())).append(");\n");
        });*/
        return sb.toString();
    }
}
