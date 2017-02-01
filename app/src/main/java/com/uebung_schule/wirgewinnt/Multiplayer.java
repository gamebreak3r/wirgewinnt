package com.uebung_schule.wirgewinnt;

import android.app.ProgressDialog;
import android.content.DialogInterface;
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

    private int gameID;
    private boolean player;
    private boolean stopTime = false;
    private View v;
    public ProgressDialog pd1;
    private MainActivity ma;

    public Multiplayer(final MainActivity ma, Button button, View v)
    {
        this.ma = ma;
        this.v = v;
        final PopupMenu popup = new PopupMenu(ma, button);
        popup.getMenuInflater()
                .inflate(R.menu.multiplayer, popup.getMenu());

        //Get Aktive Games

        //TODO User kann das Menü noch schliesen, die App Crasht daraufhin, da es keine id online gibt.
        ArrayList games = PHPConnect.getActiveGames();
        if (games.size() == 0)
        {
            popup.getMenu().add("Es wurde kein aktives Spiel gefunden!");
            popup.getMenu().getItem(1).setEnabled(false);
        }
        for(int i  = 0; games.size() > i; i++) {
            String[] stGame = games.get(i).toString().split("#");
            popup.getMenu().add("#" + stGame[0] + " " + stGame[1].toUpperCase());
        }
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getTitle().toString().equals(ma.getResources().getString(R.string.multiplayerCreateGame))){
                    gameID = PHPConnect.createNewGame(PHPConnect.username);
                    Toast.makeText(ma, "GameID: #" + gameID, Toast.LENGTH_LONG).show();
                    player = true;
                }
                else {
                    String[] getGameID = menuItem.getTitle().toString().split(" ");
                    String[] stGameID = getGameID[0].toString().split("#");
                    gameID = Integer.parseInt(stGameID[1]);
                    Toast.makeText(ma, "GameID: #" + gameID, Toast.LENGTH_LONG).show();
                    player = false;
                    waitingPlayer();
                    PHPConnect.setGameInAvtive(gameID);
                }
                return true;
        }
        });
        popup.show();
    }

    private void putStone(int column , int row)
    {
        int stoneID = 1000+column*10+row;
        int playerID;
        if (player)
        {
         playerID = 1;
        }
        else {
            playerID = 2;
        }
        if (!PHPConnect.putStone(gameID, playerID, stoneID))
        {
            System.out.println("Fehler");
        }
    }

    public void nextPlayer (int column, GameBoard gameboard) {
        int row = gameboard.stonesInColumn(column);
        if (row <= 6) {
            //In APP
            gameboard.putStone(column, row, player, v);
            //PhP
            putStone(column, row);
        }
        if (gameboard.checkIfWon(column, row, v)) {
            String playerAusgabe = null;
            playerAusgabe = "Du hast gewonnen!";

            AlertDialog.Builder dlgAlert = new AlertDialog.Builder(v.getContext());
            dlgAlert.setTitle("Gewonnen");
            dlgAlert.setMessage("Spieler: " + playerAusgabe + " hat gewonnen!");
            dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    //Nichts
                }
            });
            dlgAlert.setCancelable(true);
            dlgAlert.create().show();
            ma.rest();
        } else {
            waitingPlayer();
        }
    }

    private void waitingPlayer ()
    {
        pd1 = new ProgressDialog(v.getContext());
        pd1.setTitle("Dein Gegner ist an der Reihe!");
        pd1.setMessage("Bitte Warten...");
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
                    if (stopTime)
                    {
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
                if (value >= 100)
                {
                    PHPConnect.setGameInAvtive(gameID);
                    ma.setPageHotSeat("Der Gegner hat inerhalb von 30 Sek. keinen Stein gesetzt");
                }
            }
        }).start();
    }

    private void readData ()
    {
        new Thread(new Runnable() {
            public void run() {
                boolean gegnerSetStone = false;
                ArrayList gegnerStone = null;
                for (int i = 0; i < 6; i++) {
                    try {
                        Thread.sleep(5000);
                        gegnerStone = PHPConnect.getStoneID(gameID, player, ma.gameBoard);
                        System.out.println("Test " + gegnerStone.toString());
                        if (gegnerStone.size()>0)
                        {
                            gegnerSetStone = true;
                            stopTime = true;
                            break;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (gegnerSetStone)
                {
                    gegnerSetStone = false;
                    for (int i = 0; i < gegnerStone.size(); i++)
                    {
                        //1010
                        int col = (Integer.parseInt(gegnerStone.get(i).toString())/10) % 10;
                        int row = Integer.parseInt(gegnerStone.get(i).toString()) % 10;
                        ma.putStone(col, row, !player);
                    }
                }
                else {
                    System.out.println("Der Gegner hat in 30 Sek. keinen Stein gesetzt");
                }
            }
        }).start();
    }

}