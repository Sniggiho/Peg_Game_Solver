package PegGameSolver;

import java.util.ArrayList;

import java.util.List;

public class PegGameTriangle extends PegGame {

    public PegGameTriangle(int boardSize){
        super(boardSize, 0, 0);
        initializeGameBoardTriangle();
    }

    
    /**
     * Initializes the game board in a triangular pattern, putting 2's on all holes above the main diagonal
     * For example, a three by three board will look like:
     * 1 2 2
     * 1 0 2
     * 1 1 1
     */
    private void initializeGameBoardTriangle(){
        for (int i = 0; i < boardSize; i ++){
            for (int j = 0; j < boardSize; j ++){
                if (!(i == 0 && j == 0)){
                    if (i>j){
                        gameBoard[i][j] = 2;
                    } else{
                        gameBoard[i][j] = 1;
                    }
                }
            }
        }
        numPegs = (boardSize*(boardSize+1))/2-1;
    }

    /**
     * Checks if a given pair of cooridnates constitutes a valid move on the current game board
     * @return
     */
    public boolean checkMoveLegality(int x1, int y1, int x2, int y2){ //TODO: this should be private eventually; TODO: remove print statements

        //checks that the inputs are all in bounds
        if ((x1 >= gameBoard.length || x1 < 0) || (y1 >= gameBoard.length || y1 < 0) || (x2 >= gameBoard.length || x2 < 0) || (y2 >= gameBoard.length || y2 < 0)){
            return false;
        }

        //checks that there is a peg in the starting spot, and that the ending spot is empty
        if (gameBoard[x1][y1] != 1 || gameBoard[x2][y2] !=0){
            return false;
        }

        //checks that start and end points are one space apart
        if ((y1-y2 == 0 && Math.abs(x1-x2) != 2) || (x1-x2 == 0 && Math.abs(y1-y2) != 2)){
            return false;
        }

        //checks that start and end aren't diagonal in a bad way
        if (Math.abs(x1-x2) + Math.abs(y1-y2) > 2){
            if ((y2<y1 && x2>x1)||(y2 > y1 && x2 < x1)){
                return false;
            }
        }

        //checks that there is a peg to jump:
        int midX = (x2-x1)/2 + x1;
        int midY = (y2-y1)/2 + y1;
        if (gameBoard[midX][midY] != 1){
            return false;
        }

        return true;
    }

        /**
     * Returns a list of all legal moves from the given (x,y) location, and null if there are none
     * @param x
     * @param y
     * @return a list of all possible moves from the given x and y
     */
    private List<Move> getLegalMoves(int x, int y){
        List<Move> possibleMoves = new ArrayList<Move>();

        if (checkMoveLegality(x,y,x+2,y)) {
            possibleMoves.add(new Move(x,y,x+2,y));
        }
        if (checkMoveLegality(x,y,x,y+2)) {
            possibleMoves.add(new Move(x,y,x,y+2));
        }
        if (checkMoveLegality(x,y,x-2,y)) {
            possibleMoves.add(new Move(x,y,x-2,y));
        }
        if (checkMoveLegality(x,y,x,y-2)) {
            possibleMoves.add(new Move(x,y,x,y-2));
        }
        if (checkMoveLegality(x,y,x+2,y+2)) {
            possibleMoves.add(new Move(x,y,x+2,y+2));
        }
        if (checkMoveLegality(x,y,x-2,y-2)) {
            possibleMoves.add(new Move(x,y,x-2,y-2));
        }

        if (possibleMoves.isEmpty()){
            return null;
        } else{
            return possibleMoves;
        }
    }

    /**
     * TODO: finish
     * @return a list of all possible moves on the current board
     */
    public List<Move> getAllLegalMoves(){
        List<Move> possibleMoves = new ArrayList<Move>();

        for (int i = 0; i < boardSize; i ++){
            for (int j = 0; j < boardSize; j ++){
                if (getLegalMoves(i,j) != null){
                    for (Move move : getLegalMoves(i, j)){
                        possibleMoves.add(move);
                    }
                }
            }
        }

        return possibleMoves;
    }

    public static void main(String[] args) {
        PegGame pegGame = new PegGameTriangle(5);
        pegGame.printBoard();
        System.out.println("num of pegs is " + pegGame.getNumPegs());
        System.out.println(pegGame.checkMoveLegality(2,2,0,0));
    }
}
