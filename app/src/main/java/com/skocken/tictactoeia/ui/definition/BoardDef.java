package com.skocken.tictactoeia.ui.definition;

import com.skocken.tictactoeia.core.GameBoard;
import com.skocken.presentation.definition.Base;

public interface BoardDef {

    int BOX_EMPTY = 0;

    int BOX_CROSS = 1;

    int BOX_ROUND = 2;

    interface IPresenter extends Base.IPresenter {

        void refreshBoard();

        void onSelectBox(int x, int y);
    }

    interface IDataProvider extends Base.IDataProvider {

        GameBoard getGameBoard();

        void play(int x, int y);
    }

    interface IView extends Base.IView {

        void setBoxValue(int x, int y, int boxType);
    }
}
