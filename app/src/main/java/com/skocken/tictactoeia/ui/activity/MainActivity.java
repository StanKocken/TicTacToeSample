package com.skocken.tictactoeia.ui.activity;

import com.skocken.tictactoeia.R;
import com.skocken.tictactoeia.ui.definition.BoardDef;
import com.skocken.tictactoeia.ui.presenter.BoardPresenter;
import com.skocken.tictactoeia.ui.provider.BoardDataProvider;
import com.skocken.tictactoeia.ui.viewproxy.BoardViewProxy;
import com.skocken.presentation.activity.BaseAppCompatActivity;
import com.skocken.presentation.presenter.BasePresenter;


public class MainActivity extends BaseAppCompatActivity {

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected BasePresenter newPresenter() {
        BoardDef.IDataProvider provider = new BoardDataProvider();
        BoardDef.IView viewProxy = new BoardViewProxy(this);
        return new BoardPresenter(provider, viewProxy);
    }

}
