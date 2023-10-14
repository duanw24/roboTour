package framework.map;

import framework.Console;

import java.util.Arrays;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Map {
    // SINGLETON TBD
    private String mString;

    private Tile[][] tileMap = new Tile[4][4];

    public Tile[][] getTileMap() {
        return tileMap;
    }

    //fills map inst w empty tiles
    public Map() {
       reset();
        Console.log("Initial framework.map.Map created", msgType.SUCCESS);
    }

    public void setMap(String mString) {
        this.mString=mString;
    }

    public void setTile(int x, int y, Tile t) {
        tileMap[x][y] = t;
    }
    public void reset() {
        readMap("2112:2111:2111:2211.1112:1111:1111:1211.1112:1111:1111:1211.1122:1121:1121:1221");
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

    public void readMap(String s) {
        //splits to rows
        String[] split = s.split("\\.");
        System.out.println(Arrays.toString(split));
        int id=0;
        wallType e0, e1, e2, e3;
        Tile tempTile;
        //iterates over rows
        for(int i=0;i<4;i++) {
            String[] subSplit=split[i].split(":");
            //iterates over columns
            for (int j=0;j<4;j++) {
                e0=process(subSplit[j].charAt(0));
                e1=process(subSplit[j].charAt(1));
                e2=process(subSplit[j].charAt(2));
                e3=process(subSplit[j].charAt(3));
                tempTile = new Tile(new Wall(e0,Direction.NORTH,j,i),new Wall(e1,Direction.EAST,j,i),new Wall(e2,Direction.SOUTH,j,i),new Wall(e3,Direction.WEST,j,i),id,j,i);
                setTile(j,i,tempTile);
                Console.log("New framework.map.Tile created at "+j+":"+i + " from text", msgType.SUCCESS);
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
                return wallType.BOUNDARY;
            case '3':
                return wallType.WOOD;
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
