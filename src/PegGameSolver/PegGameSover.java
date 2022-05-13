package PegGameSolver;

import java.util.List;

/**
 * Solves the peg game through a recursive algorithm
 */
public class PegGameSover {

    private List<List<Move>> solutions; //Each "solution" is a list of moves, so this list holds them all
    private int numSoln = 0; //the total number of solutions found

    private PegGame pegGame;

    /**
     * TODO: finish
     * @param size the size of the peg game board to solve for
     */
    public void solve(int size){
        this.pegGame = new PegGame(size);

        for (Move move : pegGame.getAllLegalMoves()) {
            solve(move);
        }

        //After the algorithm has finished (and board returned to initial state)
        System.out.println(numSoln + " solutions were found. The solutions are:");
        for (List<Move> solution : solutions){
            System.out.println(solution);
        }

    }

    /**
     * Recursive method for solve, above
     */
    private void solve(Move move){
        pegGame.executeMove(move);

        List<Move> nextMoves = pegGame.getAllLegalMoves();
        if (nextMoves.isEmpty()){
            if (pegGame.getNumPegs() == 1){
                //TODO: incement solutions, add current path to solutions
            }
        }

    }
}
