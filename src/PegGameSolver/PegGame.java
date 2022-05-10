package src.PegGameSolver;


public class PegGame {
    public static final int boardSize = 5;

    /*I refer to the indices of the matrix as (x,y) corrdinates: for example, gameBoard[2][1] is the spot marked x below on a 5x5 board:
    1 1 1 1 1
    1 1 x 1 1
    1 1 0 1 1
    1 1 1 1 1
    1 1 1 1 1
    */
    private int[][] gameBoard = new int[boardSize][boardSize]; 


    /**
     * Initializes the game
     */
    public PegGame(){
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
    private void printBoard(){
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

        //checks that there is a peg in the starting spot, and that the ending spot is empty
        if (gameBoard[x1][y1] != 1 || gameBoard[x2][y2] !=0){
            return false;
        }

        //checks that the inputs are all in bounds
        if ((x1 >= gameBoard.length || x1 < 0) || (y1 >= gameBoard.length || y1 < 0) || (x2 >= gameBoard.length || x2 < 0) || (y2 >= gameBoard.length || y2 < 0)){
            return false;
        }

        //checks that start and end points are one space apart
        if (Math.abs(x1-x2) != 2 || Math.abs(y1-y2) != 2){
            return false;
        }

        //checks that there is a peg to jump:
        int midX = (x2-x1)/2 + x1;
        int midY = (y2-y1)/2 + y2;
        if (gameBoard[midX][midY] != 1){
            return false;
        }

        return true;
    }

    public static void main(String[] args) {
        PegGame pegGame = new PegGame();
        pegGame.printBoard();
    }

}