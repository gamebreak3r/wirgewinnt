package com.uebung_schule.wirgewinnt;

import android.graphics.Color;
import android.media.Image;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    GameBoard gameBoard;
    boolean currentPlayer;
    //currentPlayer legend:
    // true  - Player 1
    // false - Player 2

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout table00 = (LinearLayout)findViewById(R.id.table00);
            for (int i = 0; i < 7; i++) {
                ImageView column = new ImageView(this);
                column.setLayoutParams(new android.view.ViewGroup.LayoutParams(93, 93));
                column.setId(i + 1000 + 00);
                column.setBackgroundColor(Color.BLACK);
                table00.addView(column);
            }

        LinearLayout table10 = (LinearLayout)findViewById(R.id.table10);
        for (int i = 0; i < 7; i++) {
            ImageView column = new ImageView(this);
            column.setLayoutParams(new android.view.ViewGroup.LayoutParams(93, 93));
            column.setId(i + 1000 + 10);
            column.setBackgroundColor(Color.BLACK);
            table10.addView(column);
        }

        LinearLayout table20 = (LinearLayout)findViewById(R.id.table20);
        for (int i = 0; i < 7; i++) {
            ImageView column = new ImageView(this);
            column.setLayoutParams(new android.view.ViewGroup.LayoutParams(93, 93));
            column.setId(i + 1000 + 20);
            column.setBackgroundColor(Color.BLACK);
            table20.addView(column);
        }

        LinearLayout table30 = (LinearLayout)findViewById(R.id.table30);
        for (int i = 0; i < 7; i++) {
            ImageView column = new ImageView(this);
            column.setLayoutParams(new android.view.ViewGroup.LayoutParams(93, 93));
            column.setId(i + 1000 + 30);
            column.setBackgroundColor(Color.BLACK);
            table30.addView(column);
        }

        LinearLayout table40 = (LinearLayout)findViewById(R.id.table40);
        for (int i = 0; i < 7; i++) {
            ImageView column = new ImageView(this);
            column.setLayoutParams(new android.view.ViewGroup.LayoutParams(93, 93));
            column.setId(i + 1000 + 40);
            column.setBackgroundColor(Color.BLACK);
            table40.addView(column);
        }

        LinearLayout table50 = (LinearLayout)findViewById(R.id.table50);
        for (int i = 0; i < 7; i++) {
            ImageView column = new ImageView(this);
            column.setLayoutParams(new android.view.ViewGroup.LayoutParams(93, 93));
            column.setId(i + 1000 + 50);
            column.setBackgroundColor(Color.BLACK);
            table50.addView(column);
        }

        LinearLayout table60 = (LinearLayout)findViewById(R.id.table60);
        for (int i = 0; i < 7; i++) {
            ImageView column = new ImageView(this);
            column.setLayoutParams(new android.view.ViewGroup.LayoutParams(93, 93));
            column.setId(i + 1000 + 60);
            column.setBackgroundColor(Color.BLACK);
            table60.addView(column);
        }
        
        gameBoard = new GameBoard(7);
    }

    boolean gameHasEnded = false;
    public void onClick(View v) {
        switch (v.getId()) {

            //region mode
            case R.id.btnSingleplayer:
                //TODO

                //Spielfeld erscheint
                findViewById(R.id.llGameColumns).setVisibility(View.VISIBLE);

                break;
            case R.id.btnHotseat:
                //Spielfeld erscheint
                findViewById(R.id.llGameColumns).setVisibility(View.VISIBLE);
                //Zuganzeige (textuell, visuell)
                findViewById(R.id.txtPlayer).setVisibility(View.VISIBLE);
                currentPlayer = true;

                break;
            case R.id.btnOnline:
                //TODO

                //Spielfeld erscheint
                findViewById(R.id.llGameColumns).setVisibility(View.VISIBLE);

                break;

            //endregion

            //region gameplay
            case R.id.btnColumn0:
                //stein setzen
                putStone(0);
                //check ob gewonnen (boolean)
                if (gameHasEnded) ((TextView) findViewById(R.id.txtPlayer)).setText("Gewonnen!"); //TODO: String ausgliedern
                //wer an der reihe ist ändern
                findViewById(R.id.txtPlayer).setBackgroundColor(Color.RED);
                ((TextView) findViewById(R.id.txtPlayer)).setText("Spieler 2 an der Reihe"); //TODO: String ausgliedern
                break;
            //case R.id.btnCancel:
                //gameboard gone
                //array zurücksetzten
                //buttons anzeigen

                //break;
           // ImageView a = (ImageView) findViewById(R.id.iv00);
           // a.setId(00);

            //endregion
        }
    }

    private void putStone(int column) {
        //spalten anschauen
        //steine zählen
        //draufsetzen
        gameBoard.putStone(column, gameBoard.stonesInColumn(column), currentPlayer);
        //TODO: UI Update
        //zug beendet
        currentPlayer = !currentPlayer;
        //TODO: gameHasEnded setzen
    }

}
