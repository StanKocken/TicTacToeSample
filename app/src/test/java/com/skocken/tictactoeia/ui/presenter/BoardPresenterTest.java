package com.skocken.tictactoeia.ui.presenter;

import com.skocken.tictactoeia.core.GameBoard;
import com.skocken.tictactoeia.ui.definition.BoardDef;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import android.app.Activity;

import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class BoardPresenterTest extends TestCase {

    private BoardDef.IDataProvider mDataProvider;

    private BoardDef.IView mBoardView;

    private Activity mActivity;

    private BoardPresenter mBoardPresenter;

    @Before
    public void setUp() throws Exception {
        mDataProvider = Mockito.mock(BoardDef.IDataProvider.class);
        mBoardView = Mockito.mock(BoardDef.IView.class);
        mActivity = Mockito.mock(Activity.class);

        mBoardPresenter = new BoardPresenter(mDataProvider, mBoardView);
    }

    @Test
    public void testBoardFillView() throws Exception {
        GameBoard.Player[][] initBoard =
                {
                        {GameBoard.Player.J1, GameBoard.Player.EMPTY, GameBoard.Player.J1},
                        {GameBoard.Player.J2, GameBoard.Player.J1, GameBoard.Player.J1},
                        {GameBoard.Player.EMPTY, GameBoard.Player.J2, GameBoard.Player.J2}

                };

        int[][] boxValuesExpected =
                {
                        {BoardDef.BOX_CROSS,
                                BoardDef.BOX_EMPTY,
                                BoardDef.BOX_CROSS},
                        {BoardDef.BOX_ROUND,
                                BoardDef.BOX_CROSS,
                                BoardDef.BOX_CROSS},
                        {BoardDef.BOX_EMPTY,
                                BoardDef.BOX_ROUND,
                                BoardDef.BOX_ROUND}
                };

        GameBoard gameBoard = new GameBoard();
        gameBoard.setBoard(initBoard);
        when(mDataProvider.getGameBoard()).thenReturn(gameBoard);

        mBoardPresenter.onResume();

        ArgumentCaptor<Integer> xArguments = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> yArguments = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> boxValueArguments = ArgumentCaptor.forClass(Integer.class);

        verify(mBoardView, times(9)).setBoxValue(xArguments.capture(), yArguments.capture(),
                boxValueArguments.capture());

        List<Integer> capturedX = xArguments.getAllValues();
        List<Integer> capturedY = yArguments.getAllValues();
        List<Integer> capturedBoxValue = boxValueArguments.getAllValues();

        int[][] boxValuesCapture = new int[3][3];

        for (int i = 0; i < 9; i++) {
            boxValuesCapture[capturedY.get(i)][capturedX.get(i)] = capturedBoxValue.get(i);
        }

        Assert.assertArrayEquals(boxValuesExpected, boxValuesCapture);
    }

    @Test
    public void testOnSelectBox() throws Exception {
        mBoardPresenter.onSelectBox(2, 3);

        verify(mDataProvider).play(2, 3);
    }
}