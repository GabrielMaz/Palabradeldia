package com.palabradeldia.palabradeldia.services;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.palabradeldia.palabradeldia.activities.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadTask extends AsyncTask<String, String, Word> {
    ProgressDialog progressDialog;
    private Activity activity;

    public DownloadTask(Activity activity) {
        this.activity = activity;
    }

    @Override

    protected Word doInBackground(String... params) {

        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(params[0]);
            //reader= new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            // Parseamos la respuesta obtenida del servidor a un objeto JSON
            JSONObject jsonObject = new JSONObject(buffer.toString());
            JSONArray palabras = jsonObject.getJSONArray("infoPal");

            JSONObject pal = palabras.getJSONObject(0);//Como el json retorna un array de una posicion, seleccionamos la posicion 0
            // Creamos el objeto Word
            //Word p = (int, string, string, int, int, int) - id, palabra, descripcion, like, nolike, habilitado ->Esos son los campos de la bd
            Word p = new Word(pal.getString("palabra"), pal.getString("descripcion"), pal.getInt("likes"), pal.getInt("nolikes"), pal.getInt("habilitado"));

            return p;

        } catch (JSONException | IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Word result) {

        ((MainActivity)activity).setWord(result);
    }
}