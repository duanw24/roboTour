package framework.map;

import framework.Console;

import java.util.Arrays;

public class mapUtils {
    /*
            ** DEPRECATED CLASS **
      * IMPLEMENTED BY framework.map.Map *

    public static void readMap(String s) {
        //splits to rows
        String[] split = s.split("\\.");
        System.out.println(Arrays.toString(split));
        int id=0;
        wallType e0, e1, e2, e3;
        //iterates over rows
        for(int i=0;i<4;i++) {
            String[] subSplit=split[i].split(":");
            //iterates over columns
            for (int j=0;j<4;j++) {
                e0=process(subSplit[j].charAt(0));
                e1=process(subSplit[j].charAt(1));
                e2=process(subSplit[j].charAt(2));
                e3=process(subSplit[j].charAt(3));
                Map.getMap().setTile(j,i,new Tile(e0,e1,e2,e3,id,j,i));
                Console.getConsole().log("New framework.map.Tile created at "+j+":"+i, msgType.SUCCESS);
            }
        }

    }

    public static wallType process(char c) {
        switch (c) {
            case '0':
                return wallType.EMPTY;
            case '1':
                return wallType.GRIDLINE;
            case '2':
                return wallType.WOOD;
            case '3':
                return wallType.BOUNDARY;
            case '4':
                return wallType.SPECIAL;
            default:
                try {
                    throw new Exception("Unrecognized character " + c);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
        }
    }
     */
}
