package PegGameSolver;


public class PegGame {
    public static final int DEFAULT_BOARD_SIZE = 5;


    /*I refer to the indices of the matrix as (x,y) corrdinates: for example, gameBoard[2][1] is the spot marked x below on a 5x5 board:
    1 1 1 1 1
    1 1 x 1 1
    1 1 0 1 1
    1 1 1 1 1
    1 1 1 1 1
    */

    private int[][] gameBoard;
    private int boardSize;



    /**
     * Initializes the game at the default board size
     */
    public PegGame(){
        this.boardSize = DEFAULT_BOARD_SIZE;
        this.gameBoard = new int[boardSize][boardSize];
        initializeGameBoard();
    }

    /**
     * Initializes the game at a given board size
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
    public boolean checkMoveLegality(int x1, int y1, int x2, int y2){

        //checks that the inputs are all in bounds
        if ((x1 >= gameBoard.length || x1 < 0) || (y1 >= gameBoard.length || y1 < 0) || (x2 >= gameBoard.length || x2 < 0) || (y2 >= gameBoard.length || y2 < 0)){
            System.out.println("Move failed: index out of bounds");
            return false;
        }

        //checks that there is a peg in the starting spot, and that the ending spot is empty
        if (gameBoard[x1][y1] != 1 || gameBoard[x2][y2] !=0){
            System.out.println("Move failed: starting and ending hole contents");
            return false;
        }

        //checks that start and end points are one space apart
        if ((y1-y2 == 0 && Math.abs(x1-x2) != 2) || (x1-x2 == 0 && Math.abs(y1-y2) != 2)){
            System.out.println("Move failed: move length incorrect");

            return false;
        }

        //checks that start and end aren't diagonal
        if (Math.abs(x1-x2) + Math.abs(y1-y2) > 2){
            System.out.println("Move failed: end points diagonal");
            return false;
        }

        //checks that there is a peg to jump:
        int midX = (x2-x1)/2 + x1;
        int midY = (y2-y1)/2 + y1;
        if (gameBoard[midX][midY] != 1){
            System.out.println("Move failed: no peg to jump");
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
    public void executeMove(int x1, int y1, int x2, int y2){
        gameBoard[x1][y1] = 0;
        gameBoard[x2][y2] = 1;

        int midX = (x2-x1)/2 + x1;
        int midY = (y2-y1)/2 + y1;

        gameBoard[midX][midY] = 0;
    }

    /**
     * Executes a move on the game board based on that moves starting and ending coordinates
     * NOTE: does not check the legality of the move first (to avoid double-running checkMoveLegality() when using the pegGameSolver class), so you should always
     *      check move legality before using this method on a move
     * @param move
     */
    public void executeMove(Move move){
        executeMove(move.getStartX(), move.getStartY(), move.getEndX(), move.getEndY());
    }

    public static void main(String[] args) {

        //Visual text for execute move
        PegGame pegGame = new PegGame();
        pegGame.printBoard();
        pegGame.executeMove(0,2,2,2);
        pegGame.printBoard();
        pegGame.executeMove(1,0,1,2);
        pegGame.printBoard();
        pegGame.executeMove(1,4,1,1);
        pegGame.printBoard();
        pegGame.executeMove(3,4,1,4);
        pegGame.printBoard();
    }

}