package framework.map;

import framework.Console;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Map {
    private static Map theMap = new Map();
    private String mString;

    private Tile[][] tileMap;
    private Map() {
        tileMap = new Tile[4][4];
        for(int i=0;i<4;i++) {
            for(int j=0;j<4;j++) {
                tileMap[i][j]=new Tile(new Wall(wallType.EMPTY,90+150*i,90+150*j,));
            }
        }
        mString="";
    }

    public static Map getMap() {
        return theMap;
    }

    public void setMap(String mString) {
        this.mString=mString;
    }

    public void setTile(int x, int y, Tile t) {
        tileMap[x][y] = t;
    }
    public void reset() {
        tileMap = new Tile[4][4];
        mString="";
    }

    @Override
    public String toString() {
        String temp="";
        for(Tile[] tArray : tileMap) {
            temp+="[";
            temp+=Arrays.stream(tArray).map(Tile::toString).collect(Collectors.joining("-"));
            temp+="]\n";
        }
        return temp;
    }

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

}
