package com.skocken.tictactoeia.ui.presenter;


import com.skocken.presentation.presenter.BasePresenter;
import com.skocken.tictactoeia.core.GameBoard;
import com.skocken.tictactoeia.ui.definition.BoardDef;

public class BoardPresenter
        extends BasePresenter<BoardDef.IDataProvider, BoardDef.IView>
        implements BoardDef.IPresenter {

    public BoardPresenter(BoardDef.IDataProvider provider, BoardDef.IView view) {
        super(provider, view);
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshBoard();
    }

    @Override
    public void onSelectBox(int x, int y) {
        getProvider().play(x, y);
    }

    @Override
    public void refreshBoard() {
        GameBoard gameBoard = getProvider().getGameBoard();
        int size = gameBoard.getSize();
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                setBoxValue(gameBoard, y, x);
            }
        }
    }

    private void setBoxValue(GameBoard gameBoard, int y, int x) {
        GameBoard.Player player = gameBoard.getPlayer(x, y);
        int boxValue;
        switch (player) {
            case J1:
                boxValue = BoardDef.BOX_CROSS;
                break;
            case J2:
                boxValue = BoardDef.BOX_ROUND;
                break;
            default:
                boxValue = BoardDef.BOX_EMPTY;
                break;
        }
        getView().setBoxValue(x, y, boxValue);
    }
}
