package com.example.egzamin;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SudokuBoardView {
    public interface OnCellClickListener {
        void onCellClick(int row, int col);
    }

    private final Context context;
    private SudokuBoard board;
    private final LinearLayout[] rowContainers;
    private final TextView[][] cellViews = new TextView[4][4];
    private OnCellClickListener listener;

    public SudokuBoardView(Context context, SudokuBoard board, LinearLayout[] rowContainers) {
        this.context       = context;
        this.board         = board;
        this.rowContainers = rowContainers;
    }

    public void setOnCellClickListener(OnCellClickListener l) { this.listener = l; }

    public void buildGrid() {
        for (LinearLayout row : rowContainers) row.removeAllViews();

        float dp = context.getResources().getDisplayMetrics().density;
        int size   = (int)(72 * dp);
        int margin = (int)(3  * dp);

        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                TextView tv = new TextView(context);
                tv.setGravity(Gravity.CENTER);
                tv.setTextSize(22f);

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(size, size);
                lp.setMargins(margin, margin, c == 1 ? (int)(6*dp) : margin, margin);
                tv.setLayoutParams(lp);

                cellViews[r][c] = tv;
                rowContainers[r].addView(tv);

                final int fr = r, fc = c;
                if (!board.isFixed(r, c)) {
                    tv.setOnClickListener(v -> { if (listener != null) listener.onCellClick(fr, fc); });
                }
            }
        }
        refreshAll();
    }

    public void refreshAll() {
        for (int r = 0; r < 4; r++)
            for (int c = 0; c < 4; c++)
                refreshCell(r, c);
    }

    public void refreshCell(int r, int c) {
        TextView tv = cellViews[r][c];
        int val = board.getValue(r, c);
        tv.setText(val == 0 ? "" : String.valueOf(val));

        if (board.isFixed(r, c)) {
            tv.setBackgroundResource(R.drawable.cell_fixed);
            tv.setTextColor(Color.parseColor("#B0A8FF"));
            tv.setTypeface(null, android.graphics.Typeface.BOLD);
        } else {
            tv.setBackgroundResource(R.drawable.cell_editable);
            tv.setTextColor(Color.parseColor("#E0E0FF"));
        }
    }

    public void markError(int r, int c) {
        cellViews[r][c].setBackgroundResource(R.drawable.cell_error);
        cellViews[r][c].setTextColor(Color.parseColor("#FF6B6B"));
    }

    public void setBoard(SudokuBoard newBoard) {
        this.board = newBoard;
        buildGrid();
    }
}
