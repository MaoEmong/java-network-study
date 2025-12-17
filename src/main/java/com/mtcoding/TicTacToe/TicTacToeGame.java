package com.mtcoding.TicTacToe;

import java.util.Arrays;

// ============================================================
// 3) TicTacToeGame : 게임 상태/규칙(네트워크와 분리)
// ============================================================
public class TicTacToeGame {
    static final char EMPTY = '.';

    private final char[] board = new char[9];
    private char currentTurn = 'X';
    private boolean gameOver = false;

    TicTacToeGame() {
        Arrays.fill(board, EMPTY);
    }

    static class PutResult {
        boolean ok;
        String message;

        char winner = EMPTY;
        boolean draw = false;
    }

    synchronized char getCurrentTurn() {
        return currentTurn;
    }

    synchronized String getStateString() {
        return new String(board);
    }

    synchronized void stop() {
        gameOver = true;
    }

    synchronized PutResult tryPut(char mark, int r, int c) {
        PutResult res = new PutResult();

        System.out.println(mark + " put x : "+r+" y : "+c);

        if (gameOver) {
            res.ok = false;
            res.message = "game over";
            return res;
        }
        if (mark != currentTurn) {
            res.ok = false;
            res.message = "not your turn";
            return res;
        }
        if (r < 0 || r > 2 || c < 0 || c > 2) {
            res.ok = false;
            res.message = "out of range";
            return res;
        }

        int idx = r * 3 + c;
        if (board[idx] != EMPTY) {
            res.ok = false;
            res.message = "cell not empty";
            return res;
        }

        board[idx] = mark;

        int index = 0;
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0 ; j < 3; j++)
            {
                System.out.print(board[index]+" ");
                index++;
            }
            System.out.println();
        }

        // 승/무 체크
        char w = checkWinner();
        if (w != EMPTY) {
            gameOver = true;
            res.ok = true;
            res.winner = w;
            return res;
        }

        if (isDraw()) {
            gameOver = true;
            res.ok = true;
            res.draw = true;
            return res;
        }

        // 턴 교체
        currentTurn = (currentTurn == 'X') ? 'O' : 'X';

        res.ok = true;
        return res;
    }

    private char checkWinner() {
        int[][] lines = {
                {0,1,2},{3,4,5},{6,7,8},
                {0,3,6},{1,4,7},{2,5,8},
                {0,4,8},{2,4,6}
        };
        for (int[] l : lines) {
            char a = board[l[0]];
            if (a == EMPTY) continue;
            if (a == board[l[1]] && a == board[l[2]]) return a;
        }
        return EMPTY;
    }

    private boolean isDraw() {
        for (char v : board) if (v == EMPTY) return false;
        return true;
    }

    synchronized void reset() {
        Arrays.fill(board, EMPTY);
        currentTurn = 'X';
        gameOver = false;
    }
}
