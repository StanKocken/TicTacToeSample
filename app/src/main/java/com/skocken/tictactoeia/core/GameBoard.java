package com.skocken.tictactoeia.core;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {

    public enum Player {
        EMPTY, J1, J2
    }

    private boolean mDebug;

    private Player[][] mBoard =
            {
                    {Player.EMPTY, Player.EMPTY, Player.EMPTY},
                    {Player.EMPTY, Player.EMPTY, Player.EMPTY},
                    {Player.EMPTY, Player.EMPTY, Player.EMPTY}

            };

    private List<Play> mPlays = new ArrayList<>();

    public void setDebug(boolean debug) {
        mDebug = debug;
    }

    public int getSize() {
        return mBoard.length;
    }

    public int getNbPlayLeft() {
        int sum = 0;
        for (int y = 0; y < mBoard.length; y++) {
            for (int x = 0; x < mBoard[y].length; x++) {
                if (mBoard[y][x] == Player.EMPTY) {
                    sum++;
                }
            }
        }
        return sum;
    }

    public void setBoard(Player[][] board) {
        mBoard = board;

        if (mDebug) {
            printBoard();
        }
    }

    public Play play(Player player, int x, int y) {
        if (player == Player.EMPTY || mBoard[y][x] != Player.EMPTY) {
            return null;
        }
        return play(new Play(player, x, y));
    }

    public Play play(Play play) {
        if (play == null || getWinner() != null) {
            return null;
        }
        mBoard[play.mY][play.mX] = play.mPlayer;
        mPlays.add(play);
        if (mDebug) {
            printBoard();
        }
        return play;
    }

    public Play undo() {
        int size = mPlays.size();
        if (size == 0) {
            return null;
        }
        Play removed = mPlays.remove(size - 1);
        mBoard[removed.mY][removed.mX] = Player.EMPTY;
        return removed;
    }

    public Player getPlayer(int x, int y) {
        return mBoard[y][x];
    }

    public boolean isFill(int x, int y) {
        return getPlayer(x, y) != Player.EMPTY;
    }

    public Player getWinner() {
        Player player = getWinnerInRow();
        if (player == null) {
            player = getWinnerInCol();
        }
        if (player == null) {
            player = getWinnerInDiagonalLeftToRight();
        }
        if (player == null) {
            player = getWinnerInDiagonalRightToLeft();
        }
        if (player == null && isFull()) {
            player = Player.EMPTY;
        }

        return player;
    }

    private Player getWinnerInRow() {
        for (int y = 0; y < mBoard.length; y++) {
            Player[] line = mBoard[y];
            Player player = line[0];
            if (player == Player.EMPTY) {
                continue;
            }
            for (int x = 1; x < line.length; x++) {
                if (player != line[x]) {
                    player = null;
                }
            }
            if (player != null) {
                return player;
            }
        }
        return null;
    }

    private Player getWinnerInCol() {
        for (int x = 0; x < mBoard[0].length; x++) {
            Player player = mBoard[0][x];
            if (player == Player.EMPTY) {
                continue;
            }
            for (int y = 1; y < mBoard.length; y++) {
                if (mBoard[y][x] != player) {
                    player = null;
                }
            }
            if (player != null) {
                return player;
            }
        }
        return null;
    }

    private Player getWinnerInDiagonalLeftToRight() {
        Player player = mBoard[0][0];
        if (player == Player.EMPTY) {
            return null;
        }
        for (int i = 0; i < mBoard.length; i++) {
            if (player != mBoard[i][i]) {
                player = null;
            }
        }
        return player;
    }

    private Player getWinnerInDiagonalRightToLeft() {
        Player player = mBoard[mBoard.length - 1][0];
        if (player == Player.EMPTY) {
            return null;
        }
        for (int i = 0; i < mBoard.length; i++) {
            if (player != mBoard[mBoard.length - i - 1][i]) {
                player = null;
            }
        }
        return player;
    }

    private boolean isFull() {
        for (int y = 0; y < mBoard.length; y++) {
            for (int x = 0; x < mBoard[y].length; x++) {
                if (mBoard[y][x] == Player.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    public GameBoard clone() {
        GameBoard gameBoard = new GameBoard();
        for (int y = 0; y < mBoard.length; y++) {
            for (int x = 0; x < mBoard[y].length; x++) {
                gameBoard.mBoard[y][x] = mBoard[y][x];
            }
        }
        gameBoard.mPlays = new ArrayList<>(mPlays);
        return gameBoard;
    }

    public void printBoard() {
        Log.v("GameBoard", "-------");
        StringBuffer sb = new StringBuffer();
        for (int y = 0; y < mBoard.length; y++) {
            sb.setLength(0);
            sb.append("| ");
            for (int x = 0; x < mBoard[y].length; x++) {
                switch (mBoard[y][x]) {
                    case EMPTY:
                        sb.append(" ");
                        break;
                    case J1:
                        sb.append("X");
                        break;
                    case J2:
                        sb.append("O");
                        break;
                }
            }
            sb.append(" |");
            Log.v("GameBoard", sb.toString());
        }
        Log.v("GameBoard", "-------");
    }

    public static class Play {

        private Player mPlayer;

        private int mX, mY;

        public Play(Player player, int x, int y) {
            mX = x;
            mY = y;
            mPlayer = player;
        }


    }
}
