package com.uebung_schule.wirgewinnt;

import android.graphics.Color;
import android.media.Image;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Andreas on 11/11/2016.
 */

public class Cell {

    int column, row, status;
    //status legend:
    // 0 - unused
    // 1 - Player 1
    // 2 - Player 2 or AI

    public Cell(int column, int row, int status, View view) {
        this.column = column;
        this.row = row;
        this.status = status;

        if (status == 1){
            ImageView table = (ImageView) view.findViewById(1000+column+row);
            table.setBackgroundColor(Color.BLUE);



        }
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
