package framework.map;

import javax.annotation.processing.SupportedSourceVersion;

public class Wall {
    private wallType type;

    //xLit/yLit are literal coords of the wall on the canvas in pixels, x/y are coords in cm, xCoord/yCoord are coords in tiles
    private int x;
    private int y;
    private int xLit;
    private int yLit;
    private final int xCoord;
    private final int yCoord;

    public int getxLit() {
        return xLit;
    }
    public int getyLit() {
        return yLit;
    }
    private Direction dir;

    public Wall(wallType type, Direction dir, int xCoord, int yCoord) {
        this.xCoord=x;
        this.yCoord=y;
        this.dir=dir;
        this.type = type;
        switch(dir) {
            case NORTH -> {
                this.xLit=90+(150*xCoord);
                this.yLit=90+(150*yCoord);
            }
            case EAST -> {
                this.xLit=240+150*xCoord;
                this.yLit=90+150*yCoord;
            }
            case SOUTH -> {
                this.xLit=90+150*xCoord;
                this.yLit=240+150*yCoord;
            }
            //fuck it west gonna be flipped now
            case WEST -> {
                this.xLit=90+150*xCoord;
                this.yLit=240+150*yCoord;
            }
        }
    }


    //for some reason this method fucking dies every run
    //but runs fine if i put into wall constructor
    public void litCoords() {
        switch(dir) {
            case NORTH -> {
                this.xLit=90+(150*xCoord);
                this.yLit=90+(150*yCoord);
            }
            case EAST -> {
                this.xLit=240+150*xCoord;
                this.yLit=90+150*yCoord;
            }
            case SOUTH -> {
                this.xLit=90+150*xCoord;
                this.yLit=240+150*yCoord;
            }
            //fuck it west gonna be flipped now
            case WEST -> {
                this.xLit=90+150*xCoord;
                this.yLit=240+150*yCoord;
            }
        }
    }
    public wallType getType() {
        return type;
    }

    public void setType(wallType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type.toString()+":x,"+xLit+":y,"+yLit;
    }
}
