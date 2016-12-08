package com.uebung_schule.wirgewinnt;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

        gameBoard = new GameBoard(7);
    }

    boolean gameHasEnded = false;
    private void onClick(View v) {
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
            case R.id.btnColumn1:
                //stein setzen
                putStone(1);
                //check ob gewonnen (boolean)
                if (gameHasEnded) ((TextView) findViewById(R.id.txtPlayer)).setText("Gewonnen!"); //TODO: String ausgliedern
                //wer an der reihe ist ändern
                findViewById(R.id.txtPlayer).setBackgroundColor(Color.RED);
                ((TextView) findViewById(R.id.txtPlayer)).setText("Spieler 2 an der Reihe"); //TODO: String ausgliedern
                break;
            case R.id.btnCancel:
                //gameboard gone
                //array zurücksetzten
                //buttons anzeigen

                break;


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
