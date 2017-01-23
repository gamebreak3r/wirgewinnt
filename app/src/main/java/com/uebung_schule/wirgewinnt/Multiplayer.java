package com.uebung_schule.wirgewinnt;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

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
            popup.getMenu().add("#" + games.get(i));
        }
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if (menuItem.getTitle().toString().equals(ma.getResources().getString(R.string.multiplayerCreateGame))){
                    gameID = PhpConnect.createNewGame();
                    Toast.makeText(ma, "GameID: #" + gameID, Toast.LENGTH_LONG).show();
                }
                return true;
        }
        });
        popup.show();
    }

}
