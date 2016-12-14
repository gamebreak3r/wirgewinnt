package com.uebung_schule.wirgewinnt;

import android.graphics.Color;
import android.media.Image;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        LinearLayout table = (LinearLayout)findViewById(R.id.table);

        for (int l = 0; l < 7; l++) {
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layout.setLayoutParams(layoutParams);

            for (int i = 0; i < 7; i++) {
                ImageView column = new ImageView(this);
                column.setLayoutParams(new android.view.ViewGroup.LayoutParams(93, 93));
                column.setId(i + 1000);
                column.setBackgroundColor(Color.BLACK);
                layout.addView(column);
            }
        }


     //   ImageView bla;
     //   Button btn = (Button) View.findViewWithTag(Cell.getX().toString() + " " + Cell.getY().toString());
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
