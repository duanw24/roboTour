package framework.map;

public class Tile {
    private Wall N;
    private Wall E;
    private Wall S;
    private Wall W;

    private int tileID,x,y;
    private specials special;

    private int xCoord,yCoord;


    public Tile(Wall N, Wall E, Wall S, Wall W, int tileID, int x, int y) {
        //should go top right down left, counterclockwise!!!
        this.N=N;
        this.S=S;
        this.E=E;
        this.W=W;
        this.tileID=tileID;
        this.x=x;
        this.y=y;
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

    @Override
    public String toString() {
        return N.toString()+E.toString()+S.toString()+W.toString();
    }
}
