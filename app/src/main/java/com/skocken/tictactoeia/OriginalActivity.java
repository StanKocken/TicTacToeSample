package com.skocken.tictactoeia;

import com.skocken.tictactoeia.core.AI;
import com.skocken.tictactoeia.core.GameBoard;
import com.skocken.tictactoeia.ui.definition.BoardDef;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

public class OriginalActivity extends Activity
        implements View.OnClickListener {

    private final GameBoard mGameBoard = new GameBoard();

    private final GameBoard.Player mCurrentPlayer = GameBoard.Player.J1;

    private final AI mAI = new AI(mGameBoard, GameBoard.Player.J2);

    private ViewGroup mBoxesLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBoxesLayout = (ViewGroup) findViewById(R.id.boxes_layout);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshBoard();
    }

    @Override
    public void onClick(View v) {
        Object x = v.getTag(R.id.tag_x);
        Object y = v.getTag(R.id.tag_y);
        if (x instanceof Integer && y instanceof Integer) {
            play((int) x, (int) y);
        }
    }

    private void play(int x, int y) {
        mGameBoard.play(mCurrentPlayer, x, y);
        mAI.playNext();
        refreshBoard();
    }

    private void refreshBoard() {
        int size = mGameBoard.getSize();
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                GameBoard.Player player = mGameBoard.getPlayer(x, y);

                int boxType;
                switch (player) {
                    case EMPTY:
                        boxType = BoardDef.BOX_EMPTY;
                        break;
                    case J1:
                        boxType = BoardDef.BOX_CROSS;
                        break;
                    case J2:
                        boxType = BoardDef.BOX_ROUND;
                        break;
                    default:
                        continue;
                }
                setBoxValue(x, y, boxType);
            }
        }
    }

    private void setBoxValue(int x, int y, int boxType) {
        ViewGroup rowLayout = (ViewGroup) mBoxesLayout.getChildAt(y);
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
}
