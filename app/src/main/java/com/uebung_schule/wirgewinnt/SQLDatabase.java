package com.uebung_schule.wirgewinnt;

/**
 * Created by consult on 16.12.2016.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLDatabase {

        //MySQL Daten für das Login
        private static String ip = "eu-cdbr-azure-west-a.cloudapp.net";
        private static String db = "wirgewinnt";
        private static String username = "b4908f7592fbb4";
        private static String passwort = "81a99d4e";
        private static Connection conn;

        public static void setConnection ()
        {
            try {
                // CREATE TABLE AppUser (userID int NOT NULL AUTO_INCREMENT PRIMARY KEY, username varchar(50) NOT NULL, email varchar(100), passwort varchar(100), punkte int, lastlogin varchar(50));
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                String url = "jdbc:mysql://"+ip+"/"+db;
                conn = DriverManager.getConnection(url, username, passwort);
            }
            catch (Exception e ) {
                e.printStackTrace();
            }
        }

        public static void createNewUser (String passwort, String username)
        {
            try {
                String query = "INSERT INTO Users SET (user=" + username + ", passwort=" + passwort + ");";
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery(query);
                rs.next();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

        public static boolean getLoginTrue (String username, String passwort)
        {
        try {
            String query = "Select passwort, playerID from Users where username='" + username + "'";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next());
             if (rs.getString("passwort").equals(passwort)){
                //Hier muss ein new Player erzeugt werden.
                rs.getInt("playerID");
                return true;
             }
        }catch (SQLException e) {
            return false;
        }
        return false;
        }

        public static boolean isUserExisting (String username)
        {
            try {
                String query = "Select * from Users where username='" + username + "'";
                Statement st = conn.createStatement();
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
