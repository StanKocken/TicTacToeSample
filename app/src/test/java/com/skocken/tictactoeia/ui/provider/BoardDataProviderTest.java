package com.skocken.tictactoeia.ui.provider;

import com.skocken.tictactoeia.core.GameBoard;
import com.skocken.tictactoeia.ui.definition.BoardDef;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class BoardDataProviderTest extends TestCase {

    private BoardDef.IPresenter mPresenter;

    private BoardDataProvider mDataProvider;

    @Before
    public void setUp() throws Exception {
        mPresenter = Mockito.mock(BoardDef.IPresenter.class);

        mDataProvider = new BoardDataProvider();
        mDataProvider.setPresenter(mPresenter);
    }

    @Test
    public void testPlay() throws Exception {
        GameBoard gameBoard = mDataProvider.getGameBoard();
        assertEquals(GameBoard.Player.EMPTY, gameBoard.getPlayer(0, 0));
        assertEquals(GameBoard.Player.EMPTY, gameBoard.getPlayer(1, 1));

        mDataProvider.play(0, 0);
        assertEquals(GameBoard.Player.J1, gameBoard.getPlayer(0, 0));
        assertEquals(GameBoard.Player.J2, gameBoard.getPlayer(1, 1)); // AI played

    }
}