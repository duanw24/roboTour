package framework.map;

public class Wall {
    private wallType type;
    private int x,y,x1,y1,xCoord,yCoord;

    public Wall(wallType type, int direction, int xCoord, int yCoord) {
        this.xCoord=x;
        this.yCoord=y;
        this.type = type;
        //0 is north, 1 is east, 2 is south, 3 is west

    }

    public static int getCoords(int direction, int xCoord, int yCoord) {

    }
    public wallType getType() {
        return type;
    }

    public void setType(wallType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type.toString();
    }
}
