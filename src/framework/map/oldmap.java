/*
package framework.map;

import ai.Robot;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import framework.Console;

import java.awt.*;
import java.awt.geom.Line2D;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Map {
    // #TODO SERIALIZATION + SINGLETON
    private String mString;

    private static Gson gsonc = new Gson()
            .newBuilder()
            .setPrettyPrinting()
            .setLenient()
            .create();

    private Tile[][] tileMap = new Tile[4][4];

    public Tile[][] getTileMap() {
        return tileMap;
    }
    Robot theRobot;

    ArrayList<Wall> wList = new ArrayList<Wall>();

    //fills map inst w empty tiles
    public Map(File mapFile, Robot robot) {
        this.theRobot=robot;
       reset();
        Arrays.stream(tileMap).forEach(tArray -> Arrays.stream(tArray).forEach(t -> wList.addAll(t.getWalls())));
        System.out.println(wList.toString());
        Console.log("Initial framework.map.Map created", msgType.SUCCESS);
    }

    public void setMap(String mString) {
        this.mString=mString;
    }

    public void setTile(int x, int y, Tile t) {
        tileMap[x][y] = t;
    }
    public void reset() {
        readMap("2112:2111:2111:2211.1112:1111:1111:1211.1112:1111:1111:1211.1122:1121:1121:1221-0000000000000000");
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

    public void readMap(File file) {
        //0000.0000.0000.0000
        //obstacles are 1.5in x 3.5in x 40.64 CM
        //splits to rows
        JsonObject jsO = JsonParser.parseString(file.toString()).getAsJsonObject();


    }

    public void readMap(String s) {
        //0000.0000.0000.0000
        //obstacles are 1.5in x 3.5in x 40.64 CM
        //splits to rows

        //#TODO Add tileTypes
        String tiles = s.split("-")[1];

        s=s.split("-")[0];
        String[] split = s.split("\\.");
        System.out.println(Arrays.toString(split));
        wallType e0, e1, e2, e3;
        Tile tempTile;
        int id=0;
        //iterates over rows
        for(int i=0;i<4;i++) {
            String[] subSplit=split[i].split(":");
            //iterates over columns
            for (int j=0;j<4;j++,id++) {
                e0=processW(subSplit[j].charAt(0));
                e1=processW(subSplit[j].charAt(1));
                e2=processW(subSplit[j].charAt(2));
                e3=processW(subSplit[j].charAt(3));
                tempTile = new Tile(
                        //#TODO
                        processT(tiles.charAt(id))
                        ,new Wall(e0,Direction.NORTH,j,i),new Wall(e1,Direction.EAST,j,i),new Wall(e2,Direction.SOUTH,j,i),new Wall(e3,Direction.WEST,j,i),id,j,i);
                setTile(j,i,tempTile);
                Console.log("New framework.map.Tile created at "+j+":"+i + " from text", msgType.SUCCESS);
            }
        }

    }

    public static tileType processT(char c) {
        switch (c) {
            case '0' -> {
                return tileType.EMPTY;
            }
            case '2' -> {
                return tileType.START;
            }
            case '3' -> {
                return tileType.TARGET;
            }
            case '1' -> {
                return tileType.GATE;
            }
            default -> {throw new RuntimeException("Unrecognized character " + c);}
        }
    }

    public static wallType processW(char c) {
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
                return wallType.GATE;
            default:
                try {
                    throw new Exception("Unrecognized character " + c);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
        }
    }

    public boolean moveRobot(Point p) {
        theRobot.setPos(p);
        return true;
    }

    public boolean isLegal(Point end) {
        //iterative raycast check (line intersection)
        for(Wall wall : wList) {
            if(wall.getType().equals(wallType.OBSTACLE)) {
                Line2D l1 = new Line2D.Float(theRobot.getPos(),end);
                Line2D l2;
                if(wall.getDir().equals(Direction.NORTH) || wall.getDir().equals(Direction.SOUTH)) {
                    l2 = new Line2D.Float(new Point(wall.getxLit(),wall.getyLit()),new Point(wall.getxLit()+150,wall.getyLit()));
                }else if(wall.getDir().equals(Direction.EAST) || wall.getDir().equals(Direction.WEST)) {
                    l2 = new Line2D.Float(new Point(wall.getxLit(),wall.getyLit()),new Point(wall.getxLit(),wall.getyLit()+150));
                } else {
                    throw new RuntimeException("Unrecognized direction");
                }
                if(l1.intersectsLine(l2)) {return false;}
                }
            }
            return true;
        }
}
*/
