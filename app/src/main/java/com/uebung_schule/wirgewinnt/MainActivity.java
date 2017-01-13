package com.uebung_schule.wirgewinnt;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {

    GameBoard gameBoard;
    boolean currentPlayer;
    //currentPlayer legend:
    // true  - Player 1
    // false - Player 2
    int mode = 0;
    //mode legend:
    //0 = nix ausgewähtl
    //1 = Singelplayer
    //2 = Hotseat
    //3 = Online

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
                column.setId(i + 1000 );
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

    private int random () {
        Cell[][] status = gameBoard.getGameBorad();
        int count_h = 0;
        int count_v = 0;
        int player = 1;


        if(currentPlayer) player = 2;

        for (int i = 0; i < 6; i++){ //Spalten durchgehen
            for (int j = 0; j < 6; j++){ //Zeilen durchgehen

                if (status[i][j].status == player) { //check horizontal
                    count_h++;
                    if (count_h == 3 && status[i][6].status == 0) return i;
                } else {
                    count_h = 0;
                }

                if (status[j][i].status == player) { //check vertical
                    count_v++;
                    if (count_v == 3 && j-3 > -1 && status[j-3][i].status == 0 ) return j-3;
                    if (count_v == 3 && j+1 < 7 && status[j+1][i].status ==0) return j+1;
                } else {
                    count_v = 0;
                }
            }
        }


        int random = (int)Math.floor(Math.random() * 6+0.5);

        while (status[random][6].status != 0){
            random = (int)Math.floor(Math.random() * 6+0.5);
        }
        System.out.println(random);
        return random;
    }

    public void onClick(View v) {
        Cell[][] status = null;
        try {
            status = gameBoard.getGameBorad();
        }catch (Exception ex){};

        switch (v.getId()) {
            case R.id.btnLogin:
                TextView username = (TextView) findViewById(R.id.loginUsername);
                TextView passwort = (TextView) findViewById(R.id.loginPasswort);
                //Wenn die Datenbank geht
                try {
                    if (SQLDatabase.getLoginTrue(username.getText().toString(), passwort.getText().toString())) {
                        createGeame(v);
                    } else {
                        Toast.makeText(this, "Username oder Passwort falsch", Toast.LENGTH_LONG).show();
                    }
                }catch (IOException ex){
                    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
                    dlgAlert.setTitle("Verbingsfehler");
                    dlgAlert.setMessage("Es konnte keine Verbindung zur Datenbank aufgebaut werden!");
                    dlgAlert.setPositiveButton("Verstanden",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            //Nichts
                        }
                    });
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();
                }
                break;

            case R.id.btnRegistieren:
                setContentView(R.layout.activity_register);
                break;

            case R.id.btnSaveRegister:
                TextView register_username = (TextView) findViewById(R.id.register_username);
                TextView register_passwort = (TextView) findViewById(R.id.register_passwort);
                try {
                    if (register_passwort.getText().toString().equals("") || register_username.getText().toString().equals(""))
                    {
                        Toast.makeText(this, "Bitte Usernamen und Passwort angeben!", Toast.LENGTH_LONG).show();
                    }
                    else{
                        SQLDatabase.createNewUser(register_username.getText().toString(), register_passwort.getText().toString());
                        Toast.makeText(this, "Der User wurde angelegt", Toast.LENGTH_LONG).show();
                        setContentView(R.layout.activity_login);
                        }
                }catch (SQLException ex)
                {
                    AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
                    dlgAlert.setTitle("Verbingsfehler");
                    dlgAlert.setMessage("Es konnte keine Verbindung zur Datenbank aufgebaut werden!");
                    dlgAlert.setPositiveButton("Verstanden",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            //Nichts
                        }
                    });
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();
                    register_passwort.setText("");
                }
                break;

            //region mode
            case R.id.btnSingleplayer:
                //TODO

                //Spielfeld erscheint
                findViewById(R.id.llGameColumns).setVisibility(View.VISIBLE);
                if (mode != 1){
                    findViewById(R.id.btnSingleplayer).setBackgroundColor(Color.RED);
                    findViewById(R.id.btnHotseat).setBackgroundColor(Color.BLUE);
                    findViewById(R.id.btnOnline).setBackgroundColor(Color.BLUE);
                    rest();
                }
                mode = 1;
                break;
            case R.id.btnHotseat:
                //Spielfeld erscheint
                findViewById(R.id.llGameColumns).setVisibility(View.VISIBLE);
                //Zuganzeige (textuell, visuell)
                findViewById(R.id.txtPlayer).setVisibility(View.VISIBLE);
                if (mode != 2){
                    findViewById(R.id.btnSingleplayer).setBackgroundColor(Color.BLUE);
                    findViewById(R.id.btnHotseat).setBackgroundColor(Color.RED);
                    findViewById(R.id.btnOnline).setBackgroundColor(Color.BLUE);
                    rest();
                }
                currentPlayer = true;
                mode = 2;
                break;
            case R.id.btnOnline:
                //TODO

                //Spielfeld erscheint

                findViewById(R.id.llGameColumns).setVisibility(View.VISIBLE);

                if (mode != 3){
                    findViewById(R.id.btnSingleplayer).setBackgroundColor(Color.BLUE);
                    findViewById(R.id.btnHotseat).setBackgroundColor(Color.BLUE);
                    findViewById(R.id.btnOnline).setBackgroundColor(Color.RED);
                    rest();
                }

                mode = 3;
                break;

            //endregion

            //region gameplay
            case R.id.btnColumn0:
                //stein setzen
                if (status[0][6].status != 0) break;
                putStone(0);
                setPlayer();
                if (mode == 1) putStone(random()); setPlayer();
                break;

            case R.id.btnColumn1:
                //stein setzen
                if (status[1][6].status != 0) break;
                putStone(1);
                setPlayer();
                if (mode == 1) putStone(random()); setPlayer();

                break;

            case R.id.btnColumn2:
                //stein setzen
                if (status[2][6].status != 0) break;
                putStone(2);
                setPlayer();
                if (mode == 1) putStone(random()); setPlayer();
                break;

            case R.id.btnColumn3:
                //stein setzen
                if (status[3][6].status != 0) break;
                putStone(3);
                setPlayer();
                if (mode == 1) putStone(random()); setPlayer();
                break;


            case R.id.btnColumn4:
                //stein setzen
                if (status[4][6].status != 0) break;
                putStone(4);
                setPlayer();
                if (mode == 1) putStone(random()); setPlayer();
                break;

            case R.id.btnColumn5:
                //stein setzen
                if (status[5][6].status != 0) break;
                putStone(5);
                setPlayer();
                if (mode == 1) putStone(random()); setPlayer();
                break;

            case R.id.btnColumn6:
                //stein setzen
                if (status[6][6].status != 0) break;
                putStone(6);
                setPlayer();
                if (mode == 1) putStone(random()); setPlayer();
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

    private void rest(){
        Cell[][] reset = gameBoard.getGameBorad();
        for (int i = 0; i <= 6; i++)
        {
            for (int a = 0; a <= 6; a++) {
                reset[i][a].status = 0;
                findViewById(1000+ i + (10*a)).setBackgroundColor(Color.BLACK);
            }
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
            rest();
        }
        currentPlayer = !currentPlayer;

        if (gameBoard.checkfull()){
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
            dlgAlert.setTitle("Unentschieden");
            dlgAlert.setMessage("Keiner hat gewonnen! Ein neues Spiel wurde erstellt.");
            dlgAlert.setPositiveButton("OK",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    //Nichts
                }
            });
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
            rest();
        }


    }

}
