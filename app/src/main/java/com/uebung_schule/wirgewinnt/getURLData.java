package com.uebung_schule.wirgewinnt;

import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

/**
 * Created by consult on 15.01.2017.
 */

public class getURLData extends AsyncTask<String, Integer, String> {
    @Override
    protected String doInBackground(String... params) {
        String line;
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(params[0]);

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            line = EntityUtils.toString(httpEntity);

        } catch (UnsupportedEncodingException e) {
            line = "Error";
        } catch (MalformedURLException e) {
            line = "Error";
        } catch (IOException e) {
            line = "Error";
        }
        return line;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
    }

}
