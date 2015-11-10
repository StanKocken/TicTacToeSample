package com.skocken.tictactoeia.ui.viewproxy;

import com.skocken.tictactoeia.R;
import com.skocken.tictactoeia.ui.definition.BoardDef;
import com.skocken.presentation.viewproxy.BaseViewProxy;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

public class BoardViewProxy extends BaseViewProxy<BoardDef.IPresenter>
        implements BoardDef.IView, View.OnClickListener {

    public BoardViewProxy(Activity activity) {
        super(activity);
    }

    @Override
    public void setBoxValue(int x, int y, int boxType) {
        ViewGroup boxLayout = findViewByIdEfficient(R.id.boxes_layout);
        ViewGroup rowLayout = (ViewGroup) boxLayout.getChildAt(y);
        View boxView = rowLayout.getChildAt(x);

        int bgColor;
        switch (boxType) {
            case BoardDef.BOX_CROSS:
                bgColor = Color.RED;
                boxView.setOnClickListener(null);
                break;
            case BoardDef.BOX_ROUND:
                bgColor = Color.BLUE;
                boxView.setOnClickListener(null);
                break;
            default:
                bgColor = Color.GRAY;
                boxView.setOnClickListener(this);
                break;
        }
        boxView.setBackgroundColor(bgColor);
        boxView.setTag(R.id.tag_x, x);
        boxView.setTag(R.id.tag_y, y);
    }

    @Override
    public void onClick(View v) {
        int x = (Integer) v.getTag(R.id.tag_x);
        int y = (Integer) v.getTag(R.id.tag_y);
        getPresenter().onSelectBox(x, y);
    }
}
