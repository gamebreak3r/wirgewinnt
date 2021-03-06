package com.uebung_schule.wirgewinnt;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.widget.Button;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by consult on 23.01.2017.
 */

public class Multiplayer {

    //Game ID
    private int gameID;
    //If Player is in Game = true
    private boolean isInGame = false;
    //currentPlayer legend:
    // true  - Player 1
    // false - Player 2
    private boolean player;
    //Stops the thread
    private boolean stopTime = false;
    //Main View
    private View v;
    //Wait screen
    private ProgressDialog pd1;
    //MainActivity
    private MainActivity ma;


    protected Multiplayer(final MainActivity ma, Button button, View v) {
        //Set MainActivity
        this.ma = ma;
        //Set View
        this.v = v;
        //Show Popup with active Games
        final PopupMenu popup = new PopupMenu(ma, button);
        popup.getMenuInflater()
                .inflate(R.menu.multiplayer, popup.getMenu());

        //Get active Games ArrayList
        ArrayList games = PhpConnect.getActiveGames();
        if (games.size() == 0) {
            //If there isn't a active Game
            popup.getMenu().add(ma.getResources().getString(R.string.noGameFound));
            popup.getMenu().getItem(1).setEnabled(false);
        }
        for (int i = 0; games.size() > i; i++) {
            //Adds the active Games to the Popup Menu
            String[] stGame = games.get(i).toString().split("#");
            popup.getMenu().add("#" + stGame[0] + " " + stGame[1].toUpperCase());
        }

        //Listener Popup Menu
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getTitle().toString().equals(ma.getResources().getString(R.string.multiplayerCreateGame))) {
                    //Create new Game
                    gameID = PhpConnect.createNewGame(PhpConnect.username);
                    Toast.makeText(ma, ma.getResources().getString(R.string.gameID) + gameID, Toast.LENGTH_LONG).show();
                    //Player 1
                    player = true;
                    isInGame = true;
                } else {
                    //Join active Game
                    String[] getGameID = menuItem.getTitle().toString().split(" ");
                    String[] stGameID = getGameID[0].toString().split("#");
                    gameID = Integer.parseInt(stGameID[1]);
                    Toast.makeText(ma, ma.getResources().getString(R.string.gameID) + gameID, Toast.LENGTH_LONG).show();
                    //Player 2
                    player = false;
                    waitingPlayer();
                    //Remove Game from active Game List
                    PhpConnect.setGameInAvtive(gameID);
                    isInGame = true;
                    //Adds the Username to the Muliplayer Game
                    PhpConnect.joinGame(PhpConnect.username, gameID);
                }
                //User Can't leave the multiplayer
                ma.findViewById(R.id.btnHotseat).setClickable(false);
                ma.findViewById(R.id.btnSingleplayer).setClickable(false);
                //No Player Text
                ma.findViewById(R.id.txtPlayer).setVisibility(View.GONE);
                return true;
            }
        });
        popup.show();
    }

    //Put Stone in Multiplayer
    private void putStone(int column, int row) {
        //Get StoneID
        int stoneID = 1000 + column * 10 + row;
        int playerID;
        if (player) {
            playerID = 1;
        } else {
            playerID = 2;
        }
        //Sets the Stone in the DB
        PhpConnect.putStone(gameID, playerID, stoneID);
    }

    /* If a player sets a Stone
     * The nextPlayer on a different device is now able to
     * set a stone.
     */
    public void nextPlayer(int column, GameBoard gameboard) {
        if (isInGame) {
            //Count Stones in column
            int row = gameboard.stonesInColumn(column);
            //If row isn't full
            if (row <= 6) {
                gameboard.putStone(column, row, player, v);
                putStone(column, row);
            }
            //Check if the active Player has won the Game
            if (gameboard.checkIfWon(column, row, v)) {
                //Build a Win Message
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(v.getContext());
                dlgAlert.setTitle(ma.getResources().getString(R.string.winTitle));
                dlgAlert.setMessage(ma.getResources().getString(R.string.winGame));
                dlgAlert.setPositiveButton(ma.getResources().getString(R.string.OK), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //Remove the Message
                    }
                });
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
                //Shows the Stones
                ma.reset(true);
                //In the Multiplayer you can not restart a game directly
                //ma.findViewById(R.id.btnNewGame).setVisibility(View.GONE); gibt es nicht mehr
                //Player can'T set more stones.
                isInGame = false;
                //Game is out, Player has won.
                PhpConnect.setWin(gameID);
                //Show Player-Text
                ma.findViewById(R.id.txtPlayer).setVisibility(View.VISIBLE);
                //User Can leave the multiplayer
                ma.findViewById(R.id.btnHotseat).setClickable(true);
                ma.findViewById(R.id.btnSingleplayer).setClickable(true);
            } else {
                //Starts the Loding Screen
                waitingPlayer();
            }
        } else {
            //No active Game is selected, but the player
            //sets a Stone in the GameBoard
            ma.setPageHotSeat(ma.getResources().getString(R.string.createGame));
        }
    }

    //wating for the Player on a different device
    private void waitingPlayer() {
        //create the ProgressDialog max. 30 Seconds
        pd1 = new ProgressDialog(v.getContext());
        pd1.setTitle(ma.getResources().getString(R.string.enemy));
        pd1.setMessage(ma.getResources().getString(R.string.pleaseWait));
        pd1.setCanceledOnTouchOutside(false);
        pd1.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd1.setCancelable(false);
        pd1.setMax(100);
        pd1.show();
        readData();

        new Thread(new Runnable() {
            int value = 0;

            public void run() {
                for (; value <= 100; value++) {
                    if (stopTime) {
                        stopTime = false;
                        break;
                    }
                    try {
                        Thread.sleep(300);
                        pd1.setProgress(value);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                pd1.cancel();
                //Player hasn't set a Stone in 30 seconds
                //Game is now over.
                if (value >= 100) {
                    PhpConnect.setGameInAvtive(gameID);
                    //If the other player has already won the Game
                    if (!PhpConnect.setWin(gameID)) {
                        PhpConnect.setLose(gameID);
                        //Go back to the MainPanel
                        ma.setPageHotSeat(ma.getResources().getString(R.string.loseGame));
                        isInGame = false;
                    } else {
                        //Go back to the MainPanel
                        ma.setPageHotSeat(ma.getResources().getString(R.string.noStone));
                    }
                }
            }
        }).start();
    }

    //Start reading Data from DB
    private void readData() {
        new Thread(new Runnable() {
            public void run() {
                //If the other Player sets a Stone this value is true
                boolean gegnerSetStone = false;
                ArrayList enemyStone = null;
                for (int i = 0; i < 6; i++) {
                    try {
                        Thread.sleep(3000);
                        enemyStone = PhpConnect.getStoneID(gameID, player, ma.gameBoard);
                        //Player set a new stone
                        if (enemyStone.size() > 0) {
                            gegnerSetStone = true;
                            stopTime = true;
                            break;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (gegnerSetStone) {
                    gegnerSetStone = false;
                    //Put Stone from the other Player
                    for (int i = 0; i < enemyStone.size(); i++) {
                        //Calculates the field ids
                        //for example field ID: 1011
                        int col = (Integer.parseInt(enemyStone.get(i).toString()) / 10) % 10;
                        int row = Integer.parseInt(enemyStone.get(i).toString()) % 10;
                        ma.putStone(col, row, !player);
                    }
                    //Check if the other Player has already won the game
                    if (PhpConnect.checkIfWon(gameID)) {
                        PhpConnect.setLose(gameID);
                        //The Player can't set a new Stones
                        isInGame = false;
                        //
                        int col = (Integer.parseInt(enemyStone.get(enemyStone.size() - 1).toString()) / 10) % 10;
                        int row = Integer.parseInt(enemyStone.get(enemyStone.size() - 1).toString()) % 10;
                        //Reset GameBorad
                        ma.resetGameMultiplayer(col, row);
                        //Build a Lose Message
                        Looper.prepare();
                        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(v.getContext());
                        dlgAlert.setTitle(ma.getResources().getString(R.string.loseTitle));
                        dlgAlert.setMessage(ma.getResources().getString(R.string.loseGame));
                        dlgAlert.setPositiveButton(ma.getResources().getString(R.string.OK), new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                //Remove the Message
                            }
                        });
                        dlgAlert.create().show();
                        Looper.loop();
                    }
                }
                //Show Player-Text
                //ma.findViewById(R.id.txtPlayer).setVisibility(View.VISIBLE);
                //User Can leave the multiplayer
                ma.findViewById(R.id.btnHotseat).setClickable(true);
                ma.findViewById(R.id.btnSingleplayer).setClickable(true);
            }
        }).start();
    }

}