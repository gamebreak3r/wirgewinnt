package com.uebung_schule.wirgewinnt;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.sql.SQLException;

public class MainActivity extends AppCompatActivity{

    GameBoard gameBoard;
    private Multiplayer mplayer;
    private Menu mainMenu;
    private View vw;
    boolean currentPlayer;
    private boolean login;
    public boolean ingame = true;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        mainMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_stats:
                setContentView(R.layout.activity_stats);
                TextView tv = (TextView) findViewById(R.id.text_stats);
                tv.setText(PHPConnect.username.toUpperCase() + " Stats" + ":");
                TextView tvWin = (TextView) findViewById(R.id.text_Wins);
                TextView tvLoses = (TextView) findViewById(R.id.text_Loses);
                TextView tvWinLose = (TextView) findViewById(R.id.text_WinLose);
                int wins = PHPConnect.getWins();
                int loses = PHPConnect.getLoses();
                double winLose = wins;
                if (loses != 0) {
                    winLose = wins / loses;
                }
                tvWin.setText(tvWin.getText().toString() + "  " + wins);
                tvLoses.setText(tvLoses.getText().toString() + "  " + loses);
                tvWinLose.setText(tvWinLose.getText().toString() + "  " + winLose);
                return true;
            case R.id.menu_logout:
                setContentView(R.layout.activity_login);
                mainMenu.findItem(R.id.menu_stats).setVisible(false);
                mainMenu.findItem(R.id.menu_logout).setVisible(false);
                login = false;
                return true;
        }
        return false;
    }

    protected void createGeame(View view) {
        setContentView(R.layout.activity_main);

        /*
        *
        *
        * hier wird das Spielfeld erstellt
        * und in jeder der sieben ImageViewes neu ImageViews eingefügt
        *
        *
        *
        *
        * */
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
        findViewById(R.id.btnHotseat).setBackgroundColor(Color.RED);
        findViewById(R.id.btnSingleplayer).setBackgroundColor(Color.BLUE);
        findViewById(R.id.btnOnline).setBackgroundColor(Color.BLUE);
        findViewById(R.id.txtPlayer).setBackgroundColor(Color.BLUE);
    }

    private int botmove() {
        Cell[][] status = gameBoard.getGameBorad();
        int count_h;
        int count_v;
        int player = 1;
        int count_d1 = 0;
        int count_d2 = 0;


        if(currentPlayer) player = 2;

        for (int i = 0; i < 7; i++){ //Spalten durchgehen
            count_v = 0;
            count_h = 0;
            for (int j = 0; j < 7; j++){ //Zeilen durchgehen

                if (status[i][j].status == player) { //check horizontal
                    count_h++;
                    if (count_h == 3 && j+1 < 7 && status[i][j+1].status == 0) return i;
                    if (count_h == 3) count_h = 0;
                } else {
                    count_h = 0;
                }

                if (status[j][i].status == player) { //check vertical
                    count_v++;

                    if (count_v == 3 && j-3 > -1 && status[j-3][i].status == 0 ) {
                        if (i-1 < 0 || status[j-3][i-1].status != 0) return j-3;
                    }
                    if (count_v == 3 && j+1 < 7 && status[j+1][i].status ==0) {
                        if (i-1 < 0 || status[j+1][i-1].status != 0) return j+1;
                    }
                    if (count_v == 3) count_v = 0;
                } else {
                    count_v = 0;
                }
            }
        }

        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                for (int k = 0; k < 3; k++){ //diago von unten links nach rechts oben
                    if (status[j+k][i+k].status == player){
                        count_d1++;

                        if (count_d1 == 3 && j+k-3 > -1 && i+k-3 > -1 ){
                            if (gameBoard.stonesInColumn(j+k-3)== i+k-2) return j+k-3;
                        }
                        if (count_d1 == 3 && j+k+1 < 7 && i+k+1 < 7){
                            if (gameBoard.stonesInColumn(j+k+1)== i+k-1) return j+k+1;
                        }
                        if (count_d1 == 3) count_d1 = 0;

                    } else {
                        count_d1 = 0;
                        break;
                    }
                }
                for (int k = 0; k < 3;  k++){ //diago von links oben nach rechts unten
                    if (status[j+k][i+3-k].status == player){
                        count_d2++;

                        if (count_d2 == 3 && j-1>-1 && i+1 < 7 ){
                            if (gameBoard.stonesInColumn(j-1)== i-1) return j-1;
                        }
                        if (count_d2 == 3 && j+k+1 < 7 && i-k-1 >-1){
                            if (gameBoard.stonesInColumn(j+k+1)== i-2) return j+k+1;
                        }
                        if (count_d2 == 3) count_d2 = 0;

                    } else {
                        count_d2 = 0;
                        break;
                    }
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
        this.vw = v;
        try {
            status = gameBoard.getGameBorad();
        }catch (Exception ex){}

        switch (v.getId()) {

            case R.id.activity_main:
                findViewById(R.id.activity_main).setClickable(false);
                reset(false);
                break;

            case R.id.btnLogin:
                TextView username = (TextView) findViewById(R.id.loginUsername);
                TextView passwort = (TextView) findViewById(R.id.loginPasswort);
                //Wenn die Datenbank geht
                try {
                    if (PHPConnect.getLoginTrue(username.getText().toString().trim().toLowerCase(), passwort.getText().toString().trim())) {
                        createGeame(v);
                        mainMenu.findItem(R.id.menu_stats).setVisible(true);
                        mainMenu.findItem(R.id.menu_logout).setVisible(true);
                        login = true;
                    } else {
                        Toast.makeText(this, "Username oder Passwort falsch", Toast.LENGTH_LONG).show();
                    }
                }catch (IllegalArgumentException ex)
                {
                    Toast.makeText(this, "Username oder Passwort falsch!", Toast.LENGTH_LONG).show();
                }
                catch (IOException ex){
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
                TextView register_passwort2 = (TextView) findViewById(R.id.register_passwort2);
                try {
                    if (register_passwort.getText().toString().equals("") || register_username.getText().toString().equals(""))
                    {
                        Toast.makeText(this, "Bitte Usernamen und Passwort angeben!", Toast.LENGTH_LONG).show();
                    }
                    else {
                        if (register_passwort.getText().toString().equals(register_passwort2.getText().toString())) {
                            try {
                                if (PHPConnect.createNewUser(register_username.getText().toString().trim().toLowerCase(), register_passwort.getText().toString())) {
                                    Toast.makeText(this, "Der User wurde angelegt", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(this, "Username bereits vergeben!", Toast.LENGTH_LONG).show();
                                }
                                setContentView(R.layout.activity_login);
                            }catch (IllegalArgumentException ex)
                            {
                                Toast.makeText(this, "User konnte nicht angelegt werden!", Toast.LENGTH_LONG).show();
                            }
                        }
                        else {
                            Toast.makeText(this, "Passwörter stimmen nicht überein.", Toast.LENGTH_LONG).show();
                        }
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
                    reset(false);
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
                    reset(false);
                }
                currentPlayer = true;
                mode = 2;
                break;
            case R.id.btnOnline:
                mplayer = new Multiplayer(this, (Button) findViewById(R.id.btnOnline), findViewById(R.id.activity_main));
                //findViewById(R.id.llGameColumns).setVisibility(View.VISIBLE);

                if (mode != 3){
                    findViewById(R.id.btnSingleplayer).setBackgroundColor(Color.BLUE);
                    findViewById(R.id.btnHotseat).setBackgroundColor(Color.BLUE);
                    findViewById(R.id.btnOnline).setBackgroundColor(Color.RED);
                    reset(false);
                }

                mode = 3;
                break;

            //endregion



            //region gameplay
            case R.id.btnColumn0:
                //stein setzen
                if (status[0][6].status != 0) break;
                if (mode == 3)
                {
                    mplayer.nextPlayer( 0, gameBoard);
                }else {
                    putStone(0);
                    setPlayer();
                    if (mode == 1 && ingame) putStone(botmove());

                }
                break;

            case R.id.btnColumn1:
                //stein setzen
                if (status[1][6].status != 0) break;

                if (mode == 3)
                {
                    mplayer.nextPlayer( 1, gameBoard);
                }else {
                    putStone(1);
                    setPlayer();
                    if (mode == 1 && ingame) putStone(botmove());

                }break;

            case R.id.btnColumn2:
                //stein setzen
                if (status[2][6].status != 0) break;
                if (mode == 3 )
                {
                    mplayer.nextPlayer( 2, gameBoard);
                }else {
                    putStone(2);
                    setPlayer();
                    if (mode == 1 && ingame) putStone(botmove());

                } break;

            case R.id.btnColumn3:
                //stein setzen
                if (status[3][6].status != 0) break;
                if (mode == 3)
                {
                    mplayer.nextPlayer(3, gameBoard);
                }else {
                    putStone(3);
                    setPlayer();
                    if (mode == 1 && ingame) putStone(botmove());

                }break;


            case R.id.btnColumn4:
                //stein setzen
                if (status[4][6].status != 0) break;
                if (mode == 3)
                {
                    mplayer.nextPlayer(4, gameBoard);
                }else {
                    putStone(4);
                    setPlayer();
                    if (mode == 1 && ingame) putStone(botmove());

                }break;

            case R.id.btnColumn5:
                //stein setzen
                if (status[5][6].status != 0) break;
                if (mode == 3)
                {
                    mplayer.nextPlayer( 5, gameBoard);
                }else {
                    putStone(5);
                    setPlayer();
                    if (mode == 1 && ingame) putStone(botmove());

                }break;

            case R.id.btnColumn6:
                //stein setzen
                if (status[6][6].status != 0) break;
                if (mode == 3)
                {
                    mplayer.nextPlayer( 6, gameBoard);
                }else {
                    putStone(6);
                    setPlayer();
                    if (mode == 1 && ingame) putStone(botmove());

                }break;
            //endregion
        }
    }

    private void locknewstones (boolean clickable){
        findViewById(R.id.btnColumn0).setClickable(clickable);
        findViewById(R.id.btnColumn1).setClickable(clickable);
        findViewById(R.id.btnColumn2).setClickable(clickable);
        findViewById(R.id.btnColumn3).setClickable(clickable);
        findViewById(R.id.btnColumn4).setClickable(clickable);
        findViewById(R.id.btnColumn5).setClickable(clickable);
        findViewById(R.id.btnColumn6).setClickable(clickable);
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

    /**
     * zuruecksetzung
     */
    protected void reset(boolean win){
        Cell[][] reset = gameBoard.getGameBorad();
        for (int i = 0; i <= 6; i++) {
            for (int a = 0; a <= 6; a++) {
               // System.out.println(i + a*10);
                if (win){
                    if (reset[i][a].status < 3) findViewById(1000+ a + (10*i)).setBackgroundColor(Color.BLACK);
                    findViewById(R.id.activity_main).setClickable(true);
                    ingame = false;
                    locknewstones(false);
                }else {
                    reset[i][a].status = 0;
                    findViewById(1000+ i + (10*a)).setBackgroundColor(Color.BLACK);
                    ingame = true;
                    locknewstones(true);
                }
            }
        }
    }

    protected void putStone(int column) {
        //spalten anschauen
        //steine zählen
        //draufsetzen
        int row = gameBoard.stonesInColumn(column);
        if (row <= 6) {
            gameBoard.putStone(column, row, currentPlayer, findViewById(R.id.activity_main));
        }
        if (gameBoard.checkIfWon(column, row, findViewById(R.id.activity_main))){
            String playerAusgabe;
            if (!currentPlayer) playerAusgabe="Spieler 1";
            else playerAusgabe="Spieler 2";

            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
            dlgAlert.setTitle("Gewonnen");
            dlgAlert.setMessage("Spieler: " + playerAusgabe +" hat gewonnen!");
            dlgAlert.setPositiveButton("new game",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    //Nichts
                }
            });
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
            reset(true);


        }
        //niemand hat gewonnen
        currentPlayer = !currentPlayer;

        //unentschieden
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
            reset(false);
            System.out.println();
        }
    }

    //For the MultiPlayer
    protected void putStone (final int col, final int row, final boolean player)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                gameBoard.putStone(col, row, player, findViewById(R.id.activity_main));
            }
        });
    }

    //ResetGame from MultiPlayer
    protected  void restGameMulitplayer (final int col, final int row)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                gameBoard.checkIfWon(col, row, findViewById(R.id.activity_main));
                reset(true);
            }
        });
    }

    //Startseite
    protected  void setPageHotSeat (final String showText)
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.btnHotseat).performClick();
                Toast.makeText(findViewById(R.id.activity_main).getContext(), showText, Toast.LENGTH_LONG).show();
            }
        });
    }

    //Zurück Button
    @Override
    public void onBackPressed() {
        if (login) {
            createGeame(vw);
        }else {
            setContentView(R.layout.activity_login);
        }
    }
}
