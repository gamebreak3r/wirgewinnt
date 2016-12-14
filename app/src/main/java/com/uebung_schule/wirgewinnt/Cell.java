package com.uebung_schule.wirgewinnt;

import android.graphics.Color;

/**
 * Created by Andreas on 11/11/2016.
 */

public class Cell {

    int column, row, status;
    //status legend:
    // 0 - unused
    // 1 - Player 1
    // 2 - Player 2 or AI

    public Cell(int column, int row, int status) {
        this.column = column;
        this.row = row;
        this.status = status;
    }

    /*
    public void setStatus(int newStatus) {
        status = newStatus;
        if (newStatus == 1) {
            setBackgroundColor(Color.GREEN);
        } else if (newStatus == 2) {
            setBackgroundColor(Color.RED);
        } else {
            setBackgroundColor(Color.WHITE);
        }
    } */
}
