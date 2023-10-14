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
