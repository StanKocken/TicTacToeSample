package com.skocken.tictactoeia.core;

import android.util.Log;

public class AI {

    private GameBoard.Player mAIPlayer;

    private GameBoard.Player mOtherPlayer;

    private GameBoard mGameBoard;

    private boolean mDebug;

    public AI(GameBoard gameBoard, GameBoard.Player player) {
        mGameBoard = gameBoard;
        mAIPlayer = player;
        mOtherPlayer = getOtherPlayer(player);
    }

    public void setDebug(boolean debug) {
        mDebug = debug;
    }

    private GameBoard.Player getOtherPlayer(GameBoard.Player player) {
        GameBoard.Player otherPlayer;
        if (player == GameBoard.Player.J1) {
            otherPlayer = GameBoard.Player.J2;
        } else {
            otherPlayer = GameBoard.Player.J1;
        }
        return otherPlayer;
    }

    public void playNext() {
        GameBoard practiceGameBoard = mGameBoard.clone();

        float higherChanceToWin = -1;
        GameBoard.Play nextPlay = null;
        int boardSize = practiceGameBoard.getSize();

        for (int x = 0; x < boardSize; x++) {
            for (int y = 0; y < boardSize; y++) {
                if (!practiceGameBoard.isFill(x, y)) {
                    practiceGameBoard.play(mAIPlayer, x, y);
                    float chanceToWin = evalChanceToWin(practiceGameBoard, mOtherPlayer);
                    if (mDebug) {
                        Log.v("IA", "With: ");
                        practiceGameBoard.printBoard();
                        Log.v("IA", "Chance To Win: " + chanceToWin);
                    }

                    GameBoard.Play play = practiceGameBoard.undo();
                    if (chanceToWin > higherChanceToWin) {
                        nextPlay = play;
                        higherChanceToWin = chanceToWin;
                        if (mDebug) {
                            Log.v("IA", "New Higher Chance!!");
                        }
                    }
                }
            }
        }
        mGameBoard.play(nextPlay);
    }

    /**
     * @return 1 if 100% of chance to win, 0% if no chance
     */
    private float evalChanceToWin(GameBoard practiceGameBoard, GameBoard.Player nextPlayer) {
        GameBoard.Player winner = practiceGameBoard.getWinner();
        float chance;
        if (winner == mAIPlayer) {
            chance = 1;
        } else if (winner == mOtherPlayer) {
            chance = 0;
        } else if (winner == GameBoard.Player.EMPTY) {
            chance = .5f;
        } else {

            int left = practiceGameBoard.getNbPlayLeft();
            GameBoard.Player nextNextPlayer = getOtherPlayer(nextPlayer);
            float sumChances = 0;
            for (int i = 0; i < left; i++) {
                playNextRandom(practiceGameBoard, nextPlayer, i);
                float chances = evalChanceToWin(practiceGameBoard, nextNextPlayer);
                sumChances += chances;
                practiceGameBoard.undo();
            }
            chance = sumChances / left;
        }
        if (mDebug) {
            Log.v("IA", "eval = " + chance);
        }
        return chance;
    }

    private void playNextRandom(GameBoard practiceGameBoard, GameBoard.Player nextPlayer,
            int offset) {
        int boardSize = mGameBoard.getSize();
        int playIndex = 0;
        for (int x = 0; x < boardSize; x++) {
            for (int y = 0; y < boardSize; y++) {
                if (!practiceGameBoard.isFill(x, y)) {
                    if (playIndex != offset) {
                        playIndex++;
                        continue;
                    }
                    practiceGameBoard.play(nextPlayer, x, y);
                    return;
                }
            }
        }
    }

}
