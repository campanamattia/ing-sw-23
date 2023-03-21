package Model;

public class Coordinates {
    private final int x;
    private final int y;

    public Coordinates(int x, int y){
        this.x=x;
        this.y=y;
    }

    public Coordinates getCoordinates(){
        return this;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
}
