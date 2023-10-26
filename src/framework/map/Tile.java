package framework.map;

import framework.mapUtils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Tile {
    private Wall N;
    private Wall E;
    private Wall S;
    private Wall W;

    private int tileID,x,y;
    private specials special;
    private tileType tileType;

    private int xCoord,yCoord;


    public Tile(tileType tileType, wallType N, wallType E, wallType S, wallType W, int tileID) {
        //should go top right down left, counterclockwise!!!
        this.tileType=tileType;
        this.N=new Wall(N,Direction.NORTH,x,y);
        this.S=new Wall(S,Direction.SOUTH,x,y);
        this.E=new Wall(E,Direction.EAST,x,y);
        this.W=new Wall(W,Direction.WEST,x,y);
        this.tileID=tileID;
        Point tempP = mapUtils.idToPoint(tileID);
        this.x=tempP.x;
        this.y=tempP.y;
    }

    public Tile(int tileId, int x, int y) {
        this.tileID=tileId;
        this.x=x;
        this.y=y;
        this.N=new Wall(wallType.EMPTY,Direction.NORTH,x,y);
        this.E=new Wall(wallType.EMPTY,Direction.EAST,x,y);
        this.S=new Wall(wallType.EMPTY,Direction.SOUTH,x,y);
        this.W=new Wall(wallType.EMPTY,Direction.WEST,x,y);
    }

    public Wall getN() {
        return N;
    }
    public Wall getS() {
        return S;
    }
    public Wall getE() {
        return E;
    }
    public Wall getW() {
        return W;
    }
    public tileType getTileType() {
        return tileType;
    }
    public int getX() {return x;}

    public int getY() {return y;}
    public int getTileID() {return tileID;}

    @Override
    public String toString() {
        return this.tileType+":"+N.toString()+E.toString()+S.toString()+W.toString();
    }

    public List getWalls() {
        List<Wall> wList = new ArrayList<Wall>();
        wList.add(N);
        wList.add(E);
        wList.add(S);
        wList.add(W);
        return wList;
    }

    public void setWall(Direction dir,Wall wall) {
        switch(dir) {
            case NORTH -> {
                this.N=wall;
            } case EAST -> {
                this.E=wall;
            } case SOUTH -> {
                this.S=wall;
            } case WEST -> {
                this.W=wall;
            }
        }
    }
}
