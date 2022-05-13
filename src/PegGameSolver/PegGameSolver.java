package PegGameSolver;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Solves the peg game through a recursive algorithm
 */
public class PegGameSolver {

    private List<Deque<Move>> solutions = new ArrayList<>(); //Each "solution" is a list of moves, so this list holds them all
    private int numSoln = 0; //the total number of solutions found
    
    private Deque<Move> currentPath = new ArrayDeque<>();

    private PegGame pegGame;

    /**
     * TODO: finish
     * @param size the size of the peg game board to solve for
     */
    public void solve(int size){
        this.pegGame = new PegGame(size);

        pegGame.printBoard();


        for (Move move : pegGame.getAllLegalMoves()) {
            solve(move);
        }

        pegGame.printBoard();

        //After the algorithm has finished (and board returned to initial state)
        System.out.println(numSoln + " solutions were found. The solutions are:");
        if (numSoln == 0) {
            System.out.println("NO SOLUTIONS");
        }
        for (Deque<Move> solution : solutions){
            System.out.println(solution);

            //TODO: temp visual aid---------
            // for (Move step : solution){
            //     pegGame.printBoard();
            //     pegGame.executeMove(step);
            // }
            //------------------------------
        }


    }

    /**
     * Recursive method for solve, above
     */
    private void solve(Move move){
        pegGame.executeMove(move);
        currentPath.push(move);
        System.out.println("Trying move: " + move.toString());

        if (pegGame.getNumPegs() == 1){
            Deque<Move> solution = new ArrayDeque<>();
            solution.addAll(currentPath);
            solutions.add(solution);
            numSoln++;
        }

        List<Move> nextMoves = pegGame.getAllLegalMoves();

        if (!nextMoves.isEmpty()){
            for (Move nextMove : nextMoves){
                solve(nextMove);
            }
        }

        pegGame.undoLastMove();
        currentPath.pop();
        
    }

    public static void main(String[] args) {
        PegGameSolver solver = new PegGameSolver();
        solver.solve(3);
    }
}
