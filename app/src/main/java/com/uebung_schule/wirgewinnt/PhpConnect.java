package com.uebung_schule.wirgewinnt;

/**
 * Created by consult on 16.12.2016.
 */
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;

public class PhpConnect {

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
                    back = true;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return back;
        }
}
