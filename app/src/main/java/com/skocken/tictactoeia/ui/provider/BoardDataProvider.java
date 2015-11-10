package com.skocken.tictactoeia.ui.provider;

import com.skocken.tictactoeia.core.AI;
import com.skocken.tictactoeia.core.GameBoard;
import com.skocken.tictactoeia.ui.definition.BoardDef;
import com.skocken.presentation.provider.BaseDataProvider;

public class BoardDataProvider
        extends BaseDataProvider<BoardDef.IPresenter>
        implements BoardDef.IDataProvider {

    private final GameBoard mGameBoard = new GameBoard();

    private final GameBoard.Player mCurrentPlayer = GameBoard.Player.J1;

    private final AI mAI = new AI(mGameBoard, GameBoard.Player.J2);

    @Override
    public GameBoard getGameBoard() {
        return mGameBoard;
    }

    @Override
    public void play(int x, int y) {
        mGameBoard.play(mCurrentPlayer, x, y);
        mAI.playNext();
        getPresenter().refreshBoard();
    }
}
