package PegGameSolver;

public class Move {
    private int x1, x2, y1, y2;

    public Move(int x1, int x2, int y1, int y2){
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }

    public int getStartX(){
        return x1;
    }

    public int getStartY(){
        return y1;
    }

    public int getEndX(){
        return x2;
    }

    public int getEndY(){
        return y2;
    }

}
