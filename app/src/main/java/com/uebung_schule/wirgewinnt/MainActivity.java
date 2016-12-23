package com.uebung_schule.wirgewinnt;

import android.content.DialogInterface;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    GameBoard gameBoard;
    boolean currentPlayer;
    //currentPlayer legend:
    // true  - Player 1
    // false - Player 2

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_login);
    }

    protected void createGeame(View view) {
        setContentView(R.layout.activity_main);

        LinearLayout table00 = (LinearLayout)findViewById(R.id.table00);
            for (int i = 6; i >= 0; i--) {
                ImageView column = new ImageView(this);
                column.setLayoutParams(new android.view.ViewGroup.LayoutParams(93, 93));
                column.setId(i + 1000 + 00);
                column.setBackgroundColor(Color.BLACK);
                table00.addView(column);
            }

        LinearLayout table10 = (LinearLayout)findViewById(R.id.table10);
        for (int i = 6; i >= 0; i--) {
            ImageView column = new ImageView(this);
            column.setLayoutParams(new android.view.ViewGroup.LayoutParams(93, 93));
            column.setId(i + 1000 + 10);
            column.setBackgroundColor(Color.BLACK);
            table10.addView(column);
        }

        LinearLayout table20 = (LinearLayout)findViewById(R.id.table20);
        for (int i = 6; i >= 0; i--) {
            ImageView column = new ImageView(this);
            column.setLayoutParams(new android.view.ViewGroup.LayoutParams(93, 93));
            column.setId(i + 1000 + 20);
            column.setBackgroundColor(Color.BLACK);
            table20.addView(column);
        }

        LinearLayout table30 = (LinearLayout)findViewById(R.id.table30);
        for (int i = 6; i >= 0; i--) {
            ImageView column = new ImageView(this);
            column.setLayoutParams(new android.view.ViewGroup.LayoutParams(93, 93));
            column.setId(i + 1000 + 30);
            column.setBackgroundColor(Color.BLACK);
            table30.addView(column);
        }

        LinearLayout table40 = (LinearLayout)findViewById(R.id.table40);
        for (int i = 6; i >= 0; i--) {
            ImageView column = new ImageView(this);
            column.setLayoutParams(new android.view.ViewGroup.LayoutParams(93, 93));
            column.setId(i + 1000 + 40);
            column.setBackgroundColor(Color.BLACK);
            table40.addView(column);
        }

        LinearLayout table50 = (LinearLayout)findViewById(R.id.table50);
        for (int i = 6; i >= 0; i--) {
            ImageView column = new ImageView(this);
            column.setLayoutParams(new android.view.ViewGroup.LayoutParams(93, 93));
            column.setId(i + 1000 + 50);
            column.setBackgroundColor(Color.BLACK);
            table50.addView(column);
        }

        LinearLayout table60 = (LinearLayout)findViewById(R.id.table60);
        for (int i = 6; i >= 0; i--) {
            ImageView column = new ImageView(this);
            column.setLayoutParams(new android.view.ViewGroup.LayoutParams(93, 93));
            column.setId(i + 1000 + 60);
            column.setBackgroundColor(Color.BLACK);
            table60.addView(column);
        }
        
        gameBoard = new GameBoard(7,findViewById(R.id.activity_main));
    }

    public void onClick(View v) {
        Cell[][] status = gameBoard.getGameBorad();
        switch (v.getId()) {
            case R.id.btnLogin:
                SQLDatabase.setConnection();
                TextView username = (TextView) findViewById(R.id.loginUsername);
                TextView passwort = (TextView) findViewById(R.id.loginPasswort);
                //Wenn die Datenbank geht
               if (SQLDatabase.getLoginTrue(username.toString(), passwort.toString()))
                {
                    System.out.println("Es geht!");
                }
                else{
                    Toast.makeText(this, "Username oder Passwort falsch", Toast.LENGTH_LONG).show();
                }
                break;

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
                if (status[0][6].status != 0) break;
                putStone(0);
                setPlayer();
                break;

            case R.id.btnColumn1:
                //stein setzen
                if (status[1][6].status != 0) break;
                putStone(1);
                setPlayer();
                break;

            case R.id.btnColumn2:
                //stein setzen
                if (status[2][6].status != 0) break;
                putStone(2);
                setPlayer();
                break;

            case R.id.btnColumn3:
                //stein setzen
                if (status[3][6].status != 0) break;
                putStone(3);
                setPlayer();
                break;


            case R.id.btnColumn4:
                //stein setzen
                if (status[4][6].status != 0) break;
                putStone(4);
                setPlayer();
                break;

            case R.id.btnColumn5:
                //stein setzen
                if (status[5][6].status != 0) break;
                putStone(5);
                setPlayer();
                break;

            case R.id.btnColumn6:
                //stein setzen
                if (status[6][6].status != 0) break;
                putStone(6);
                setPlayer();
                break;
            //endregion
        }
    }
    private void setPlayer () {
        if (currentPlayer) {
            findViewById(R.id.txtPlayer).setBackgroundColor(Color.RED);
            ((TextView) findViewById(R.id.txtPlayer)).setText("Spieler 2 an der Reihe");
        } else {
            findViewById(R.id.txtPlayer).setBackgroundColor(Color.BLUE);
            ((TextView) findViewById(R.id.txtPlayer)).setText("Spieler 1 an der Reihe");
        }
    }

    private void putStone(int column) {
        //spalten anschauen
        //steine zählen
        //draufsetzen
        int row = gameBoard.stonesInColumn(column);
        if (row <= 6) {
            gameBoard.putStone(column, row, currentPlayer, findViewById(R.id.activity_main));
        }
        if (gameBoard.checkIfWon(column, row, findViewById(R.id.activity_main))){
            String playerAusgabe = null;
            if (!currentPlayer) playerAusgabe="Spieler 1";
            else playerAusgabe="Spieler 2";

            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
            dlgAlert.setTitle("Gewonnen");
            dlgAlert.setMessage("Spieler: " + playerAusgabe +" hat gewonnen!");
            dlgAlert.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                   //Nichts
                }
            });
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
            Cell[][] reset = gameBoard.getGameBorad();
            for (int i = 0; i <= 6; i++)
            {
                for (int a = 0; a <= 6; a++) {
                    reset[i][a].status = 0;
                    findViewById(1000+ i + (10*a)).setBackgroundColor(Color.BLACK);
                }
            }
        }
        currentPlayer = !currentPlayer;
    }

}
