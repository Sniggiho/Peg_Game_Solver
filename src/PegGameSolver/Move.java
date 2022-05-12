package PegGameSolver;

/**
 * Wrapper class for game moves. Holds four coordinates: x and y for the start and end point of the move, respectively
 * NOTE: a move object may contain an invalid move. Use pegGame.checkMoveLegality() to check whether or not this object
 *      reprexents a move that can currently be taken.
 */
public class Move {
    private int x1, x2, y1, y2;

    public Move(int x1, int y1, int x2, int y2){
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

    public String toString(){
        return ("("+x1+","+y1+") -> (" + x2 +"," + y2 + ")");
    }

}
