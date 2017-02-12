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
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {

    protected GameBoard gameBoard;
    //Instance from Multiplayer
    private Multiplayer mplayer;
    //Instance from Stats/Logout Menu
    private Menu mainMenu;
    //Active View
    private View vw;
    //currentPlayer legend:
    // true  - Player 1
    // false - Player 2
    boolean currentPlayer;
    //If user is Loged in true
    private boolean login;
    public boolean ingame = true;

    int mode = 0;
    //mode legend:
    //0 = nothing selected
    //1 = Singelplayer
    //2 = Hotseat
    //3 = Online

    //Login Screen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    //Create the Option Menu for the Stats and Logout
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        mainMenu = menu;
        return true;
    }

    //Option Items for the Main Menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_stats:
                //Screen stats
                setContentView(R.layout.activity_stats);
                TextView tv = (TextView) findViewById(R.id.text_stats);
                tv.setText(PhpConnect.username.toUpperCase() + " " + getResources().getString(R.string.menuStats) + ":");
                TextView tvWin = (TextView) findViewById(R.id.text_Wins);
                TextView tvLoses = (TextView) findViewById(R.id.text_Loses);
                TextView tvGames = (TextView) findViewById(R.id.text_games);
                int wins = PhpConnect.getWins();
                int loses = PhpConnect.getLoses();
                //How many games has a user
                int games = wins + loses;
                tvWin.setText(tvWin.getText().toString() + "  " + wins);
                tvLoses.setText(tvLoses.getText().toString() + "  " + loses);
                tvGames.setText(tvGames.getText().toString() + "  " + games);
                return true;
            case R.id.menu_logout:
                //Back to the login Screen
                setContentView(R.layout.activity_login);
                mainMenu.findItem(R.id.menu_stats).setVisible(false);
                mainMenu.findItem(R.id.menu_logout).setVisible(false);
                login = false;
                return true;
        }
        return false;
    }

    //Create the Game with the ImageViews
    protected void createGame(View view) {
        setContentView(R.layout.activity_main);
        CreateGame.createGame(view, this);
    }

    private int botmove() {
        //bot will try to prevent the player to win if the bot cant prevent something it will set a random stone
        Cell[][] status = gameBoard.getGameBorad();
        int count_h;
        int count_v;
        int player = 1;
        int count_d1 = 0;
        int count_d2 = 0;

        if (currentPlayer) player = 2;

        for (int i = 0; i < 7; i++) { //go trough columns
            count_v = 0;
            count_h = 0;
            for (int j = 0; j < 7; j++) { //go trough rows

                if (status[i][j].status == player) { //check horizontal
                    count_h++;
                    if (count_h == 3 && j + 1 < 7 && status[i][j + 1].status == 0) return i;
                    if (count_h == 3) count_h = 0;
                } else {
                    count_h = 0;
                }

                if (status[j][i].status == player) { //check vertical
                    count_v++;

                    if (count_v == 3 && j - 3 > -1 && status[j - 3][i].status == 0) {
                        if (i - 1 < 0 || status[j - 3][i - 1].status != 0) return j - 3;
                    }
                    if (count_v == 3 && j + 1 < 7 && status[j + 1][i].status == 0) {
                        if (i - 1 < 0 || status[j + 1][i - 1].status != 0) return j + 1;
                    }
                    if (count_v == 3) count_v = 0;
                } else {
                    count_v = 0;
                }
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 3; k++) { //diago von unten links nach rechts oben
                    if (status[j + k][i + k].status == player) {
                        count_d1++;

                        if (count_d1 == 3 && j + k - 3 > -1 && i + k - 3 > -1) {
                            if (gameBoard.stonesInColumn(j + k - 3) == i + k - 2) return j + k - 3;
                        }
                        if (count_d1 == 3 && j + k + 1 < 7 && i + k + 1 < 7) {
                            if (gameBoard.stonesInColumn(j + k + 1) == i + k - 1) return j + k + 1;
                        }
                        if (count_d1 == 3) count_d1 = 0;

                    } else {
                        count_d1 = 0;
                        break;
                    }
                }
                for (int k = 0; k < 3; k++) { //diago von links oben nach rechts unten
                    if (status[j + k][i + 3 - k].status == player) {
                        count_d2++;

                        if (count_d2 == 3 && j - 1 > -1 && i + 1 < 7) {
                            if (gameBoard.stonesInColumn(j - 1) == i - 1) return j - 1;
                        }
                        if (count_d2 == 3 && j + k + 1 < 7 && i - k - 1 > -1) {
                            if (gameBoard.stonesInColumn(j + k + 1) == i - 2) return j + k + 1;
                        }
                        if (count_d2 == 3) count_d2 = 0;

                    } else {
                        count_d2 = 0;
                        break;
                    }
                }
            }
        }

        int random = (int) Math.floor(Math.random() * 6 + 0.5);
        while (status[random][6].status != 0) {
            random = (int) Math.floor(Math.random() * 6 + 0.5);
        }
        return random;
    }

    /*
    If a player clicks a Button, this action will called
     */
    public void onClick(View v) {
        //Create Status Array
        Cell[][] status = null;
        this.vw = v;
        try {
            status = gameBoard.getGameBorad();
        } catch (Exception ex) {
            //Nothing
        }

        switch (v.getId()) {
            //if the game has ended the clicking on the linearlayout will reset the gameboard
            case R.id.activity_main:
                findViewById(R.id.activity_main).setClickable(false);
                reset(false);
                break;

            case R.id.btnLogin:
                //Get Username
                TextView username = (TextView) findViewById(R.id.loginUsername);
                //Get Password
                TextView passwort = (TextView) findViewById(R.id.loginPasswort);
                try {
                    //send the password and the username to the Server
                    //Server returns true if it is correct
                    if (PhpConnect.getLoginTrue(username.getText().toString().trim().toLowerCase(), passwort.getText().toString().trim())) {
                        createGame(v);
                        //User can see now the Stats and logout buttons
                        mainMenu.findItem(R.id.menu_stats).setVisible(true);
                        mainMenu.findItem(R.id.menu_logout).setVisible(true);
                        //login ist true, so user can use the back button
                        login = true;
                    } else {
                        Toast.makeText(this, getResources().getString(R.string.wrongUser), Toast.LENGTH_LONG).show();
                    }
                } catch (IllegalArgumentException ex) {
                    Toast.makeText(this, getResources().getString(R.string.wrongUser), Toast.LENGTH_LONG).show();
                } catch (IOException ex) {
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
                    dlgAlert.setTitle(getResources().getString(R.string.dbError));
                    dlgAlert.setPositiveButton(getResources().getString(R.string.OK), new DialogInterface.OnClickListener() {
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
                TextView register_password = (TextView) findViewById(R.id.register_passwort);
                TextView register_password2 = (TextView) findViewById(R.id.register_passwort2);
                try {
                    if (register_password.getText().toString().equals("") || register_username.getText().toString().equals("")) {
                        Toast.makeText(this, getResources().getString(R.string.enterUserPassword), Toast.LENGTH_LONG).show();
                    } else {
                        if (register_password.getText().toString().equals(register_password2.getText().toString())) {
                            try {
                                if (PhpConnect.createNewUser(register_username.getText().toString().trim().toLowerCase(), register_password.getText().toString())) {
                                    Toast.makeText(this, getResources().getString(R.string.createUser), Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(this, getResources().getString(R.string.forgiveUser), Toast.LENGTH_LONG).show();
                                }
                                setContentView(R.layout.activity_login);
                            } catch (IllegalArgumentException ex) {
                                Toast.makeText(this, getResources().getString(R.string.dbError), Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(this, getResources().getString(R.string.passwortIncorrect), Toast.LENGTH_LONG).show();
                        }
                    }

                } catch (SQLException ex) {
                    AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
                    dlgAlert.setTitle(getResources().getString(R.string.dbError));
                    dlgAlert.setPositiveButton(getResources().getString(R.string.OK), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            //Nothing
                        }
                    });
                    dlgAlert.setCancelable(true);
                    dlgAlert.create().show();
                    register_password.setText("");
                }
                break;

            //region mode
            //switches to singleplayer
            case R.id.btnSingleplayer:
                findViewById(R.id.llGameColumns).setVisibility(View.VISIBLE);
                if (mode != 1) {
                    findViewById(R.id.btnSingleplayer).setBackgroundColor(Color.RED);
                    findViewById(R.id.btnHotseat).setBackgroundColor(Color.BLUE);
                    findViewById(R.id.btnOnline).setBackgroundColor(Color.BLUE);
                    reset(false);
                }
                mode = 1;
                break;
            //switches to multiplayer on one device
            case R.id.btnHotseat:
                findViewById(R.id.llGameColumns).setVisibility(View.VISIBLE);
                findViewById(R.id.txtPlayer).setVisibility(View.VISIBLE);
                if (mode != 2) {
                    findViewById(R.id.btnSingleplayer).setBackgroundColor(Color.BLUE);
                    findViewById(R.id.btnHotseat).setBackgroundColor(Color.RED);
                    findViewById(R.id.btnOnline).setBackgroundColor(Color.BLUE);
                    reset(false);
                }
                currentPlayer = true;
                mode = 2;
                break;
            // switches to the online mode
            case R.id.btnOnline:
                mplayer = new Multiplayer(this, (Button) findViewById(R.id.btnOnline), findViewById(R.id.activity_main));
                //findViewById(R.id.llGameColumns).setVisibility(View.VISIBLE);
                if (mode != 3) {
                    findViewById(R.id.btnSingleplayer).setBackgroundColor(Color.BLUE);
                    findViewById(R.id.btnHotseat).setBackgroundColor(Color.BLUE);
                    findViewById(R.id.btnOnline).setBackgroundColor(Color.RED);
                    reset(false);
                }
                mode = 3;
                break;
            //endregion

            //region gameplay
            // puts a stone in clicked column
            case R.id.btnColumn0:

                if (status[0][6].status != 0) break;
                if (mode == 3) {
                    mplayer.nextPlayer(0, gameBoard);
                } else {
                    putStone(0);
                    setPlayer();
                    if (mode == 1 && ingame) putStone(botmove());
                }
                break;

            case R.id.btnColumn1:

                if (status[1][6].status != 0) break;
                if (mode == 3) {
                    mplayer.nextPlayer(1, gameBoard);
                } else {
                    putStone(1);
                    setPlayer();
                    if (mode == 1 && ingame) putStone(botmove());
                }
                break;

            case R.id.btnColumn2:

                if (status[2][6].status != 0) break;
                if (mode == 3) {
                    mplayer.nextPlayer(2, gameBoard);
                } else {
                    putStone(2);
                    setPlayer();
                    if (mode == 1 && ingame) putStone(botmove());
                }
                break;

            case R.id.btnColumn3:

                if (status[3][6].status != 0) break;
                if (mode == 3) {
                    mplayer.nextPlayer(3, gameBoard);
                } else {
                    putStone(3);
                    setPlayer();
                    if (mode == 1 && ingame) putStone(botmove());
                }
                break;

            case R.id.btnColumn4:

                if (status[4][6].status != 0) break;
                if (mode == 3) {
                    mplayer.nextPlayer(4, gameBoard);
                } else {
                    putStone(4);
                    setPlayer();
                    if (mode == 1 && ingame) putStone(botmove());
                }
                break;

            case R.id.btnColumn5:

                if (status[5][6].status != 0) break;
                if (mode == 3) {
                    mplayer.nextPlayer(5, gameBoard);
                } else {
                    putStone(5);
                    setPlayer();
                    if (mode == 1 && ingame) putStone(botmove());
                }
                break;

            case R.id.btnColumn6:

                if (status[6][6].status != 0) break;
                if (mode == 3) {
                    mplayer.nextPlayer(6, gameBoard);
                } else {
                    putStone(6);
                    setPlayer();
                    if (mode == 1 && ingame) putStone(botmove());
                }
                break;
            //endregion
        }
    }

    // locks buttons to prevent new stones if game already has ended
    private void lockNewStones(boolean clickable) {
        findViewById(R.id.btnColumn0).setClickable(clickable);
        findViewById(R.id.btnColumn1).setClickable(clickable);
        findViewById(R.id.btnColumn2).setClickable(clickable);
        findViewById(R.id.btnColumn3).setClickable(clickable);
        findViewById(R.id.btnColumn4).setClickable(clickable);
        findViewById(R.id.btnColumn5).setClickable(clickable);
        findViewById(R.id.btnColumn6).setClickable(clickable);
    }

    private void setPlayer() { //switches between the two players
        if (currentPlayer) {
            findViewById(R.id.txtPlayer).setBackgroundColor(Color.RED);
            ((TextView) findViewById(R.id.txtPlayer)).setText(getResources().getString(R.string.player2Turn));
        } else {
            findViewById(R.id.txtPlayer).setBackgroundColor(Color.BLUE);
            ((TextView) findViewById(R.id.txtPlayer)).setText(getResources().getString(R.string.player1Turn));
        }
    }

    /**
     * Reset the stones
     */
    protected void reset(boolean win) { //resets the gameboard and can show the deciding stones
        Cell[][] reset = gameBoard.getGameBorad();
        for (int i = 0; i <= 6; i++) {
            for (int a = 0; a <= 6; a++) {
                if (win) { // resets every stone without the deciding ones
                    if (reset[i][a].status < 3)
                        findViewById(1000 + a + (10 * i)).setBackgroundColor(Color.BLACK);
                    findViewById(R.id.activity_main).setClickable(true);
                    ingame = false;
                    lockNewStones(false);
                } else {// resets every stone
                    reset[i][a].status = 0;
                    findViewById(1000 + i + (10 * a)).setBackgroundColor(Color.BLACK);
                    ingame = true;
                    lockNewStones(true);
                }
            }
        }
    }

    protected void putStone(int column) {//checks the current column for the next stone to place
        int row = gameBoard.stonesInColumn(column);
        if (row <= 6) {// check for left space in column
            gameBoard.putStone(column, row, currentPlayer, findViewById(R.id.activity_main));
        }
        if (gameBoard.checkIfWon(column, row, findViewById(R.id.activity_main))) { //gives the function the needed parameters to check for end
            //if game has ended an alert is shown on the screen with the winning player
            String playerAusgabe = getResources().getString(R.string.player2);
            if (!currentPlayer) playerAusgabe = getResources().getString(R.string.player1);

            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
            dlgAlert.setTitle(getResources().getString(R.string.win));
            dlgAlert.setMessage(getResources().getString(R.string.player) + " " + playerAusgabe + " " + getResources().getString(R.string.hasWon));
            dlgAlert.setPositiveButton(getResources().getString(R.string.OK), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    //Nothing
                }
            });
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
            reset(true); //resets gameboard without the deciding stones


        }
        currentPlayer = !currentPlayer; //changes the current player

        //draw
        if (gameBoard.checkFull()) { //if the board is full it will be reseted and shows an alert with little information
            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);
            dlgAlert.setTitle(getResources().getString(R.string.draw));
            dlgAlert.setMessage(getResources().getString(R.string.drawMessage));
            dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    //Nothing
                }
            });
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
            reset(false);
        }
    }

    //For the MultiPlayer
    protected void putStone(final int col, final int row, final boolean player) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                gameBoard.putStone(col, row, player, findViewById(R.id.activity_main));
            }
        });
    }

    //ResetGame from MultiPlayer
    protected void resetGameMultiplayer(final int col, final int row) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                gameBoard.checkIfWon(col, row, findViewById(R.id.activity_main));
                reset(true);
            }
        });
    }

    //Main Page
    protected void setPageHotSeat(final String showText) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                findViewById(R.id.btnHotseat).performClick();
                Toast.makeText(findViewById(R.id.activity_main).getContext(), showText, Toast.LENGTH_LONG).show();
            }
        });
    }

    //Back Button
    @Override
    public void onBackPressed() {
        if (login) {
            createGame(vw);
        } else {
            setContentView(R.layout.activity_login);
        }
    }
}
