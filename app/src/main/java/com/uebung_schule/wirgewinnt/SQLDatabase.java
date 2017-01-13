package com.uebung_schule.wirgewinnt;

/**
 * Created by consult on 16.12.2016.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import android.os.StrictMode;

public class SQLDatabase {

        //MySQL Daten für das Login
        private static String ip = "wirgewinnt.square7.ch";
        private static String db = "wirgewinnt";
        private static String username = "wirgewinnt";
        private static String passwort = "ABCD";
        private static Connection con;

        public static void setConnection ()
        {
            try {
                if (android.os.Build.VERSION.SDK_INT > 9) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                }
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                con=DriverManager.getConnection("jdbc:mysql://"+ip+":3306/"+db, username, passwort);
            }
            catch (Exception e ) {
                e.printStackTrace();
            }
        }

        public static void createNewUser (String passwort, String username) throws SQLException
        {
                setConnection();
                String query = "INSERT INTO user (username, passwort) values ('" + username + "', '" + passwort + "')";
                PreparedStatement ps = con.prepareStatement(query);
                ps.executeUpdate();
                con.close();
        }

        public static boolean getLoginTrue (String username, String passwort) throws SQLException {
            setConnection();
            String query = "Select passwort, username from user where username='" + username + "'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                if (rs.getString("passwort").equals(passwort)) {
                    con.close();
                    return true;
                }
            }
            con.close();
            return false;
        }

        public static boolean isUserExisting (String username)
        {
            try {
                String query = "Select * from Users where username='" + username + "'";
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(query);
                while (rs.next()){
                    return true;
                }
            }catch (SQLException e) {
                return false;
            }
            return false;
        }
}
