package framework;

import framework.map.*;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

public class mapUtils {

    private static Map theMap;
    public static void setMap(Map m) {
        theMap=m;
    }

    public static Tile processT(int id, char c, int x, int y) {
        switch (c) {
            case '0' -> {
                return new Tile(tileType.EMPTY, wallType.EMPTY,wallType.EMPTY,wallType.EMPTY,wallType.EMPTY,id);
            }
            case '2' -> {
                return new Tile(tileType.START,wallType.EMPTY,wallType.EMPTY,wallType.EMPTY,wallType.EMPTY,id);
            }
            case '3' -> {
                return new Tile(tileType.TARGET,wallType.EMPTY,wallType.EMPTY,wallType.EMPTY,wallType.EMPTY,id);
            }
            case '1' -> {
                return new Tile(tileType.GATE,wallType.GATE,wallType.GATE,wallType.GATE,wallType.GATE,id);
            }
            default -> {throw new RuntimeException("Unrecognized character " + c);}
        }
    }

    public static Point processC(int i) {
        return new Point(i%4,i/4);
    }

    public static wallType processW(char c) {
        switch (c) {
            case '0' -> {
                return wallType.EMPTY;
            }
            case '1' -> {
                return wallType.GRIDLINE;
            }
            case '2' -> {
                return wallType.BOUNDARY;
            }
            case '3' -> {
                return wallType.WOOD;
            }
            case '4' -> {
                return wallType.GATE;
            }
            default -> {
                try {
                    throw new Exception("Unrecognized character " + c);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static Direction processD(char c) {
        switch (c) {
            case '0' -> {
                return Direction.NORTH;
            }
            case '1' -> {
                return Direction.EAST;
            }
            case '2' -> {
                return Direction.SOUTH;
            }
            case '3' -> {
                return Direction.WEST;
            }
            default -> {
                try {
                    throw new Exception("Unrecognized character " + c);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public static void nextTile(int id) {
        Tile temp=getTile(id);
        switch (temp.getTileType()) {
            case EMPTY -> {
                theMap.setTile(id,new Tile(tileType.TARGET, wallType.EMPTY,wallType.EMPTY,wallType.EMPTY,wallType.EMPTY,id));
            }
            case START -> {
                theMap.setTile(id, new Tile(tileType.GATE,wallType.GATE,wallType.GATE,wallType.GATE,wallType.GATE,id));
            }
            case TARGET -> {
                theMap.setTile(id, new Tile(tileType.START,wallType.EMPTY,wallType.EMPTY,wallType.EMPTY,wallType.EMPTY,id));
            }
            case GATE -> {
                theMap.setTile(id, new Tile(tileType.EMPTY,wallType.EMPTY,wallType.EMPTY,wallType.EMPTY,wallType.EMPTY,id));
            }
            default -> {throw new RuntimeException("you provided wrong tiletype you fucking dumbass");}
        }
    }

    public static Point idToPoint(int id) {
        return new Point(id%4,id/4);
    }

    public static int pointToID(Point p) {
        return p.x+p.y*4;
    }

    private static java.util.List<tileType> tileTypes = Arrays.asList(tileType.EMPTY,tileType.TARGET,tileType.START,tileType.GATE);

    public static tileType getNextTileType(tileType t) {
        int temp=tileTypes.indexOf(t);
        return temp==3?tileType.EMPTY:tileTypes.get(temp+1);
    }

    public static Tile getTile(Point p) {
        return theMap.getTileMap()[p.y][p.x];
    }

    //#TODO THE FUCK IS GOING ON HERE
    // SWITCHED p.y and p.x now shit works half the fucking time
    // i wanna fucking kill myself
    public static Tile getTile(int id) {
        return getTile(idToPoint(id));
    }

    public static void export() {
        try {
            BufferedWriter bfr = new BufferedWriter(new FileWriter(new File("src/resources/maps/"+(System.currentTimeMillis())+".json")));
            String template = "{\n" +
                    "  \"HOW TO USE THIS FILE\": \"For tiles, 0=EMPTY 1=GATE 2=START 3=TARGET. For obstacles, tileID:direction (NESW). For start, tileID:direction (NESW)\",\n" +
                    "  \"tiles\": \"1000.1110.0000.0000\",\n" +
                    "  \"obstacles\": [\n" +
                    "    \"0:0\",\n" +
                    "    \"0:0\",\n" +
                    "    \"0:0\",\n" +
                    "    \"0:0\",\n" +
                    "    \"0:0\",\n" +
                    "    \"0:0\",\n" +
                    "    \"0:0\",\n" +
                    "    \"0:0\"\n" +
                    "  ],\n" +
                    "  \"start\":\"0:0\",\n" +
                    "  \"target\":\"15\",\n" +
                    "  \"target_time\":\"60\"\n" +
                    "}";

            bfr.write(template);
            bfr.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
