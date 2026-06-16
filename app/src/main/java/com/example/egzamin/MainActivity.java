package com.example.egzamin;


import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private SudokuBoard board;
    private SudokuBoardView boardView;
    private TextView tvStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvStatus = findViewById(R.id.tvStatus);

        LinearLayout[] rows = {
                findViewById(R.id.row0),
                findViewById(R.id.row1),
                findViewById(R.id.row2),
                findViewById(R.id.row3)
        };

        board = new SudokuBoard();
        boardView = new SudokuBoardView(this, board, rows);
        boardView.setOnCellClickListener(this::onCellClick);
        boardView.buildGrid();

        findViewById(R.id.btnCheck).setOnClickListener(v -> sprawdz());
        findViewById(R.id.btnNew).setOnClickListener(v -> nowaGra());
    }

    private void onCellClick(int row, int col) {
        tvStatus.setText("");
        new InputDialog(this, value -> {
            board.setValue(row, col, value);
            boardView.refreshCell(row, col);
        }).show();
    }

    private void sprawdz() {
        if (board.hasEmptyCells()) {
            tvStatus.setText("Uzupełnij wszystkie pola.");
            tvStatus.setTextColor(Color.parseColor("#9090BB"));
            return;
        }

        boolean blad = false;
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                if (!board.isCorrect(r, c)) {
                    boardView.markError(r, c);
                    blad = true;
                }
            }
        }

        if (blad) {
            tvStatus.setText("x");
            tvStatus.setTextColor(Color.parseColor("#FF6B6B"));
        } else {
            tvStatus.setText("✅");
            tvStatus.setTextColor(Color.parseColor("#4CAF50"));
        }
    }

    private void nowaGra() {
        board = new SudokuBoard();
        boardView.setBoard(board);
        boardView.setOnCellClickListener(this::onCellClick);
        tvStatus.setText("");
    }
}