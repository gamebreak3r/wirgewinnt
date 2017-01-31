package com.uebung_schule.wirgewinnt;

/**
 * Created by consult on 16.12.2016.
 */
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class PhpConnect {

        public static String username;

        public static boolean createNewUser (String passwort, String username) throws SQLException
        {
            Boolean back = false;
            try {
                String output = new getURLData()
                        .execute("http://wirgewinnt.square7.ch/html/user.php?Rusername=" + username + "&Rpasswort=" + passwort)
                        .get();
                if (output.contains("true"))
                {
                   back = true;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return back;
        }

        public static boolean getLoginTrue (String username, String passwort) throws IOException{

            Boolean back = false;
            try {
                String output = new getURLData()
                                .execute("http://wirgewinnt.square7.ch/html/user.php?Lusername=" + username + "&Lpasswort=" + passwort)
                                .get();
                if (output.contains("true"))
                {
                    PhpConnect.username = username;
                    back = true;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return back;
        }


    /**
     * Multiplayer
     */

    public static int createNewGame(String playerName)
    {
        int gameID = 0;
        String output = null;
        try {
            output = new getURLData()
                    .execute("http://wirgewinnt.square7.ch/html/multiplayer.php?createGame=" + playerName)
                    .get();
            String[] back = output.split(";");
            gameID = Integer.parseInt(back[1]);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return gameID;
    }

    public static boolean putStone(int gameID, int player, int stoneID)
    {
        Boolean back = false;
        String output = null;
        try {
            output = new getURLData()
                    .execute("http://wirgewinnt.square7.ch/html/multiplayer.php?gameID=" + gameID + "&player=" + player +"&putStone=" + stoneID)
                    .get();
            if (output.contains("true"))
            {
                back = true;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return back;
    }
    public static ArrayList getActiveGames()
    {
        ArrayList back = new ArrayList();
        String output = null;
        try {
            output = new getURLData()
                    .execute("http://wirgewinnt.square7.ch/html/multiplayer.php?getActiveGames=true")
                    .get();
            String[] sp = output.split(";");
            for(int i = 1 ; i +1 < sp.length ; i++) {
                back.add(sp[i].toString());
                //Wegen Leerfelder duch die 2x ;;
                i++;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return back;
    }

    public static boolean setGameInAvtive (int gameID)
    {
        String output = null;
        try {
            output = new getURLData()
                    .execute("http://wirgewinnt.square7.ch/html/multiplayer.php?removeGame=" + gameID)
                    .get();
            if (output.contains("true"))
            {
                return true;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static ArrayList getStoneID (int gameID, boolean player, GameBoard gb)
    {
        ArrayList back = new ArrayList();
        String output = null;
        try {
            output = new getURLData()
                    .execute("http://wirgewinnt.square7.ch/html/multiplayer.php?getStones=" + gameID)
                    .get();
            String[] sp = output.split("##");
            //TODO Nachsehen was hier genau ankommt!
            String[] player1 = sp[1].split(";");
            String[] player2 = sp[2].split(";");
            int stones = 1;
            for (int i = 0; i < 7; i++) {
                stones = stones+gb.stonesInColumn(i);
            }
            if (stones == player1.length + player2.length) {
                if (player) {
                    for (int i = 1; i + 1 < player1.length; i++) {
                        back.add(sp[i].toString());
                        //Wegen Leerfelder duch die 2x ;;
                        i++;
                    }
                } else {
                    for (int i = 1; i + 1 < player2.length; i++) {
                        back.add(sp[i].toString());
                        //Wegen Leerfelder duch die 2x ;;
                        i++;
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return back;
    }

}
