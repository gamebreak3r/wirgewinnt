package com.uebung_schule.wirgewinnt;

/**
 * Created by consult on 16.12.2016.
 */

import android.annotation.TargetApi;
import android.os.Build;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * This Class is for the Connection to the Server via a php script
 */
public class PHPConnect {

    /**
     * User Register and Login
     */
    //If User is logged in, here is his static username
    public static String username;

    //Create a new User in the DB
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public static boolean createNewUser(String username, String passwort) throws SQLException {
        try {
            String send = "http://wirgewinnt.square7.ch/html/user.php?Rusername=" + username + "&Rpasswort=" + passwort;
            //Check if User has a blank in his username or password
            if (send.contains(" ")) {
                return false;
            }
            String output = new getURLData().execute(send).get();
            if (output.contains("true")) {
                return true;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Check if the username and the password is correct
    //If the output contains a true, the username is right
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public static boolean getLoginTrue(String username, String password) throws IOException {
        try {
            String send = "http://wirgewinnt.square7.ch/html/user.php?Lusername=" + username + "&Lpasswort=" + password;
            //Check if User has a blank in his username or password
            if (send.contains(" ")) {
                return false;
            }
            String output = new getURLData().execute(send).get();
            if (output.contains("true")) {
                //sets the username
                PHPConnect.username = username;
                return true;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Multiplayer
     */

    //Create a new Multiplayer Game
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public static int createNewGame(String playerName) {
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

    //Join a existing Game
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public static void joinGame(String playerName, int gameID) {
        String output = null;
        try {
            output = new getURLData()
                    .execute("http://wirgewinnt.square7.ch/html/multiplayer.php?joinGameName=" + playerName + "&gameID=" + gameID)
                    .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    //Put the Stone
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public static boolean putStone(int gameID, int player, int stoneID) {
        Boolean back = false;
        String output = null;
        try {
            output = new getURLData()
                    .execute("http://wirgewinnt.square7.ch/html/multiplayer.php?gameID=" + gameID + "&player=" + player + "&putStone=" + stoneID)
                    .get();
            if (output.contains("true")) {
                back = true;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return back;
    }

    //Returns the active Games with the username of the host.
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public static ArrayList getActiveGames() {
        ArrayList back = new ArrayList();
        String output = null;
        try {
            output = new getURLData()
                    .execute("http://wirgewinnt.square7.ch/html/multiplayer.php?getActiveGames=true")
                    .get();
            String[] sp = output.split(";");
            for (int i = 1; i + 1 < sp.length; i++) {
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

    //Sets a Game inactive
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public static boolean setGameInAvtive(int gameID) {
        String output = null;
        try {
            output = new getURLData()
                    .execute("http://wirgewinnt.square7.ch/html/multiplayer.php?removeGame=" + gameID)
                    .get();
            if (output.contains("true")) {
                return true;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Returns the stoneIDs
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public static ArrayList getStoneID(int gameID, boolean player, GameBoard gb) {
        ArrayList back = new ArrayList();
        String output = null;
        try {
            output = new getURLData()
                    .execute("http://wirgewinnt.square7.ch/html/multiplayer.php?getStones=" + gameID)
                    .get();
            String[] sp = output.split("##");
            String[] player1 = sp[1].split(";");
            String[] player2 = sp[2].split(";");
            int stones = 1;
            for (int i = 0; i < 7; i++) {
                stones = stones + gb.stonesInColumn(i);
            }
            if (stones == (player1.length + player2.length - 2)) {
                if (player) {
                    for (int i = 1; i < player2.length; i++) {
                        back.add(player2[i].toString());
                    }
                } else {
                    for (int i = 1; i < player1.length; i++) {
                        back.add(player1[i].toString());
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

    //Sets the Player how wins the Game
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public static boolean setWin(int gameID) {
        try {
            String output = new getURLData()
                    .execute("http://wirgewinnt.square7.ch/html/multiplayer.php?gameID=" + gameID + "&winPlayer=" + username)
                    .get();
            if (output.contains("true")) {
                return true;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Sets the player how loses the Game
    public static boolean setLose(int gameID) {
        try {
            String output = new getURLData()
                    .execute("http://wirgewinnt.square7.ch/html/multiplayer.php?gameID=" + gameID + "&losePlayer=" + username)
                    .get();
            if (output.contains("true")) {
                return true;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Check if the other player has already won the Game
    public static boolean checkIfWon(int gameID) {
        try {
            String output = new getURLData()
                    .execute("http://wirgewinnt.square7.ch/html/multiplayer.php?gameID=" + gameID + "&hasWon=win")
                    .get();
            //If the output contains true, the other player has already won!
            if (output.contains("true")) {
                return true;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Stats
     */

    //retuns the wins from the Player
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public static int getWins() {
        try {
            //Only the Stats from the active Player can show here!
            String output = new getURLData()
                    .execute("http://wirgewinnt.square7.ch/html/stats.php?player=" + username + "&winLose=win")
                    .get();
            String[] sp = output.split("##");
            return Integer.parseInt(sp[1].toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
            //player hasn't got Stats
        }
        return 0;
    }

    //retuns the Loses from the active Player
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public static int getLoses() {
        try {
            String output = new getURLData()
                    .execute("http://wirgewinnt.square7.ch/html/stats.php?player=" + username + "&winLose=lose")
                    .get();
            if (output.contains("##")) {
                String[] sp = output.split("##");
                return Integer.parseInt(sp[1].toString());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
            //player hasn't got Stats
        }
        return 0;
    }
}
