package com.skocken.tictactoeia;

import com.skocken.testing.JULog;
import com.skocken.tictactoeia.core.GameBoard;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import java.util.List;


public class GameBoardTest extends TestCase {

    private static final GameBoard.Player J1 = GameBoard.Player.J1;

    private static final GameBoard.Player J2 = GameBoard.Player.J2;

    private static final GameBoard.Player __ = GameBoard.Player.EMPTY;

    private GameBoard mSubject;

    @Before
    public void setUp() throws Exception {
        mSubject = new GameBoard();
    }

    @Test
    public void testShouldPrintDashBoardIfDebugSetBoard() {
        JULog.clearLastElements();

        mSubject.setDebug(false);
        GameBoard.Player[][] initBoard =
                {
                        {__, __, J2},
                        {__, J1, __},
                        {__, __, __}

                };
        mSubject.setBoard(initBoard);

        assertNull(JULog.getLastElement());

        mSubject.setDebug(true);
        mSubject.setBoard(initBoard);

        assertNotNull(JULog.getLastElement());
    }

    @Test
    public void testShouldPrintDashBoardIfDebugPlay() {
        GameBoard.Player[][] initBoard =
                {
                        {__, __, __},
                        {__, __, __},
                        {__, __, __}

                };
        mSubject.setBoard(initBoard);

        JULog.clearLastElements();
        mSubject.setDebug(false);

        mSubject.play(GameBoard.Player.J1, 0, 0);

        assertNull(JULog.getLastElement());

        mSubject.setDebug(true);

        mSubject.play(GameBoard.Player.J2, 1, 1);

        assertNotNull(JULog.getLastElement());
    }

    @Test
    public void testShouldPrintBoard() {
        GameBoard.Player[][] initBoard =
                {
                        {__, __, J2},
                        {__, J1, __},
                        {__, __, __}

                };

        String[] messagesExpected = {
                "-------",
                "|   O |",
                "|  X  |",
                "|     |",
                "-------"
        };

        mSubject.setBoard(initBoard);
        mSubject.printBoard();
        List<JULog.Element> lastElements = JULog.getLastElements(messagesExpected.length);
        assertNotNull(lastElements);
        assertEquals(messagesExpected.length, lastElements.size());

        for (int i = 0; i < messagesExpected.length; i++) {
            assertEquals(messagesExpected[i], lastElements.get(i).msg);
        }
    }

    @Test
    public void testPlayNull() throws Exception {
        assertNull(mSubject.play(null));
    }

    @Test
    public void testPlayWithAWinner() throws Exception {
        GameBoard.Player[][] initBoard =
                {
                        {__, J1, J2},
                        {__, J1, J2},
                        {__, J1, __}

                };
        mSubject.setBoard(initBoard);
        assertNull(mSubject.play(new GameBoard.Play(GameBoard.Player.J2, 0, 0)));
    }

    @Test
    public void testSetPlayer() throws Exception {
        assertEquals(GameBoard.Player.EMPTY, mSubject.getPlayer(0, 0));
        mSubject.play(GameBoard.Player.J1, 0, 0);
        assertEquals(GameBoard.Player.J1, mSubject.getPlayer(0, 0));
    }

    @Test
    public void testGetNbPlayLeft() throws Exception {
        assertEquals(9, mSubject.getNbPlayLeft());
        mSubject.play(GameBoard.Player.J1, 2, 2);
        assertEquals(8, mSubject.getNbPlayLeft());
        mSubject.play(GameBoard.Player.J1, 1, 1);
        assertEquals(7, mSubject.getNbPlayLeft());
    }

    @Test
    public void testGetWinnerInCol() throws Exception {
        mSubject.play(GameBoard.Player.J1, 0, 0);
        mSubject.play(GameBoard.Player.J1, 0, 1);
        assertNull(mSubject.getWinner());
        mSubject.play(GameBoard.Player.J1, 0, 2);
        assertEquals(mSubject.getWinner(), GameBoard.Player.J1);
    }

    @Test
    public void testGetWinnerInRow() throws Exception {
        mSubject.play(GameBoard.Player.J1, 0, 0);
        mSubject.play(GameBoard.Player.J1, 1, 0);
        assertNull(mSubject.getWinner());
        mSubject.play(GameBoard.Player.J1, 2, 0);
        assertEquals(mSubject.getWinner(), GameBoard.Player.J1);
    }

    @Test
    public void testGetWinnerInDiagonalLeftToRight() throws Exception {
        mSubject.play(GameBoard.Player.J1, 0, 0);
        mSubject.play(GameBoard.Player.J1, 1, 1);
        assertNull(mSubject.getWinner());
        mSubject.play(GameBoard.Player.J1, 2, 2);
        assertEquals(mSubject.getWinner(), GameBoard.Player.J1);
    }

    @Test
    public void testGetWinnerInDiagonalRightToLeft() throws Exception {
        mSubject.play(GameBoard.Player.J1, 2, 2);
        mSubject.play(GameBoard.Player.J1, 1, 1);
        assertNull(mSubject.getWinner());
        mSubject.play(GameBoard.Player.J1, 0, 0);
        assertEquals(mSubject.getWinner(), GameBoard.Player.J1);
    }

    @Test
    public void testIsFill() throws Exception {
        assertFalse(mSubject.isFill(0, 0));
        mSubject.play(GameBoard.Player.J1, 0, 0);
        assertTrue(mSubject.isFill(0, 0));
    }

    @Test
    public void testPlayEmpty() throws Exception {
        assertFalse(mSubject.isFill(0, 0));
        mSubject.play(GameBoard.Player.EMPTY, 0, 0);
        assertFalse(mSubject.isFill(0, 0));
    }

    @Test
    public void testUndo() throws Exception {
        assertFalse(mSubject.isFill(0, 1));
        mSubject.play(GameBoard.Player.J1, 0, 1);
        assertTrue(mSubject.isFill(0, 1));
        mSubject.undo();
        assertFalse(mSubject.isFill(0, 1));
        mSubject.undo();
        assertFalse(mSubject.isFill(0, 1));
    }

    @Test
    public void testGetSize() throws Exception {
        assertEquals(3, mSubject.getSize());
    }
}