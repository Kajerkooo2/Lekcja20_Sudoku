package com.example.egzamin;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class InputDialog extends Dialog {

    public interface OnValueSelected {
        void onSelected(int value);
    }

    public InputDialog(Context context, OnValueSelected listener) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (getWindow() != null)
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        float dp = context.getResources().getDisplayMetrics().density;

        // Główny kontener
        LinearLayout root = new LinearLayout(context);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER);
        root.setPadding((int)(24 * dp), (int)(20 * dp), (int)(24 * dp), (int)(20 * dp));
        root.setBackgroundResource(R.drawable.boad_background);

        // Tytuł okna
        TextView title = new TextView(context);
        title.setText("Wybierz liczbe");
        title.setTextColor(Color.parseColor("#E0E0FF"));
        title.setTextSize(16f);
        title.setGravity(Gravity.CENTER);
        title.setPadding(0, 0, 0, (int)(16 * dp));
        root.addView(title);

        // Wiersz na przyciski - wymuszamy dopasowanie do szerokości rodzica
        LinearLayout row = new LinearLayout(context);
        row.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams rowParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        row.setLayoutParams(rowParams);

        int[] values = {1, 2, 3, 4, 0};
        String[] labels = {"1", "2", "3", "4", "✕"};

        LinearLayout.LayoutParams btnParams = new LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                1.0f
        );

        int margin = (int)(3 * dp);
        btnParams.setMargins(margin, 0, margin, 0);

        for (int i = 0; i < values.length; i++) {
            final int val = values[i];
            Button btn = new Button(context);
            btn.setText(labels[i]);
            btn.setTextColor(val == 0 ? Color.parseColor("#FF6B6B") : Color.parseColor("#B0A8FF"));
            btn.setTextSize(18f);
            btn.setBackgroundResource(R.drawable.cell_editable);

            btn.setPadding(0, 0, 0, 0);

            btn.setOnClickListener(v -> {
                listener.onSelected(val);
                dismiss();
            });

            row.addView(btn, btnParams);
        }

        root.addView(row);
        setContentView(root);
    }
}
