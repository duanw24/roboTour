package framework.map;

import ai.Robot;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import framework.Console;
import framework.mapUtils;

import java.awt.*;
import java.awt.geom.Line2D;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Map {
    public static void main(String[] args) {
        Robot r = new Robot(new Point(0, 0), new Point(0, 0));
        Map theMap = new Map(r);
        System.out.println(theMap.toString());
    }

    // #TODO SERIALIZATION + SINGLETON
    private String mString;

    private static Gson gsonc = new Gson()
            .newBuilder()
            .setLenient()
            .create();

    private Tile[][] tileMap = new Tile[4][4];

    public Tile[][] getTileMap() {
        return tileMap;
    }
    Robot theRobot;

    ArrayList<Wall> oList = new ArrayList<Wall>();

    //fills map inst w empty tiles
    public Map(Robot robot) {
        mapUtils.setMap(this);
        this.theRobot=robot;
        reset();
        System.out.println(oList.toString());
        Console.log("Initial framework.map.Map created", msgType.SUCCESS);
    }

    public void setMap(String mString) {
        this.mString=mString;
    }

    public void setTile(int id, Tile t) {
        Point p = mapUtils.idToPoint(id);
        tileMap[p.y][p.x]=t;
    }
    public void reset() {
        readMap(new File("src/resources/maps/emptymap.json"));
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
        try {
            BufferedReader bfr = new BufferedReader(new FileReader(file));
            String temp = bfr.lines().collect(Collectors.joining("\n"));
            System.out.println(temp);
        JsonObject jsO = JsonParser.parseString(temp).getAsJsonObject();
        String[] split = jsO.get("tiles").getAsString().split("\\.");
        int id=0;
        for(int i=0;i<4;i++) {
            for(int j=0;j<4;j++,id++) {
                tileMap[j][i]=mapUtils.processT(id,split[i].charAt(j),j,i);
            }
        }
        ArrayList<Point> obstacles = new ArrayList<>();
        JsonArray jsA = jsO.get("obstacles").getAsJsonArray();
        jsA.iterator().forEachRemaining(t->{
            obstacles.add(new Point(Integer.parseInt(t.getAsString().split(":")[0]),Integer.parseInt(t.getAsString().split(":")[1])));
        });
        obstacles.forEach(t->{
            Point tempP = mapUtils.processC(t.x);
            Wall tempW = (new Wall(wallType.OBSTACLE,mapUtils.processD((char) (t.y+'0')), tempP.x, tempP.y));
            oList.add(tempW);
            tileMap[tempP.y][tempP.x].setWall(mapUtils.processD((char) (t.y+'0')),new Wall(wallType.OBSTACLE,mapUtils.processD((char) (t.y+'0')), tempP.x, tempP.y));
        });
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void changeTileType(int tileID) {
        Point p = mapUtils.idToPoint(tileID);
        System.out.println(p.toString());
        Tile temp = mapUtils.getTile(p.x,p.y);
        setTile(temp.getTileID(),mapUtils.regenerateTile(temp.getTileID(),mapUtils.getNextTileType(temp.getTileType())));
    }






    public boolean moveRobot(Point p) {
        theRobot.setPos(p);
        return true;
    }

    public boolean isLegal(Point end) {
        //iterative raycast check (line intersection)
        if(end.x>=50||end.x<=0||end.y>=50||end.y<=0) {System.out.println("out of bounds dumbass");return false;}
        for(Wall wall : oList) {
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
