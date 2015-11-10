package com.skocken.tictactoeia;

import com.skocken.testing.JULog;
import com.skocken.tictactoeia.core.AI;
import com.skocken.tictactoeia.core.GameBoard;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

public class AITest extends TestCase {

    private static final GameBoard.Player IA = GameBoard.Player.J2;

    private static final GameBoard.Player J1 = GameBoard.Player.J1;

    private static final GameBoard.Player EMPTY = GameBoard.Player.EMPTY;

    private static final GameBoard.Player __ = EMPTY;

    private static final GameBoard.Player TIE = EMPTY;

    private GameBoard mGameBoard;

    private AI mIA;

    @Before
    public void setUp() throws Exception {
        mGameBoard = new GameBoard();
        mIA = new AI(mGameBoard, IA);
    }

    @Test
    public void testModeDebug() {
        GameBoard.Player[][] initBoard =
                {
                        {J1, J1, IA},
                        {IA, J1, __},
                        {IA, __, __}

                };
        mGameBoard.setBoard(initBoard);

        mIA.setDebug(false);
        JULog.clearLastElements();

        mIA.playNext();
        assertNull(JULog.getLastElement());

        mGameBoard.play(J1, 2, 2);

        mIA.setDebug(true);
        JULog.clearLastElements();
        mIA.playNext();
        assertNotNull(JULog.getLastElement());

    }

    @Test
    public void testPlayNextEmpty() throws Exception {
        JULog.logMethodName();

        GameBoard.Player[][] initBoard =
                {
                        {__, __, __},
                        {__, __, __},
                        {__, __, __}

                };
        mGameBoard.setBoard(initBoard);

        JULog.setMute(true);
        mIA.playNext();
        JULog.setMute(false);

        mGameBoard.printBoard();

        assertEquals(IA, mGameBoard.getPlayer(1, 1));
    }


    @Test
    public void testPlayNextEmpty2() throws Exception {
        JULog.logMethodName();

        GameBoard.Player[][] initBoard =
                {
                        {__, __, J1},
                        {J1, IA, __},
                        {__, __, __}

                };
        mGameBoard.setBoard(initBoard);

        mIA.playNext();

        assertEquals(IA, mGameBoard.getPlayer(1, 1));
    }

    @Test
    public void testPlayNextTwoMoveTieOrLoose() throws Exception {
        JULog.logMethodName();

        GameBoard.Player[][] initBoard =
                {
                        {J1, __, __},
                        {IA, J1, J1},
                        {__, __, IA}

                };
        mGameBoard.setBoard(initBoard);

        mIA.playNext();

        assertEquals(IA, mGameBoard.getPlayer(0, 2));
    }

    @Test
    public void testPlayNextOneMovePlusOtherTieOrLoose() throws Exception {
        JULog.logMethodName();

        GameBoard.Player[][] initBoard =
                {
                        {J1, __, __},
                        {IA, J1, J1},
                        {J1, IA, IA}

                };
        mGameBoard.setBoard(initBoard);

        mIA.playNext();

        assertEquals(IA, mGameBoard.getPlayer(2, 0));

        mGameBoard.play(J1, 1, 0);

        assertEquals(__, mGameBoard.getWinner());
    }

    @Test
    public void testPlayNextOneMovePlusOtherTieOrWin() throws Exception {
        JULog.logMethodName();

        GameBoard.Player[][] initBoard =
                {
                        {J1, __, J1},
                        {IA, J1, J1},
                        {__, IA, IA}

                };
        mGameBoard.setBoard(initBoard);

        mIA.playNext();

        assertEquals(IA, mGameBoard.getPlayer(0, 2));
        assertEquals(IA, mGameBoard.getWinner());
    }

    @Test
    public void testPlayNextOneMove() throws Exception {
        JULog.logMethodName();

        GameBoard.Player[][] initBoard =
                {
                        {J1, IA, J1},
                        {IA, IA, J1},
                        {J1, __, __}

                };
        mGameBoard.setBoard(initBoard);

        mIA.playNext();

        assertEquals(IA, mGameBoard.getPlayer(1, 2));
        assertEquals(IA, mGameBoard.getWinner());
    }

    @Test
    public void testPlayNextOneMove2() throws Exception {
        JULog.logMethodName();

        GameBoard.Player[][] initBoard =
                {
                        {J1, IA, IA},
                        {IA, J1, IA},
                        {J1, __, __}

                };
        mGameBoard.setBoard(initBoard);

        mIA.playNext();

        assertEquals(IA, mGameBoard.getPlayer(2, 2));
        assertEquals(IA, mGameBoard.getWinner());
    }


}