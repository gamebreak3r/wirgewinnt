package com.uebung_schule.wirgewinnt;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by consult on 12.02.2017.
 */

public class CreateGame {
    /*
    Create the Game with the ImageVies
    For Example IDS: 1011
     */
    protected static void createGame(View view, MainActivity ma) {
        LinearLayout table00 = (LinearLayout) ma.findViewById(R.id.table00);
        for (int i = 6; i >= 0; i--) {
            ImageView column = new ImageView(ma);
            column.setLayoutParams(new android.view.ViewGroup.LayoutParams(93, 93));
            column.setId(i + 1000);
            column.setBackgroundColor(Color.BLACK);
            table00.addView(column);
        }

        LinearLayout table10 = (LinearLayout) ma.findViewById(R.id.table10);
        for (int i = 6; i >= 0; i--) {
            ImageView column = new ImageView(ma);
            column.setLayoutParams(new android.view.ViewGroup.LayoutParams(93, 93));
            column.setId(i + 1000 + 10);
            column.setBackgroundColor(Color.BLACK);
            table10.addView(column);
        }

        LinearLayout table20 = (LinearLayout) ma.findViewById(R.id.table20);
        for (int i = 6; i >= 0; i--) {
            ImageView column = new ImageView(ma);
            column.setLayoutParams(new android.view.ViewGroup.LayoutParams(93, 93));
            column.setId(i + 1000 + 20);
            column.setBackgroundColor(Color.BLACK);
            table20.addView(column);
        }

        LinearLayout table30 = (LinearLayout) ma.findViewById(R.id.table30);
        for (int i = 6; i >= 0; i--) {
            ImageView column = new ImageView(ma);
            column.setLayoutParams(new android.view.ViewGroup.LayoutParams(93, 93));
            column.setId(i + 1000 + 30);
            column.setBackgroundColor(Color.BLACK);
            table30.addView(column);
        }

        LinearLayout table40 = (LinearLayout) ma.findViewById(R.id.table40);
        for (int i = 6; i >= 0; i--) {
            ImageView column = new ImageView(ma);
            column.setLayoutParams(new android.view.ViewGroup.LayoutParams(93, 93));
            column.setId(i + 1000 + 40);
            column.setBackgroundColor(Color.BLACK);
            table40.addView(column);
        }

        LinearLayout table50 = (LinearLayout) ma.findViewById(R.id.table50);
        for (int i = 6; i >= 0; i--) {
            ImageView column = new ImageView(ma);
            column.setLayoutParams(new android.view.ViewGroup.LayoutParams(93, 93));
            column.setId(i + 1000 + 50);
            column.setBackgroundColor(Color.BLACK);
            table50.addView(column);
        }

        LinearLayout table60 = (LinearLayout) ma.findViewById(R.id.table60);
        for (int i = 6; i >= 0; i--) {
            ImageView column = new ImageView(ma);
            column.setLayoutParams(new android.view.ViewGroup.LayoutParams(93, 93));
            column.setId(i + 1000 + 60);
            column.setBackgroundColor(Color.BLACK);
            table60.addView(column);
        }

        ma.gameBoard = new GameBoard(7, ma.findViewById(R.id.activity_main));
        ma.findViewById(R.id.btnHotseat).setBackgroundColor(Color.RED);
        ma.findViewById(R.id.btnSingleplayer).setBackgroundColor(Color.BLUE);
        ma.findViewById(R.id.btnOnline).setBackgroundColor(Color.BLUE);
        ma.findViewById(R.id.txtPlayer).setBackgroundColor(Color.BLUE);
    }
}
