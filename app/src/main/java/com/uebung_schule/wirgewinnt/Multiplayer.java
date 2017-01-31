package com.uebung_schule.wirgewinnt;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.widget.Button;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.logging.Handler;

/**
 * Created by consult on 23.01.2017.
 */

public class Multiplayer {

    private int gameID;
    private boolean player;
    private boolean stopTime = false;
    public ProgressDialog pd1;
    private MainActivity ma;

    public Multiplayer(final MainActivity ma, Button button)
    {
        this.ma = ma;
        final PopupMenu popup = new PopupMenu(ma, button);
        popup.getMenuInflater()
                .inflate(R.menu.multiplayer, popup.getMenu());

        //Get Aktive Games
        ArrayList games = PhpConnect.getActiveGames();
        if (games.size() == 0)
        {
            popup.getMenu().add("Es wurde kein aktives Spiel gefunden!");
            popup.getMenu().getItem(1).setEnabled(false);
        }
        for(int i  = 0; games.size() > i; i++) {
            String[] stGame = games.get(i).toString().split("#");
            popup.getMenu().add("#" + stGame[0] + " " + stGame[1]);
        }
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getTitle().toString().equals(ma.getResources().getString(R.string.multiplayerCreateGame))){
                    gameID = PhpConnect.createNewGame(PhpConnect.username);
                    Toast.makeText(ma, "GameID: #" + gameID, Toast.LENGTH_LONG).show();
                    player = true;
                }
                else {
                    String[] getGameID = menuItem.getTitle().toString().split(" ");
                    String[] stGameID = getGameID[0].toString().split("#");
                    gameID = Integer.parseInt(stGameID[1]);
                    Toast.makeText(ma, "GameID: #" + gameID, Toast.LENGTH_LONG).show();
                    player = false;
                    //TODO Game inaktive setzen DB
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
        if (!PhpConnect.putStone(gameID, playerID, stoneID))
        {
            System.out.println("Fehler");
        }
    }

    public void nextPlayer (View v, int column, GameBoard gameboard) {
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
            pd1 = new ProgressDialog(v.getContext());
            pd1.setTitle("Dein Gegner ist an der Reihe!");
            pd1.setMessage("Bitte Warten...");
            pd1.setCanceledOnTouchOutside(false);
            pd1.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pd1.setMax(100);
            pd1.show();

            new Thread(new Runnable() {
                int value = 0;
                public void run() {
                    for (; value <= 100; value++) {
                        if (stopTime)
                        {
                            break;
                        }
                        try {
                            Thread.sleep(100);
                            pd1.setProgress(value);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    pd1.cancel();
                }
            }).start();
        }
    }

}
