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
     * @param holeX
     * @param holeY
     */
    public void solve(PegGame game){
        this.pegGame = game;
        pegGame.printBoard();

        for (Move move : pegGame.getAllLegalMoves()) {
            solve(move);
        }

        //After the algorithm has finished (and board returned to initial state)
        System.out.println(numSoln + " solutions were found. An example solution is:");
        if (numSoln == 0) {
            System.out.println("NO SOLUTIONS");
        } else{
            System.out.println(solutions.get(0));
        }
    }

    /**
     * Recursive method for solve, above
     */
    private void solve(Move move){
        pegGame.executeMove(move);
        currentPath.addLast(move);

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

        pegGame.undoMove(move);
        currentPath.removeLast();
        
    }

    public static void main(String[] args) {
        PegGameSolver solver = new PegGameSolver();

        PegGame sq = new PegGame(4);

        PegGame tri = new PegGameTriangle(6);
        solver.solve(tri);
    }
}
