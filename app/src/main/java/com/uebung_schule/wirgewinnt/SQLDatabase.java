package com.uebung_schule.wirgewinnt;

/**
 * Created by consult on 16.12.2016.
 */
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpRetryException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import android.os.StrictMode;
import android.util.Log;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import javax.net.ssl.HttpsURLConnection;

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
            String url = "http://wirgewinnt.square7.ch/html/index.php?username="+username;

                setConnection();
                String query = "INSERT INTO user (username, passwort) values ('" + username + "', '" + passwort + "')";
                PreparedStatement ps = con.prepareStatement(query);
                ps.executeUpdate();
                con.close();
        }

        public static boolean getLoginTrue (String username, String passwort) throws IOException{

            return true;
            /*String url = "http://wirgewinnt.square7.ch/html/login.php?username=" + username;
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet(url);

            ResponseHandler<String> responseHandler = new BasicResponseHandler();

            try {
                String response_str = client.execute(request, responseHandler);
                Log.println(Log.ERROR, "HTTPClient", "Antwort des Requests: " + response_str);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return false;*/
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
