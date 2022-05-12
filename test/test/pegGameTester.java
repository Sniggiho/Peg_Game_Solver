package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import PegGameSolver.PegGame;


import static org.junit.jupiter.api.Assertions.*;


public class pegGameTester {

    private PegGame pegGame;

    @BeforeEach
    public void setup(){
        pegGame = new PegGame(5);
    }

    @Test
    public void testMoveLegality(){
        assertFalse(pegGame.checkMoveLegality(2,2,0,0)); //no starting peg
        assertFalse(pegGame.checkMoveLegality(10,-1,-3,400)); //index out of bounds 
        assertFalse(pegGame.checkMoveLegality(0,0,2,2)); //end space full
        assertFalse(pegGame.checkMoveLegality(0,0,0,0)); //no movement 
        assertFalse(pegGame.checkMoveLegality(2,2,4,4)); //diagonal
        assertFalse(pegGame.checkMoveLegality(0,0,4,4)); //too far, and full

        
        assertTrue(pegGame.checkMoveLegality(0,2,2,2)); 
        assertTrue(pegGame.checkMoveLegality(2,4,2,2)); 
    }

}
