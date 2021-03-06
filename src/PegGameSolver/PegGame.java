package PegGameSolver;

import java.util.ArrayList;
import java.util.List;

public class PegGame {
    public static final int DEFAULT_BOARD_SIZE = 5;


    /*I refer to the indices of the matrix as (x,y) corrdinates: for example, gameBoard[2][1] is the spot marked x below on a 5x5 board:
    1 1 1 1 1
    1 1 x 1 1
    1 1 0 1 1
    1 1 1 1 1
    1 1 1 1 1
    */
    protected int[][] gameBoard;
    protected int boardSize;

    protected int numPegs;

    /**
     * Initializes the game at the default board size
     */
    public PegGame(){
        this.boardSize = DEFAULT_BOARD_SIZE;
        this.gameBoard = new int[boardSize][boardSize];
        initializeGameBoard();
    }

    /**
     * Initializes the game at a given board size, with an empty space in the center
     * @param boardSize
     */
    public PegGame(int boardSize){
        if (boardSize < 0){
            throw new IllegalArgumentException("boardSize must be positive");
        }
        this.boardSize = boardSize;
        this.gameBoard = new int[boardSize][boardSize];
        initializeGameBoard();
    }

    /**
     * Initializes the game at a given board size, with an empty space at the location specified
     * @param boardSize
     * @param holeX
     * @param holeY
     */
    public PegGame(int boardSize, int holeX, int holeY){
        this(boardSize);
        if (holeX*holeY < 0  || holeX > boardSize -1 || holeY>boardSize-1){
            throw new IllegalArgumentException("invalid hole location");
        }
        gameBoard[boardSize/2][boardSize/2] = 1;
        gameBoard[holeX][holeY] = 0;
    }

    /**
     * Initializes the game board to have a 1 at each index except for the very center, which is set to 0
     * 
     * For example, a three by three board will look like:
     * 1 1 1
     * 1 0 1
     * 1 1 1
     */
    private void initializeGameBoard(){
        for (int i = 0; i < boardSize; i ++){
            for (int j = 0; j < boardSize; j ++){
                if (!(i == (boardSize/2) && j == (boardSize/2))){
                    gameBoard[i][j] = 1;
                }
            }
        }
        numPegs = boardSize*boardSize-1;
    }


    /**
     * Prints out the board in the system out, with 1 for occupied spots and 0 for empty spots
     */
    public void printBoard(){
        String rowAsString = "";

        for (int i = 0; i < boardSize; i ++){
            for (int j = 0; j < boardSize; j ++){
                rowAsString += gameBoard[j][i] + " ";
            }
            
            System.out.println(rowAsString);
            rowAsString = "";
        }

        System.out.println("Board size is " + gameBoard.length);
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

        //checks that start and end aren't diagonal
        if (Math.abs(x1-x2) + Math.abs(y1-y2) > 2){
            return false;
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
     * Checks if a given move is legal
     * @param move
     * @return whether or not the move object constitutes a valid move on the current game board
     */
    public boolean checkMoveLegality(Move move){
        return checkMoveLegality(move.getStartX(), move.getStartY(), move.getEndX(), move.getEndY());
    }

    /**
     * Executes a move on the game board from (x1, y1) to (x2,y2).
     * NOTE: does not check the legality of the move first (to avoid double-running checkMoveLegality() when using the pegGameSolver class), so you should always
     *      check move legality before using this method on a move
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    private void executeMove(int x1, int y1, int x2, int y2, boolean undo){
        gameBoard[x1][y1] = 0;
        gameBoard[x2][y2] = 1;

        int midX = (x2-x1)/2 + x1;
        int midY = (y2-y1)/2 + y1;

        if (!undo){
            gameBoard[midX][midY] = 0;
        } else{
            gameBoard[midX][midY] = 1;
        }

    }

    /**
     * Executes a move on the game board based on that moves starting and ending coordinates
     * NOTE: does not check the legality of the move first (to avoid double-running checkMoveLegality() when using the pegGameSolver class), so you should always
     *      check move legality before using this method on a move
     * @param move
     */
    public void executeMove(Move move){
        executeMove(move.getStartX(), move.getStartY(), move.getEndX(), move.getEndY(), false);
        numPegs --;
    }

    /**
     * Undoes the given move
     * NOTE: in no way checks that the move has already been done, may produce illegal moves
     * @param move the move to undo
     */
    public void undoMove(Move move){
        executeMove(move.getEndX(), move.getEndY(), move.getStartX(), move.getStartY(), true);
        numPegs ++;    
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

    /**
     * Getter method for the number of pegs left on the board
     * @return
     */
    public int getNumPegs(){
        return numPegs;
    }

    //TODO: fix this
    public static void main(String[] args) {
        PegGame pegGame = new PegGame(3, 2, 2);
        pegGame.printBoard();
        System.out.println("num of pegs is " + pegGame.getNumPegs());
        
        Move testMove = new Move(2, 0, 2, 2);

        pegGame.executeMove(testMove);
        pegGame.printBoard();
        System.out.println("num of pegs is " + pegGame.getNumPegs());

        pegGame.undoMove(testMove);
        pegGame.printBoard();
        System.out.println("num of pegs is " + pegGame.getNumPegs());


    }

}