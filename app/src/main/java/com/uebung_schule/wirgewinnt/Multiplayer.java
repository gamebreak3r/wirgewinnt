package com.uebung_schule.wirgewinnt;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;
import android.support.v7.app.ActionBarActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import java.util.ArrayList;

/**
 * Created by consult on 23.01.2017.
 */

public class Multiplayer {

    private int gameID;
    private boolean player;

    public Multiplayer(final MainActivity ma, Button button)
    {
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

    public void putStone(int column , int row, boolean player)
    {
        int stoneID = 1000+column*10+row;
        PhpConnect.putStone(gameID, player, stoneID);
    }

    public ProgressDialog pd1;
    public int value = 50;
    public int max = 200;
    public void nextPlayer (View v)
    {
        //TODO
        pd1 = new ProgressDialog(v.getContext());
        pd1.setTitle("Dein Gegner ist an der Reihe!");
        pd1.setMessage("Bitte Warten...");
        pd1.setCanceledOnTouchOutside(false);
        pd1.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd1.setMax(max);
        pd1.show();
        for (int i = 0; i < max; i++) {
            pd1.setProgress(value+i);
            if(max == value) {
                pd1.cancel();
            }
        }
    }

}
